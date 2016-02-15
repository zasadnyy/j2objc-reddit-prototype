package com.zasadnyy.reddit.model.test.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by vitalik on 01/02/16.
 */
public final class TestUtils {

	private TestUtils() {
	}

	public static JsonObject loadJson(URL path) throws URISyntaxException, FileNotFoundException
	{
		Reader reader = new FileReader(new File(path.toURI()));
		JsonElement jsonElement = new JsonParser().parse(new JsonReader(reader));
		return jsonElement.getAsJsonObject();
	}
}
