package com.zasadnyy.reddit.model.cache;

import com.zasadnyy.reddit.model.entity.Submission;

import java.util.List;

/**
 * Created by vitalik on 27/01/16.
 */
public interface ICache {
	/**
	 * Blocking call
	 */
	void saveSubmissions(List<Submission> result);
}
