package com.zasadnyy.reddit.model.entity;

import com.google.gson.JsonObject;
import com.zasadnyy.reddit.model.entity.parser.SubmissionsParser;
import com.zasadnyy.reddit.model.test.utils.TestUtils;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * Created by vitalik on 01/02/16.
 */
public class SubmissionsParserTest
{
	@Test
	public void testParseSubmissions() throws FileNotFoundException, URISyntaxException {
		URL resource = getClass().getClassLoader().getResource("responses/load_submissions_response.json");
		
		Assert.assertNotNull(resource);
		
		JsonObject json = TestUtils.loadJson(resource);
		List<com.zasadnyy.reddit.model.entity.Submission> result = SubmissionsParser.parseSubmissions(json);
		
		Assert.assertEquals(10, result.size());
	}
}