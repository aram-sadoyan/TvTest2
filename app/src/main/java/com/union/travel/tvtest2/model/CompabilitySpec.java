package com.union.travel.tvtest2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CompabilitySpec implements Serializable {


	@SerializedName("Android")
	private int android;
	@SerializedName("Ios")
	private int ios;
	@SerializedName("Windows")
	private int windows;
	@SerializedName("Mac")
	private int mac;
	@SerializedName("WindowsPhone")
	private int windowsPhone;


	public boolean getAndroid() {
		return android == 1;
	}

	public void setAndroid(int android) {
		this.android = android;
	}

	public boolean getIos() {
		return ios == 1;
	}

	public void setIos(int ios) {
		this.ios = ios;
	}

	public boolean getWindows() {
		return windows == 1;
	}

	public void setWindows(int windows) {
		this.windows = windows;
	}

	public boolean getMac() {
		return mac == 1;
	}

	public void setMac(int mac) {
		this.mac = mac;
	}

	public boolean getWindowsPhone() {
		return windowsPhone == 1;
	}

	public void setWindowsPhone(int windowsPhone) {
		this.windowsPhone = windowsPhone;
	}
}
