package com.union.travel.tvtest2.api;

import com.google.gson.annotations.SerializedName;
import com.union.travel.tvtest2.utils.DefaultGsonBuilder;

public class Response {

	@SerializedName("message") public String message = "";
	@SerializedName("reason") public String reason = "";

	@Override
	public String toString() {
		return DefaultGsonBuilder.getDefaultGson().toJson(this);
	}
}