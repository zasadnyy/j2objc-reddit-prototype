//
//  ViewController.m
//  reddit-gs
//
//  Created by Vitaliy Zasadnyy on 04/02/16.
//  Copyright © 2016 Vitaliy Zasadnyy. All rights reserved.
//

#import "ViewController.h"
#import "AppDelegate.h"
#import "com/zasadnyy/reddit/model/IOperationCallback.h"
#import "com/zasadnyy/reddit/model/entity/Submission.h"
#import "com/google/gson/JsonObject.h"
#import "java/util/Map.h"
#import "java/util/Set.h"
#import "java/util/Iterator.h"
#import "java/util/LinkedList.h"

@interface ViewController ()<UITableViewDataSource, UITableViewDelegate, VzIOperationCallback>

@property (weak, nonatomic) IBOutlet UITextField *textField;
@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (strong) AppDelegate* appDelegate;
@property (strong) NSMutableArray* dataToShow;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.dataToShow = [NSMutableArray array];
    self.appDelegate = (AppDelegate*)[UIApplication sharedApplication].delegate;
    
    [self.tableView setDataSource:self];
    [self.tableView setDelegate:self];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (IBAction)doSearch:(id)sender {
    if(self.textField.text.length > 0){
        [self.dataToShow removeAllObjects];
        [self.tableView reloadData];
        [self.appDelegate.manager loadSubmissionsWithNSString:self.textField.text withVzIOperationCallback:self];
    }
}

#pragma mark - IOperationDelegate methods

- (void)onSuccessWithId:(id)result{
    JavaUtilLinkedList* jsonObject = result;
    id<JavaUtilIterator> it = [jsonObject iterator];
    while([it hasNext]){
        VzSubmission* value = [it next];
        [self.dataToShow addObject:[value getTitle]];
    }
    dispatch_async(dispatch_get_main_queue(), ^{
        [self.tableView reloadData];
    });
}

- (void)onErrorWithNSString:(NSString *)reason{
    dispatch_async(dispatch_get_main_queue(), ^{
        [[[UIAlertView alloc] initWithTitle:@"Error" message:reason delegate:nil cancelButtonTitle:@"Ok" otherButtonTitles:nil] show];
    });
}


#pragma mark - TableView methods

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    UITableViewCell* cell = [tableView dequeueReusableCellWithIdentifier:@"redditCell" forIndexPath:indexPath];
    
    cell.textLabel.text = self.dataToShow[indexPath.row];
    return cell;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.dataToShow.count;
}

@end
