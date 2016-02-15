package com.zasadnyy.reddit.model.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zasadnyy.reddit.model.IOperationCallback;
import com.zasadnyy.reddit.model.api.request.AccessTokenRequest;
import com.zasadnyy.reddit.model.api.response.AccessTokenResponse;
import com.zasadnyy.reddit.model.communication.ICommunication;
import com.zasadnyy.reddit.model.entity.Submission;
import com.zasadnyy.reddit.model.entity.parser.SubmissionsParser;
import com.zasadnyy.reddit.model.utils.JsonUtils;

import java.util.List;

/**
 * Created by vitalik on 27/01/16.
 */
public class RestRedditApi implements IRedditApi {

	private String _oauthToken;
	private ICommunication _communication;

	public RestRedditApi(ICommunication communication) {
		this._communication = communication;
	}

	@Override
	public boolean isAuthenticated() {
		return _oauthToken != null && !_oauthToken.isEmpty();
	}

	@Override
	public void authenticate(String appId, String apiKey, final IOperationCallback<String> operationCallback) {
		JsonParser p = new JsonParser();
		
		if (isAuthenticated()) {
			operationCallback.onSuccess("Reddit already authenticated");
		} else {
			_communication.communicate(new AccessTokenRequest(appId, apiKey), new IOperationCallback<JsonObject>() {

				@Override
				public void onSuccess(JsonObject result) {
					AccessTokenResponse response = JsonUtils.getGson().fromJson(result, AccessTokenResponse.class);
					
					_oauthToken = String.format("%s %s", response.getTokenType(), response.getAccessToken());

					operationCallback.onSuccess("Reddit authenticated");
				}

				@Override
				public void onError(String reason) {
					operationCallback.onError(reason);
				}
			});
		}
	}

	@Override
	public void loadSubmissions(String subreddit, final IOperationCallback<List<Submission>> operationCallback) {
		if (!isAuthenticated()) {
			operationCallback.onError("Reddit Api is not authenticated");
		} else {
			_communication.communicate(new com.zasadnyy.reddit.model.api.request.SubmissionRequest(_oauthToken, subreddit), new IOperationCallback<JsonObject>() {
				@Override
				public void onSuccess(JsonObject result) {
					List<Submission> submissions = SubmissionsParser.parseSubmissions(result);
					operationCallback.onSuccess(submissions);
				}

				@Override
				public void onError(String reason) {
					operationCallback.onError(reason);
				}
			});
		}
	}
}

