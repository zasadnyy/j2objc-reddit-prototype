package com.zasadnyy.reddit.model.communication;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zasadnyy.reddit.model.IOperationCallback;
import com.zasadnyy.reddit.model.api.request.IBasicAuthRequest;
import com.zasadnyy.reddit.model.api.request.IRequest;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Vitaliy Zasadnyy on 02/02/16.
 * @copyright &copy; 2015 GetSocial B.V. All rights reserved.
 */
public class OkHttpCommunication implements ICommunication {

	public static final String USER_AGENT = "test-app-234567890";

	private final OkHttpClient _client = new OkHttpClient();

	@Override
	public void communicate(IRequest request, final IOperationCallback<JsonObject> operationCallback) {
		JsonParser p = new JsonParser();
		p.parse("test");
		Request okHttpRequest = convertRequest(request);

		_client.newCall(okHttpRequest).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				operationCallback.onError(e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				if (response.isSuccessful()) {
					operationCallback.onSuccess(new JsonParser().parse(response.body().string()).getAsJsonObject());
				} else {
					operationCallback.onError("Request failed with response code: " + response.code());
				}
			}
		});
	}

	private Request convertRequest(IRequest request) {
		Request.Builder builder = new Request.Builder();

		// add params
		switch (request.getMethod()) {
			case POST:
				builder.post(createPostBody(request));
				builder.url(request.getUrl());
				break;
			case GET:
				builder.url(createGetUrl(request));
				break;
		}

		// add default headers
		builder.header("user-agent", USER_AGENT);

		// add headers
		Map<String, Object> headers = request.getHeaders();
		if (headers != null && headers.size() > 0) {
			for (Map.Entry<String, Object> header : headers.entrySet()) {
				builder.addHeader(header.getKey(), header.getValue().toString());
			}
		}

		// add authorization
		if (request instanceof IBasicAuthRequest) {
			IBasicAuthRequest basicAuthRequest = (IBasicAuthRequest) request;
			String basicCredentials = Credentials.basic(basicAuthRequest.getUsername(), basicAuthRequest.getPassword());
			builder.header("Authorization", basicCredentials);
		}

		return builder.build();
	}

	private String createGetUrl(IRequest request) {
		StringBuilder sb = new StringBuilder(request.getUrl());

		Map<String, Object> params = request.getParams();
		if (params != null && params.size() > 0) {
			sb.append("?");

			Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> header = iterator.next();
				sb.append(header.getKey()).append("=").append(header.getValue());

				if (iterator.hasNext()) {
					sb.append("&");
				}
			}
		}

		return sb.toString();
	}

	@NotNull
	private RequestBody createPostBody(IRequest request) {
		FormBody.Builder body = new FormBody.Builder();

		Map<String, Object> params = request.getParams();
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, Object> param : params.entrySet()) {
				body.addEncoded(param.getKey(), param.getValue().toString());
			}
		}

		return body.build();
	}
}
