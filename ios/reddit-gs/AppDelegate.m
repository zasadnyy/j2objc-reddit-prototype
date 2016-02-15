//
//  AppDelegate.m
//  reddit-gs
//
//  Created by Vitaliy Zasadnyy on 04/02/16.
//  Copyright © 2016 Vitaliy Zasadnyy. All rights reserved.
//

#import "AppDelegate.h"
#import "ComponentsFactoryImpl.h"
#import "com/zasadnyy/reddit/model/IOperationCallback.h"

@interface AppDelegate () <VzIOperationCallback>

@end

@implementation AppDelegate

- (BOOL)          application:(UIApplication *)application
didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{

    ComponentsFactoryImpl *impl = [[ComponentsFactoryImpl alloc] init];

    self.manager = [[VzRedditDataManager alloc] initWithVzIComponentsFactory:impl];
    [self.manager initWithAppId:@"MbIIzk6XocJVPkIF5cFfxPAkiEg" apiKey:@"lQ_oH0YDiu4lwA" callback:self];


    return YES;
}

- (void)onSuccessWithId:(id)result
{
    NSLog(@"Manager initialized");
}

- (void)onErrorWithNSString:(NSString *)reason
{
    NSLog(@"Manager failed to initalize, error : %@", reason.description);
}


- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state.
    // This can occur for certain types of temporary interruptions (such as an
    // incoming phone call or SMS message) or when the user quits the application
    // and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down
    // OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate
    // timers, and store enough application state information to restore your
    // application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called
    // instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state;
    // here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the
    // application was inactive. If the application was previously in the
    // background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if
    // appropriate. See also applicationDidEnterBackground:.
}

@end
