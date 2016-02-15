package com.zasadnyy.reddit.model.api.request;

/**
 * Created by vitalik on 27/01/16.
 */
public interface IBasicAuthRequest extends IRequest {
	String getUsername();

	String getPassword();
}
