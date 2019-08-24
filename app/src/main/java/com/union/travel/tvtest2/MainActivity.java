package com.union.travel.tvtest2;

import android.hardware.usb.UsbDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;

public class MainActivity extends AppCompatActivity implements ArduinoListener {

	Button btn1 = null;
	Button btn2 = null;

	TextView txtv = null;
	TextView txtv2 = null;
	//PendingIntent permissionIntent;


	private static final String ACTION_USB_PERMISSION =
			"com.android.example.USB_PERMISSION";


	Arduino arduino;
	//private int state = STATE_DISCONNECTED;

	@Override
	protected void onStart() {
		super.onStart();
		arduino.setArduinoListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		arduino = new Arduino(this);
		arduino.addVendorId(6790);
		//todo check this new/old var
//		arduino2 = new Arduino(this);
//		arduino2.addVendorId(3725);

		final ScrollView scrollView;
		btn1 = findViewById(R.id.btn1);
		btn2 = findViewById(R.id.btn2);
		txtv = findViewById(R.id.textView);
		txtv2 = findViewById(R.id.textView2);
		scrollView = findViewById(R.id.scrollView);

		scrollView.post(new Runnable() {
			public void run() {
				scrollView.fullScroll(View.FOCUS_DOWN);
			}
		});

//		btn1.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				/**Setting up the Broadcast receiver to request a permission to allow the APP to access the USB device*/
//				IntentFilter filterPermission = new IntentFilter(ACTION_USB_PERMISSION);
//				registerReceiver(usbReceiver, filterPermission);
//				permissionIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(ACTION_USB_PERMISSION), 0);
//				UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
//				HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
//				UsbDevice device1 = deviceList.get("/dev/bus/usb/004/007");
//				UsbDevice device2 = deviceList.get("/dev/bus/usb/006/002");
//				manager.requestPermission(device1, permissionIntent);
//				manager.requestPermission(device2, permissionIntent);
//			}
//		});
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		arduino.unsetArduinoListener();
		arduino.close();

	}

//	private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
//
//		public void onReceive(Context context, Intent intent) {
//			String action = intent.getAction();
//			if (ACTION_USB_PERMISSION.equals(action)) {
//				synchronized (this) {
//					UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
//
//
//					if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//						if(device != null){
//							//call method to set up device communication
//						}
//					}
//					else {
//						Log.d("dwd", "permission denied for device " + device);
//					}
//				}
//			}
//		}
//	};

	@Override
	public void onArduinoAttached(UsbDevice device) {
		arduino.open(device);

	}

	@Override
	public void onArduinoDetached() {

	}

	@Override
	public void onArduinoMessage(byte[] bytes) {
		display("Received: " + new String(bytes));

	}

	@Override
	public void onArduinoOpened() {

	}

	@Override
	public void onUsbPermissionDenied() {

	}


	public void display(final String message) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//txtv.append(message + "\n");
				txtv.setText(message);
			}
		});

	}
}
