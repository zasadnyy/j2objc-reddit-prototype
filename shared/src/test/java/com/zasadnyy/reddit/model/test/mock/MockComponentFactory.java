package com.zasadnyy.reddit.model.test.mock;

import com.zasadnyy.reddit.model.IComponentsFactory;
import com.zasadnyy.reddit.model.api.IRedditApi;
import com.zasadnyy.reddit.model.api.RestRedditApi;
import com.zasadnyy.reddit.model.cache.ICache;
import com.zasadnyy.reddit.model.cache.MemoryCache;
import com.zasadnyy.reddit.model.communication.ICommunication;
import com.zasadnyy.reddit.model.test.mock.communication.MockCommunication;

/**
 * @author Vitaliy Zasadnyy on 04/02/16.
 * @copyright &copy; 2015 GetSocial B.V. All rights reserved.
 */
public class MockComponentFactory implements IComponentsFactory {

	@Override
	public ICommunication provideCommunication() {
		return new MockCommunication();
	}

	@Override
	public ICache provideCache() {
		return new MemoryCache();
	}

	@Override
	public IRedditApi provideRedditApi() {
		return new RestRedditApi(provideCommunication());
	}
}
