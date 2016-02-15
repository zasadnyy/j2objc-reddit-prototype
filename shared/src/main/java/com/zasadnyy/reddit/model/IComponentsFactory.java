package com.zasadnyy.reddit.model;

import com.zasadnyy.reddit.model.api.IRedditApi;
import com.zasadnyy.reddit.model.cache.ICache;
import com.zasadnyy.reddit.model.communication.ICommunication;

/**
 * @author Vitaliy Zasadnyy on 04/02/16.
 * @copyright &copy; 2015 GetSocial B.V. All rights reserved.
 */
public interface IComponentsFactory {
	ICommunication provideCommunication();

	ICache provideCache();

	IRedditApi provideRedditApi();
}
