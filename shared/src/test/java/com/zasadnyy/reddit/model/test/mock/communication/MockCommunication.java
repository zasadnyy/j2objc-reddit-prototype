package com.zasadnyy.reddit.model.test.mock.communication;

import com.google.gson.JsonObject;
import com.zasadnyy.reddit.model.IOperationCallback;
import com.zasadnyy.reddit.model.RedditDataManagerTest;
import com.zasadnyy.reddit.model.api.request.AccessTokenRequest;
import com.zasadnyy.reddit.model.api.request.IRequest;
import com.zasadnyy.reddit.model.api.request.SubmissionRequest;
import com.zasadnyy.reddit.model.communication.ICommunication;
import com.zasadnyy.reddit.model.test.utils.TestUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;


/**
 * @author Vitaliy Zasadnyy on 04/02/16.
 * @copyright &copy; 2015 GetSocial B.V. All rights reserved.
 */
public class MockCommunication implements ICommunication {
	
	@Override
	public void communicate(@NotNull IRequest request, @Nullable IOperationCallback<JsonObject> operationCallback) {
		if(request instanceof AccessTokenRequest) {
			if(RedditDataManagerTest.REDDIT_APP_ID.equals(((AccessTokenRequest) request).getUsername())) {
				responseWith("responses/request_auth_token_response.json", operationCallback, true);
			}
			else {
				responseWith("responses/request_auth_tocken_response_failed.json", operationCallback, false);
			}
		}
		else if(request instanceof SubmissionRequest) {
			responseWith("responses/load_submissions_response.json", operationCallback, true);
		}
	}
	
	private void responseWith(String responsePath, @Nullable IOperationCallback<JsonObject> operationCallback, boolean isSuccesful) {
		final URL url = getClass().getClassLoader().getResource(responsePath);
		final JsonObject json;
		
		try {
			json = TestUtils.loadJson(url);
			if(isSuccesful) {
				operationCallback.onSuccess(json);
			}
			else {
				operationCallback.onError(json.toString());
			}
		}
		catch(URISyntaxException | FileNotFoundException e) {
			operationCallback.onError(e.getMessage());
		}
	}
	
}
