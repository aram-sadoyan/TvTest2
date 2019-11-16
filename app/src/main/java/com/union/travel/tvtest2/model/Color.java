package com.union.travel.tvtest2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Color {


	@SerializedName("Name")
	private
	String colorName;


	@SerializedName("ColorURL")
	private List<String> colorUrls;

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public List<String> getColorUrls() {
		return colorUrls;
	}

	public void setColorUrls(List<String> colorUrls) {
		this.colorUrls = colorUrls;
	}
}
