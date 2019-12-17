package com.union.travel.tvtest3.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.Map;

public class DefaultGsonBuilder {

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private static Gson defaultGson = null;
	private static Gson exposedGson = null;
	private static Gson serializedGson = null;

	private static final JsonSerializer<Double> DOUBLE_JSON_SERIALIZER = new JsonSerializer<Double>() {
		@Override
		public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
			if (src == (double) src.longValue())
				return new JsonPrimitive(src.longValue());
			return new JsonPrimitive(src);
		}
	};

	private static final ExclusionStrategy SERIALIZED_EXCLUSION_STRATEGY = new ExclusionStrategy() {
		@Override
		public boolean shouldSkipField(FieldAttributes f) {
			return f.getAnnotation(SerializedName.class) != null;
		}

		@Override
		public boolean shouldSkipClass(Class<?> clazz) {
			return false;
		}
	};

	public static Gson getDefaultGson() {
		if (defaultGson == null)
			defaultGson = new GsonBuilder()
					.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
					.setDateFormat(DEFAULT_DATE_FORMAT)
					.serializeSpecialFloatingPointValues()
					.disableHtmlEscaping()
					.registerTypeAdapter(Double.class, DOUBLE_JSON_SERIALIZER)
					.create();
		return defaultGson;
	}

	public static Gson getExposedGson() {
		if (exposedGson == null)
			exposedGson = new GsonBuilder()
					.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
					.excludeFieldsWithoutExposeAnnotation()
					.setDateFormat(DEFAULT_DATE_FORMAT)
					.serializeSpecialFloatingPointValues()
					.disableHtmlEscaping()
					.registerTypeAdapter(Double.class, DOUBLE_JSON_SERIALIZER)
					.create();
		return exposedGson;
	}

	public static Gson getSerializedGson() {
		if (serializedGson == null)
			serializedGson = new GsonBuilder()
					.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
					.setExclusionStrategies(SERIALIZED_EXCLUSION_STRATEGY)
					.setDateFormat(DEFAULT_DATE_FORMAT)
					.serializeSpecialFloatingPointValues()
					.disableHtmlEscaping()
					.registerTypeAdapter(Double.class, DOUBLE_JSON_SERIALIZER)
					.create();
		return serializedGson;
	}

	public static Gson getGsonWithTypeAdapters(Map<Type, JsonDeserializer> jsonSerializersMap) {
		if (jsonSerializersMap == null) {
			throw new NullPointerException("jsonSerializersMap is null");
		}
		if (jsonSerializersMap.isEmpty()) {
			throw new IllegalArgumentException("jsonSerializersMap is empty, use getDefaultGson()");
		}
		GsonBuilder builder = new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setDateFormat(DEFAULT_DATE_FORMAT)
				.serializeSpecialFloatingPointValues()
				.disableHtmlEscaping()
				.registerTypeAdapter(Double.class, DOUBLE_JSON_SERIALIZER);
		for (Map.Entry<Type, JsonDeserializer> entry : jsonSerializersMap.entrySet()) {
			builder.registerTypeAdapter(entry.getKey(), entry.getValue());
		}
		return builder.create();
	}

}