package com.zasadnyy.reddit.model.api;

import com.zasadnyy.reddit.model.IOperationCallback;
import com.zasadnyy.reddit.model.entity.Submission;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by vitalik on 27/01/16.
 */
public interface IRedditApi {
	boolean isAuthenticated();

	void authenticate(@NotNull String appId, @NotNull String apiKey, @NotNull IOperationCallback<String> operationCallback);

	void loadSubmissions(@NotNull String subreddit, @NotNull IOperationCallback<List<Submission>> operationCallback);
}
