package com.union.travel.tvtest2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.union.travel.fromStackOverFlow.IUsbConnectionHandler;
import com.union.travel.fromStackOverFlow.UsbController;

public class UsbControllerActivity extends Activity {
	/** Called when the activity is first created. */
	private static final int VID = 0x2341;
	private static final int PID = 0x0001;//I believe it is 0x0000 for the Arduino Megas
	private static UsbController sUsbController;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usb_controller);
		if(sUsbController == null){
			sUsbController = new UsbController(this, mConnectionHandler, VID, PID);
		}
//		((SeekBar)(findViewById(R.id.seekBar1))).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//			@Override
//			public void onStopTrackingTouch(SeekBar seekBar) {
//			}
//			@Override
//			public void onStartTrackingTouch(SeekBar seekBar) {
//			}
//
//			@Override
//			public void onProgressChanged(SeekBar seekBar, int progress,
//										  boolean fromUser) {
//				if(fromUser){
//					if(sUsbController != null){
//						sUsbController.send((byte)(progress&0xFF));
//					}
//				}
//			}
//		});
		((Button)findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(sUsbController == null)
					sUsbController = new UsbController(UsbControllerActivity.this, mConnectionHandler, VID, PID);
				else{
					sUsbController.stop();
					sUsbController = new UsbController(UsbControllerActivity.this, mConnectionHandler, VID, PID);
				}
			}
		});

	}

	private final IUsbConnectionHandler mConnectionHandler = new IUsbConnectionHandler() {
		@Override
		public void onUsbStopped() {
			Log.d("dwd","Usb Topped");
		}

		@Override
		public void onErrorLooperRunningAlready() {
			Log.d("dwd","Looper already running!");
		}

		@Override
		public void onDeviceNotFound() {
			if(sUsbController != null){
				sUsbController.stop();
				sUsbController = null;
			}
		}
	};
}