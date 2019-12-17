package com.union.travel.tvtest3.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Model implements Serializable {


	@SerializedName("ID")
	private
	int id;

	@SerializedName("Name")
	private
	String name;

	@SerializedName("Category")
	private
	String category;

	@SerializedName("title")
	private
	String title;


	/// serialTxtView  ex.  PART NUMBER 010-02158-13
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

	@SerializedName("Brend")
	private
	String brandName = "";

	@SerializedName("Price")
	private
	List<Price> prices = new ArrayList<>();

	@SerializedName("Color")
	private
	List<Color> colors = new ArrayList<>();

	@SerializedName("VideoLink")
	private
	List<String> videoUrls = new ArrayList<>();

	@SerializedName("NewImgLink")
	private
	List<String> videoImages = new ArrayList<>();

	@SerializedName("Specs")
	private Spec spec;


	private List<ModelVideo> modelVideos = new ArrayList<>();

	public List<ModelVideo> getModelVideos() {
		return modelVideos;
	}

	public void setModelVideos(List<ModelVideo> modelVideos) {
		this.modelVideos = modelVideos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Spec getSpec() {
		return spec;
	}

	public void setSpec(Spec spec) {
		this.spec = spec;
	}

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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public List<String> getVideoImages() {
		return videoImages;
	}

	public void setVideoImages(List<String> videoImages) {
		this.videoImages = videoImages;
	}
}
