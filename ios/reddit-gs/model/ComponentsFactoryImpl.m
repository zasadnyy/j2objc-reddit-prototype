//
//  ComponentsFactoryImpl.m
//  reddit-gs
//
//  Created by Vass Gábor on 08/02/16.
//  Copyright © 2016 Vitaliy Zasadnyy. All rights reserved.
//

#import "ComponentsFactoryImpl.h"
#import "MemoryCache.h"
#import "OkHttpCommunication.h"
#import "RestRedditApi.h"
#import "com/zasadnyy/reddit/model/api/RestRedditApi.h"

#define REDDIT_API_KEY @"MbIIzk6XocJVPkIF5cFfxPAkiEg";
#define REDDIT_APP_ID @"lQ_oH0YDiu4lwA";


@interface ComponentsFactoryImpl()

@property (strong) MemoryCache* memoryCache;
@property (strong) OkHttpCommunication* httpCommunication;
@property (strong) VzRestRedditApi* restRedditApi;

@end

@implementation ComponentsFactoryImpl

- (id<VzICommunication>)provideCommunication {
  NSLog(@"%s is called", __FUNCTION__);
    if(!self.httpCommunication){
        self.httpCommunication = [[OkHttpCommunication alloc] init];
    }
  return self.httpCommunication;
}

- (id<VzICache>)provideCache {
  NSLog(@"%s is called", __FUNCTION__);
    if(!self.memoryCache){
        self.memoryCache = [[MemoryCache alloc] init];
    }
    return self.memoryCache;
}

- (id<VzIRedditApi>)provideRedditApi {
  NSLog(@"%s is called", __FUNCTION__);
    if(!self.restRedditApi){
        self.restRedditApi = [[VzRestRedditApi alloc] initWithVzICommunication:[self provideCommunication]];
    }
    return self.restRedditApi;
}

@end
