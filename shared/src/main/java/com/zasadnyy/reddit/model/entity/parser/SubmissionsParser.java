package com.zasadnyy.reddit.model.entity.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vitalik on 01/02/16.
 */
public final class SubmissionsParser {

	private SubmissionsParser() {
	}

	public static List<com.zasadnyy.reddit.model.entity.Submission> parseSubmissions(JsonObject json) {
		List<com.zasadnyy.reddit.model.entity.Submission> result = new LinkedList<>();
		JsonArray children = json.getAsJsonObject("data").getAsJsonArray("children");
		for (JsonElement child : children) {
			result.add(new com.zasadnyy.reddit.model.entity.Submission(child.getAsJsonObject().getAsJsonObject("data")));
		}
		return result;
	}
}
