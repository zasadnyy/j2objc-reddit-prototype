//
//  AppDelegate.h
//  reddit-gs
//
//  Created by Vitaliy Zasadnyy on 04/02/16.
//  Copyright © 2016 Vitaliy Zasadnyy. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "com/zasadnyy/reddit/model/RedditDataManager.h"

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong) VzRedditDataManager* manager;

@end

