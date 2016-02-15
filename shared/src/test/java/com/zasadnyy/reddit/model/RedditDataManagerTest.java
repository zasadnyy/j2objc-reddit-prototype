package com.zasadnyy.reddit.model;

import com.zasadnyy.reddit.model.entity.Submission;
import com.zasadnyy.reddit.model.test.mock.MockComponentFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by vitalik on 01/02/16.
 */
public class RedditDataManagerTest {
	
	public static final String REDDIT_API_KEY = "MbIIzk6XocJVPkIF5cFfxPAkiEg";
	public static final String REDDIT_APP_ID = "lQ_oH0YDiu4lwA";
	
	private com.zasadnyy.reddit.model.RedditDataManager _manager;
	
	@Before
	public void setUp() throws Exception {
		_manager = new RedditDataManager(new MockComponentFactory());
	}
	
	@Test
	public void testInitSuccess() throws Exception {
		_manager.init(REDDIT_APP_ID, REDDIT_API_KEY, new com.zasadnyy.reddit.model.IOperationCallback<String>() {
			@Override
			public void onSuccess(String result) {
				Assert.assertTrue(_manager.isInitialized());
			}
			
			@Override
			public void onError(String reason) {
				Assert.fail(reason);
			}
		});
	}
	
	@Test
	public void testInitFailed() throws Exception {
		_manager.init("wrong app id", REDDIT_API_KEY, new com.zasadnyy.reddit.model.IOperationCallback<String>() {
			@Override
			public void onSuccess(String result) {
				Assert.fail("Init should fail sue to wrong app id");
			}
			
			@Override
			public void onError(String reason) {
				Assert.assertEquals("{\"error\":401}", reason);
			}
		});
	}
	
	@Test
	public void testLoadSubmissions() throws Exception {
		final String subreddit = "singularity";
		
		_manager.init(REDDIT_APP_ID, REDDIT_API_KEY, new com.zasadnyy.reddit.model.IOperationCallback<String>() {
			@Override
			public void onSuccess(String result) {
				_manager.loadSubmissions(subreddit, new com.zasadnyy.reddit.model.IOperationCallback<List<Submission>>() {
					@Override
					public void onSuccess(List<Submission> result) {
						Assert.assertEquals(10, result.size());
						Assert.assertEquals(subreddit, result.get(0).getSubreddit());
					}
					
					@Override
					public void onError(String reason) {
						Assert.fail(reason);
					}
				});
			}
			
			@Override
			public void onError(String reason) {
				Assert.fail(reason);
			}
		});
	}
}