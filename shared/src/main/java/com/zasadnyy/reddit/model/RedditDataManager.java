package com.zasadnyy.reddit.model;

import com.zasadnyy.reddit.model.api.IRedditApi;
import com.zasadnyy.reddit.model.cache.ICache;
import com.zasadnyy.reddit.model.entity.Submission;

import java.util.List;

/**
 * Created by vitalik on 27/01/16.
 */
public class RedditDataManager {
	
	private IRedditApi _api;
	private ICache _cache;
	
	public RedditDataManager(IComponentsFactory factory) {
		this._api = factory.provideRedditApi();
		this._cache = factory.provideCache();
	}
	
	public boolean isInitialized() {
		return _api.isAuthenticated();
	}
	
	public void init(String appId, String apiKey, IOperationCallback<String> callback) {
		if(_api.isAuthenticated()) {
			callback.onSuccess("Authenticated");
		}
		else {
			_api.authenticate(appId, apiKey, callback);
		}
	}
	
	public void loadSubmissions(final String subreddit, final IOperationCallback<List<Submission>> callback) {
		if(!isInitialized()) {
			callback.onError("Reddit DataManager is not initialized");
		}
		else {
			_api.loadSubmissions(subreddit, new IOperationCallback<List<Submission>>() {
				
				@Override
				public void onSuccess(List<Submission> result) {
					_cache.saveSubmissions(result);
					callback.onSuccess(result);
				}
				
				@Override
				public void onError(String reason) {
					callback.onError(reason);
				}
			});
		}
	}
}
