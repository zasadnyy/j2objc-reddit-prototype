package com.zasadnyy.reddit.model.api.request;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vitalik on 01/02/16.
 */
public class SubmissionRequest implements IRequest {

	public static final String URL_PATTERN = "https://oauth.reddit.com/r/%s/top";

	private Map<String, Object> _headers;
	private Map<String, Object> _params;
	private String _subreddit;

	public SubmissionRequest(String oauthToken, String subreddit) {
		this._subreddit = subreddit;

		_headers = new HashMap<>();
		_headers.put("Authorization", oauthToken);

		_params = new HashMap<>();
		_params.put("count", 0);
		_params.put("limit", 10);
		_params.put("t", "month");
		_params.put("show", "all");
	}

	@Override
	public Method getMethod() {
		return Method.GET;
	}

	@NotNull
	@Override
	public String getUrl() {
		return String.format(URL_PATTERN, _subreddit);
	}

	@NotNull
	@Override
	public Map<String, Object> getHeaders() {
		return _headers;
	}

	@NotNull
	@Override
	public Map<String, Object> getParams() {
		return _params;
	}
}
