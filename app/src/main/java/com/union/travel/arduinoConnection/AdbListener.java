package com.union.travel.arduinoConnection;

public interface AdbListener {
	public void adbConnected();

	public void adbDisconnected();

	public void adbEvent(byte[] buffer);
}