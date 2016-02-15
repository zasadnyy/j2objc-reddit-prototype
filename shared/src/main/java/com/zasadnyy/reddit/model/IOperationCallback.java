package com.zasadnyy.reddit.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by vitalik on 27/01/16.
 */
public interface IOperationCallback<T> {
	void onSuccess(@Nullable T result);

	void onError(@NotNull String reason);
}
