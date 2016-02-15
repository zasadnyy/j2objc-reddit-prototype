package com.zasadnyy.reddit.model.api.request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vitalik on 27/01/16.
 */
public class AccessTokenRequest implements IBasicAuthRequest {

	private static final String URL = "https://www.reddit.com/api/v1/access_token";

	private Map<String, Object> _params;
	private String _appId;
	private String _apiKey;

	public AccessTokenRequest(String appId, String apiKey) {
		this._appId = appId;
		this._apiKey = apiKey;

		_params = new HashMap<>();
		_params.put("grant_type", "https://oauth.reddit.com/grants/installed_client");
		_params.put("device_id", "1234567890qwertyuiopasd");
		_params.put("scope", "read");
	}

	@Override
	public IRequest.Method getMethod() {
		return IRequest.Method.POST;
	}

	@Override
	public String getUrl() {
		return URL;
	}

	@Override
	public Map<String, Object> getHeaders() {
		return null;
	}

	@Override
	public Map<String, Object> getParams() {
		return _params;
	}

	@Override
	public String getUsername() {
		return _appId;
	}

	@Override
	public String getPassword() {
		return _apiKey;
	}
}
