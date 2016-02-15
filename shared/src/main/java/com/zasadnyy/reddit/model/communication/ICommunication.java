package com.zasadnyy.reddit.model.communication;

import com.google.gson.JsonObject;
import com.zasadnyy.reddit.model.IOperationCallback;
import com.zasadnyy.reddit.model.api.request.IRequest;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by vitalik on 27/01/16.
 */
public interface ICommunication {
	void communicate(@NotNull IRequest request, @Nullable IOperationCallback<JsonObject> operationCallback);
}
