package com.union.travel.tvtest3.model;

import com.google.gson.annotations.SerializedName;

public class BrandItem {

	@SerializedName("icon_url")
	private String icUrl = null;

	@SerializedName("name")
	private String name = null;

	public String getIcUrl() {
		return icUrl;
	}

	public void setIcUrl(String icUrl) {
		this.icUrl = icUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
