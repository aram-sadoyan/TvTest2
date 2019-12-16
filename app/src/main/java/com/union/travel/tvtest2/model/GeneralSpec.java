package com.union.travel.tvtest2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GeneralSpec implements Serializable {


	@SerializedName("Size")
	private
	String size;
	@SerializedName("Material")
	private
	String material;
	@SerializedName("Battery")
	private
	String battery;
	@SerializedName("BatteryLife")
	private
	String batterylife;
	@SerializedName("WaterResistance")
	private
	String waterResistance;
	@SerializedName("Display")
	private
	String display;
	@SerializedName("ScreenSize")
	private
	String displaySize;
	@SerializedName("Platform")
	private
	String platform;
	@SerializedName("Processor")
	private
	String processor;
	@SerializedName("Memory")
	private
	String memory;
	@SerializedName("Conectivity")
	private
	String connectivity;
	@SerializedName("Steps")
	private
	int steps;
	@SerializedName("Distance")
	private
	int distance;
	@SerializedName("Colories")
	private
	int colories;
	@SerializedName("Activity")
	private
	int activity;
	@SerializedName("Floors")
	private
	int floors;
	@SerializedName("Sleep")
	private
	int sleep;
	@SerializedName("Heart")
	private
	int heart;


	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getBattery() {
		return battery;
	}

	public void setBattery(String battery) {
		this.battery = battery;
	}

	public String getBatterylife() {
		return batterylife;
	}

	public void setBatterylife(String batterylife) {
		this.batterylife = batterylife;
	}

	public String getWaterResistance() {
		return waterResistance;
	}

	public void setWaterResistance(String waterResistance) {
		this.waterResistance = waterResistance;
	}


	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getDisplaySize() {
		return displaySize;
	}

	public void setDisplaySize(String displaySize) {
		this.displaySize = displaySize;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getConnectivity() {
		return connectivity;
	}

	public void setConnectivity(String connectivity) {
		this.connectivity = connectivity;
	}

	public boolean getSteps() {
		return steps == 1;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public boolean getDistance() {
		return distance == 1;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public boolean getColories() {
		return colories == 1;
	}

	public void setColories(int colories) {
		this.colories = colories;
	}

	public boolean getActivity() {
		return activity == 1;
	}

	public void setActivity(int activity) {
		this.activity = activity;
	}

	public boolean getFloors() {
		return floors == 1;
	}

	public void setFloors(int floors) {
		this.floors = floors;
	}

	public boolean getSleep() {
		return sleep ==1;
	}

	public void setSleep(int sleep) {
		this.sleep = sleep;
	}

	public boolean getHeart() {
		return heart ==1;
	}

	public void setHeart(int heart) {
		this.heart = heart;
	}
}


