package com.union.travel.tvtest2.model;

import android.view.Display;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Brand {

	@SerializedName("name")
	String name;

	@SerializedName("ic_url")
	String icUrl;

	@SerializedName("model")
	List<Model> models;



}
