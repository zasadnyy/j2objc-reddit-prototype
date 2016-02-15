package com.zasadnyy.reddit.model.api.request;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Created by vitalik on 27/01/16.
 */
public interface IRequest {

	Method getMethod();

	@NotNull
	String getUrl();

	@Nullable
	Map<String, Object> getHeaders();

	@Nullable
	Map<String, Object> getParams();

	enum Method {
		GET, POST
	}
}
