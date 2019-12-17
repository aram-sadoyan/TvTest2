package com.union.travel.tvtest3.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Price implements Serializable {


	@SerializedName("Size")
	private
	int size;

	@SerializedName("Price")
	private
	String pricetext;


	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getPricetext() {
		return pricetext;
	}

	public void setPricetext(String pricetext) {
		this.pricetext = pricetext;
	}
}
