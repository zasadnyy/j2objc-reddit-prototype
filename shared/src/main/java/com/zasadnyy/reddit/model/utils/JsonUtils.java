package com.zasadnyy.reddit.model.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * Created by vitalik on 01/02/16.
 */
public final class JsonUtils {
	
	private static Gson gson;
	
	private JsonUtils() {
		// forbid creating JsonUtils instance
	}
	
	public static Gson getGson() {
		if(gson == null) {
			gson = new GsonBuilder()
					.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
					.create();
		}
		return gson;
	}
	
	/**
	 * Safely converts an object into string (used because sometimes JSONObject's get() method returns null).
	 *
	 * @param obj The object to convert.
	 *
	 * @return The string.
	 */
	public static String safeJsonToString(JsonElement obj) {
		if(obj != null && !obj.isJsonNull() && obj.isJsonPrimitive()) {
			return obj.getAsString();
		}
		return null;
	}
	
	/**
	 * Safely converts an object into an integer
	 *
	 * @param obj The object to convert.
	 *
	 * @return an Integer representing the integer value of the Object (null if the object cannot be converted to an Integer)
	 */
	public static Integer safeJsonToInteger(JsonElement obj) {
		Integer intValue = null;
		
		try {
			String str = safeJsonToString(obj);
			intValue = str != null ? Integer.parseInt(str) : null;
		}
		catch(NumberFormatException e) {
			System.err.println("Safe JSON conversion to Integer failed");
		}
		
		return intValue;
	}
	
	/**
	 * Safely converts an object into an double
	 *
	 * @param obj The object to convert.
	 *
	 * @return a Double representing the double value of the Object (null if the object cannot be converted to Double)
	 */
	public static Double safeJsonToDouble(JsonElement obj) {
		Double doubleValue = null;
		
		try {
			String str = safeJsonToString(obj);
			doubleValue = str != null ? Double.parseDouble(str) : null;
		}
		catch(NumberFormatException e) {
			System.err.println("Safe JSON conversion to Double failed");
		}
		
		return doubleValue;
	}
	
	
	/**
	 * Safely converts an object into an boolean
	 *
	 * @param obj The object to convert.
	 *
	 * @return a Boolean representing the boolean value of the Object (null only if the object was also null)
	 */
	public static Boolean safeJsonToBoolean(JsonElement obj) {
		String str = safeJsonToString(obj);
		Boolean booleanValue = str != null ? Boolean.parseBoolean(str) : null;
		return booleanValue;
	}
	
	/**
	 * Safely converts an object into an long
	 *
	 * @param obj The object to convert.
	 *
	 * @return a Long representing the long value of the Object (null if the object cannot be converted to Long)
	 */
	public static Long safeJsonToLong(JsonElement obj) {
		Long longValue = null;
		
		try {
			String str = safeJsonToString(obj);
			longValue = str != null ? Long.parseLong(str) : null;
		}
		catch(NumberFormatException e) {
			System.err.println("Safe JSON conversion to Long failed");
		}
		
		return longValue;
	}
}
