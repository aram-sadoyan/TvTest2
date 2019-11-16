package com.union.travel.tvtest2.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Model {


	@SerializedName("Name")
	private
	String name;

	@SerializedName("Category")
	private
	String category;

	@SerializedName("title")
	private
	String title;


	@SerializedName("SDescription")
	private
	String sdDescription;


	@SerializedName("Description")
	private
	String description;

	@SerializedName("PartNumber")
	private
	String partNumber;

	@SerializedName("Music")
	private
	boolean music;


	@SerializedName("Price")
	private
	List<Price> prices = new ArrayList<>();

	@SerializedName("Color")
	private
	List<Color> colors = new ArrayList<>();

	@SerializedName("VideoLink")
	private
	List<String> videoUrls = new ArrayList<>();


	public List<Color> getColors() {
		return colors;
	}

	public void setColors(List<Color> colors) {
		this.colors = colors;
	}

	public List<String> getVideoUrls() {
		return videoUrls;
	}

	public void setVideoUrls(List<String> videoUrls) {
		this.videoUrls = videoUrls;
	}

	public boolean isMusic() {
		return music;
	}

	public void setMusic(boolean music) {
		this.music = music;
	}

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}








	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSdDescription() {
		return sdDescription;
	}

	public void setSdDescription(String sdDescription) {
		this.sdDescription = sdDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}






	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}




}
