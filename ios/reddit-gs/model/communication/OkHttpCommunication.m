//
//  OkHttpCommunication.m
//  reddit-gs
//
//  Created by Vass Gábor on 08/02/16.
//  Copyright © 2016 Vitaliy Zasadnyy. All rights reserved.
//

#import "OkHttpCommunication.h"
#import "com/zasadnyy/reddit/model/api/request/IRequest.h"
#import "com/zasadnyy/reddit/model/api/request/IBasicAuthRequest.h"
#import "com/zasadnyy/reddit/model/IOperationCallback.h"
#import "com/google/gson/JsonParser.h"
#import "com/google/gson/JsonObject.h"
#import "java/util/Map.h"
#import "java/util/Set.h"
#import "java/util/Iterator.h"

#define USER_AGENT @"test-app-987652"

@implementation OkHttpCommunication

- (void)communicateWithVzIRequest:(id<VzIRequest>)request
         withVzIOperationCallback:(id<VzIOperationCallback>)operationCallback {
    
    NSURLSession* session = [NSURLSession sessionWithConfiguration:[NSURLSessionConfiguration defaultSessionConfiguration]];
    
    
    NSMutableURLRequest* localRequest = [self prepareLocalRequestFromRequest:request];
    
    if([request conformsToProtocol:@protocol(VzIBasicAuthRequest)]){
        [localRequest setValue:@"Basic bFFfb0gwWURpdTRsd0E6TWJJSXprNlhvY0pWUGtJRjVjRmZ4UEFraUVn" forHTTPHeaderField:@"Authorization"];
    }
    
    // add headers
    id<JavaUtilMap> headers = [request getHeaders];
    if(headers != nil && [headers size] > 0){
        for(id<JavaUtilMap_Entry> param in [headers entrySet]){
            NSString* key = [param getKey];
            NSString* value = [param getValue];
            [localRequest setValue:value forHTTPHeaderField:key];
        }
    }
    
    [localRequest setValue:USER_AGENT forHTTPHeaderField:@"User-Agent"];
    
    NSLog(@"Sending request: %@",localRequest.URL.absoluteString);
    
    NSURLSessionDataTask* dataTask = [session dataTaskWithRequest:localRequest completionHandler:^(NSData *data, NSURLResponse* response, NSError* error) {
        NSHTTPURLResponse* httpResponse = (NSHTTPURLResponse*)response;
        NSLog(@"Request finished, response code: %lu", httpResponse.statusCode);
        if(error == nil && httpResponse.statusCode < 300){
            ComGoogleGsonJsonParser* parser = [[ComGoogleGsonJsonParser alloc] init];
            NSString* stringContent = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
            ComGoogleGsonJsonObject* retValue = [[parser parseWithNSString:stringContent] getAsJsonObject];
            [operationCallback onSuccessWithId:retValue];
        }else{
            if(error == nil){
                [operationCallback onErrorWithNSString:[NSString stringWithFormat:@"Request failed with response code: %lu", httpResponse.statusCode]];
            }else{
                [operationCallback onErrorWithNSString:[NSString stringWithFormat:@"Request failed, error: %@", error.description]];
            }
        }

    }];
    [dataTask resume];
    
}

-(NSMutableURLRequest*) prepareLocalRequestFromRequest:(id<VzIRequest>)request{
    NSMutableURLRequest* retValue = nil;
    NSURL* url = nil;
    NSData* postBody = nil;
    NSString* method = nil;
    if([request getMethod] == VzIRequest_MethodEnum_get_POST()){
        url = [NSURL URLWithString:[request getUrl]];
        postBody = [self createPostBody:request];
        method = @"POST";
    }else{
        method = @"GET";
        url = [self createGetURL:request];
    }
    retValue = [NSMutableURLRequest requestWithURL:url];
    [retValue setHTTPMethod:method];
    [retValue setHTTPBody:postBody];
    return retValue;
}

-(NSData*) createPostBody:(id<VzIRequest>)request{
    NSData* retValue = nil;
    id<JavaUtilMap> params = [request getParams];
    if(params != nil && [params size] > 0){
        NSMutableString* bodyStr = [[NSMutableString alloc] init];
        int counter = 0;
        for(id<JavaUtilMap_Entry> param in [params entrySet]){
            NSString* key = [param getKey];
            NSString* value = [param getValue];
            NSString* append = [[NSString stringWithFormat:@"%@=%@", key, value] stringByAddingPercentEscapesUsingEncoding:NSASCIIStringEncoding];
            if(counter > 0){
                [bodyStr appendString:@"&"];
            }
            counter++;
            [bodyStr appendString:append];
        }
        retValue = [bodyStr dataUsingEncoding:NSUTF8StringEncoding];
    }
    return retValue;
}

-(NSURL*)createGetURL:(id<VzIRequest>)request{
    NSMutableString* completeStrURL = [[NSMutableString alloc] initWithString:[request getUrl]];
    
    id<JavaUtilMap> params = [request getParams];
    if(params != NULL && [params size] > 0){
        [completeStrURL stringByAppendingString:@"?"];
        
        id<JavaUtilIterator> it = [[params entrySet] iterator];
        while([it hasNext]){
            id header = [it next];
            NSString* key = [header getKey];
            NSString* value = [header getValue];
            
            [completeStrURL stringByAppendingFormat:@"%@=%@",key, value];
            
            if([it hasNext]){
                [completeStrURL stringByAppendingString:@"&"];
            }
        }
    }
    
    NSURL* completeURL = [NSURL URLWithString:completeStrURL];
    return completeURL;
}

@end
