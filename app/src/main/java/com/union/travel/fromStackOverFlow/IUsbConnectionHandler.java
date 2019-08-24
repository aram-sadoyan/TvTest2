package com.union.travel.fromStackOverFlow;

public interface IUsbConnectionHandler {

	void onUsbStopped();

	void onErrorLooperRunningAlready();

	void onDeviceNotFound();
}