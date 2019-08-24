package com.union.travel.tvtest2;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;


import com.union.travel.arduinoConnection.MicroBridge;

import java.util.HashMap;

import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;

public class MainActivity extends AppCompatActivity implements ArduinoListener {

	private int Distance = 0;
	public final String APP_NAME = "arduino54";

	public final byte COMMAND_SEND_TRUE = 1;    // Команда разрешения передачи
	public final byte COMMAND_SEND_FALSE = 2;   // Команда запрета передачи
	public final byte COMMAND_PLAY_BEEP = 3;    // Команда включения буззера

	public final int SYS_COMMAND_DATA = 0;          // Внутренняя команда: передача данных
	public final int SYS_COMMAND_CONNECTED = 1;     // Внутренняя команда: соединение установлено
	public final int SYS_COMMAND_DISCONNECTED = 2;
	ImageView connectedImage;

	MicroBridge microBridge;


	Button btn1 = null;
	Button btn2 = null;

	TextView txtv = null;
	TextView txtv2 = null;
	PendingIntent permissionIntent;


	private static final String ACTION_USB_PERMISSION =
			"com.android.example.USB_PERMISSION";



	//Physicaloid mPhysicaloid; // initialising library
	Handler mHandler = new Handler();


	private PendingIntent mPermissionIntent;
	//private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

	Arduino arduino;
	Arduino arduino2;
	//private int state = STATE_DISCONNECTED;
	private boolean debug = false;

	@Override
	protected void onStart() {
		super.onStart();
		arduino.setArduinoListener(this);
		//arduino.setArduinoListener(ardunioListener1);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		arduino = new Arduino(this);
		arduino.addVendorId(6790);
		arduino2 = new Arduino(this);
		arduino2.addVendorId(3725);

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


		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("dwd","btn 1 clicked");

				if (microBridge != null){
					microBridge.write(new byte[] {(byte) COMMAND_SEND_TRUE});

				}

				mPermissionIntent = PendingIntent.getBroadcast(MainActivity.this,
						0, new Intent(ACTION_USB_PERMISSION), 0);
				/**Setting up the Broadcast receiver to request a permission to allow the APP to access the USB device*/
				IntentFilter filterPermission = new IntentFilter(ACTION_USB_PERMISSION);
				registerReceiver(usbReceiver, filterPermission);


				permissionIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(ACTION_USB_PERMISSION), 0);
				UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
				HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
				UsbDevice device1 = deviceList.get("/dev/bus/usb/004/007");
				UsbDevice device2 = deviceList.get("/dev/bus/usb/006/002");
				manager.requestPermission(device1, permissionIntent);
				manager.requestPermission(device2, permissionIntent);


			}
		});


		btn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				startActivity(new Intent(MainActivity.this, UsbControllerActivity.class));

			}
		});





		arduino2.setArduinoListener(new ArduinoListener() {
			@Override
			public void onArduinoAttached(UsbDevice device) {
				display2("onArduinoAttached: ");

				arduino2.open(device);

			}

			@Override
			public void onArduinoDetached() {
				display2("onArduinoDetached: ");

			}

			@Override
			public void onArduinoMessage(byte[] bytes) {
				display2("Received: "+new String(bytes));

			}

			@Override
			public void onArduinoOpened() {
				display2("onArduinoOpened: ");

			}

			@Override
			public void onUsbPermissionDenied() {
				display2("onUsbPermissionDenied: ");

			}
		});

	}


	@Override
	protected void onDestroy (){
		super.onDestroy();
	//	server.stop();
		arduino.unsetArduinoListener();
		arduino.close();

		arduino2.unsetArduinoListener();
		arduino2.close();
	}

	private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (ACTION_USB_PERMISSION.equals(action)) {
				synchronized (this) {
					UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);


					if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
						if(device != null){
							//call method to set up device communication
						}
					}
					else {
						Log.d("dwd", "permission denied for device " + device);
					}
				}
			}
		}
	};

	@Override
	public void onArduinoAttached(UsbDevice device) {
	//	display("Arduino attached!");
		arduino.open(device);

	}

	@Override
	public void onArduinoDetached() {

	}

	@Override
	public void onArduinoMessage(byte[] bytes) {
		display("Received: "+new String(bytes));

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

	public void display2(final String message) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//txtv.append(message + "\n");
				txtv2.setText(message);
			}
		});

	}


}
