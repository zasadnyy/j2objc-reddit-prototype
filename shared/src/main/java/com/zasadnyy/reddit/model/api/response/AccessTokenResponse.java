package com.zasadnyy.reddit.model.api.response;

/**
 * @author Vitaliy Zasadnyy on 09/02/16.
 * @copyright &copy; 2015 GetSocial B.V. All rights reserved.
 */

/*
	JSON representation
	
	{
		"access_token": "-ptGVBtMwKKOe4UrD0-74B_MJGhU",
		"token_type": "bearer",
		"expires_in": 3600,
		"scope": "*"
	}
*/
public class AccessTokenResponse {
	
	//	@SerializedName("access_token")
	private String accessToken;
	private String tokenType;
	private long expiresIn;
	private String scope;
	
	public AccessTokenResponse() {
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public String getTokenType() {
		return tokenType;
	}
	
	public long getExpiresIn() {
		return expiresIn;
	}
	
	public String getScope() {
		return scope;
	}
}
