package com.union.travel.tvtest3;

import android.hardware.usb.UsbDevice;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.tabs.TabLayout;
import com.physicaloid.lib.Physicaloid;
import com.union.travel.tvtest3.adapter.TabAdapter;
import com.union.travel.tvtest3.api.RestClient;
import com.union.travel.tvtest3.model.AppSettings;
import com.union.travel.tvtest3.model.Brand;
import com.union.travel.tvtest3.model.Model;
import com.union.travel.tvtest3.model.SensorModelData;
import com.union.travel.tvtest3.tabFragments.ComparingPageFragment;
import com.union.travel.tvtest3.tabFragments.DemoVideosFragment;
import com.union.travel.tvtest3.tabFragments.OverviewFragment;
import com.union.travel.tvtest3.tabFragments.BrandFragment;
import com.union.travel.tvtest3.tabFragments.ModelFragment;
import com.union.travel.tvtest3.tabFragments.SpecsFragment;
import com.union.travel.tvtest3.utils.MyExceptionHandler;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ArduinoListener {

	private TabAdapter adapter;
	private TabLayout tabLayout;
	private ViewPager viewPager;
	private SimpleDraweeView appLogoIc = null;
	private CountDownTimer countDownTimer;
	private ExoPlayerManager exoManager;
	private PlayerView playerView = null;
	private TextView txtv = null;
	private TextView txtv2 = null;
	private TextView txtv3 = null;
	private TextView txtv4 = null;
	private Arduino arduino;
	private boolean operationRuning = false;
	private boolean tabsAreShown = false;
	private int currentId = -2;
	private Map<Integer, Watch> watchMap = new HashMap<>();
	private List<Model> allModelList = new ArrayList<>();
	private ArrayList<InfoTab> infoTabs;
	private OverviewFragment overViewFragment;
	private BrandFragment brandFragment;
	private ModelFragment modelFragment;
	private DemoVideosFragment demoVideosFragment;
	private SpecsFragment specsFragment;
	private ComparingPageFragment comparingPageFragment;
	private List<Fragment> fragmentItemList = new ArrayList<>();
	private List<Integer> sensorsList = new ArrayList<>();

	private Physicaloid physicaloid;

	private boolean isDataRecevied = false;
	private List<Brand> brandList = new ArrayList<>();

	private boolean debugBooleanStarted = false;


	private List<String> allVideoList = new ArrayList<>();
	private int currentVideoIndex = -1;

	@Override
	protected void onStart() {
		super.onStart();
		arduino.setArduinoListener(this);

		//initWatchMap();
		setDefaultTabs();

		RestClient.getInstance(getApplicationContext()).getWatchApiService().getBrandList().enqueue(new Callback<List<Brand>>() {
			@Override
			public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
				brandList = response.body();
				if (brandList != null && !brandList.isEmpty()) {
					AppSettings.getInstance().setFirstRequestedData(brandList);

					RestClient.getInstance(getApplicationContext()).getWatchApiService().getSensorId().enqueue(new Callback<List<SensorModelData>>() {
						@Override
						public void onResponse(Call<List<SensorModelData>> call, Response<List<SensorModelData>> response) {
							//Log.d("dwd", "Sensor id succes");

							List<SensorModelData> sensorModelData = response.body();
							if (sensorModelData != null && !sensorModelData.isEmpty()) {
								AppSettings.getInstance().setSensorMoelData(sensorModelData.get(0));
								isDataRecevied = true;


								allVideoList = AppSettings.getInstance().getVideoUrlList();

								//todo remove fro debug
								allVideoList.clear();
								allVideoList.add("https://mysmartech.ru/esiminch.mp4");
								allVideoList.add("https://mysmartech.ru/esiminch.mp4");
								allVideoList.add("https://mysmartech.ru/esiminch.mp4");

								initWatchMap();

								//todo remove comment its for debug, AND remove playerView.setVisibility(View.GONE);
								playerView.setVisibility(View.GONE);
								//startVideo(0);


								//todo remove its fro debug
								proceedSensor(3);

							}

						}

						@Override
						public void onFailure(Call<List<SensorModelData>> call, Throwable t) {
							Log.d("dwd", "Sensor id" + t.getMessage());

						}
					});


				}

			}

			@Override
			public void onFailure(Call<List<Brand>> call, Throwable t) {
				Log.d("dwd", t.getMessage());
			}
		});


	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		viewPager = findViewById(R.id.viewPager);
		tabLayout = findViewById(R.id.tabLayout);
		playerView = findViewById(R.id.exoplayerview_activity_video);
		appLogoIc = findViewById(R.id.app_logo);


		txtv = findViewById(R.id.textView);
		txtv2 = findViewById(R.id.textView2);
		txtv3 = findViewById(R.id.textView3);
		txtv4 = findViewById(R.id.textView4);

		arduino = new Arduino(this);
		arduino.addVendorId(6790);


		//hideVideoWithPLayBtn();
		//visibleWatchLayout(); ///todo

		//move this to another place
		//todo set app time off delay 10000 = 10 secon
//		countDownTimer = new CountDownTimer(180000, 1000) {
//			public void onTick(long millisUntilFinished) {
//				//TODO: Do something every second
//			}
//
//			public void onFinish() {
//
//				closeTabsWithFadeOut();
//
//			}
//		}.start();


	}


	private void startVideo(int index) {
		this.currentVideoIndex = index;
		if (currentVideoIndex == -1){
			return;
		}
		if (exoManager == null) {
			exoManager = ExoPlayerManager.createInstance(this, getApplicationContext(), playerView);
			exoManager.setVideoCallback(new ExoPlayerManager.VideoCallback() {
				@Override
				public void onVideoStart(@NotNull String url) {
					Log.d("dwd","dwdwd");
				}

				@Override
				public void onVideoEnd(@NotNull String url) {
					startVideo(currentVideoIndex);
					Log.d("dwd","dwdwd");

				}

				@Override
				public void onVideoFail(@NotNull String url, @NotNull String errorMsg) {
					startVideo(currentVideoIndex);
				}

				@Override
				public void onVideoBufferingEnd() {
					Log.d("dwd","dwdwd");

				}
			});
		}

		exoManager.toggleMute(true);
		//exoManager.seekToMls(9000);
		if (currentVideoIndex >= allVideoList.size()) {
			currentVideoIndex = 0;
		}
		if (!allVideoList.isEmpty()) {
			exoManager.setVideoPath(allVideoList.get(currentVideoIndex));
		}

		currentVideoIndex = currentVideoIndex + 1;
		if (currentVideoIndex >= allVideoList.size()) {
			currentVideoIndex = 0;
		}
		exoManager.playStream(0L);

	}


	private void initTabLayout() {
		//todo get Brand by Sensor Key
//		Brand brand = brands.get(0);
//		Model currentModel = new Model();
//		for (Model model : brand.getModels()){
//			if (model.getId() == key){
//				currentModel = model;
//			}
//		}

		for (InfoTab tab : infoTabs) {
			//Bundle args = new Bundle();
			TabLayout.Tab newTab = tabLayout.newTab();
			newTab.setText(tab.getTabNameForLocale());
			newTab.setTag(tab);

			if ("tabId1".equals(tab.tabId)) {
				//args.putString("name", watch.getName());
				if (brandFragment == null) {
					brandFragment = new BrandFragment();
					fragmentItemList.add(brandFragment);
				}
				//brandFragment.setArguments(args);
			} else if ("tabId2".equals(tab.tabId)) {
				//args.putString("name", watch.getName());
				if (overViewFragment == null) {
					overViewFragment = new OverviewFragment();
					fragmentItemList.add(overViewFragment);
				}
				//overViewFragment.setArguments(args);
			} else if ("tabId3".equals(tab.tabId)) {
				//args.putString("mainDesc", watch.getMainDescription());
				if (modelFragment == null) {
					modelFragment = new ModelFragment();
					fragmentItemList.add(modelFragment);
				}
				//modelFragment.setArguments(args);
			} else if ("tabId4".equals(tab.tabId)) {

				//	args.putSerializable(AppConstants.EXTRA_SERIALIZABLE_KEY_SPEC, currentModel.getSpec());
				//args.putString("smallDesc", watch.getSmallDescription());
				if (specsFragment == null) {
					specsFragment = new SpecsFragment();
					fragmentItemList.add(specsFragment);
				}
				//specsFragment.setArguments(args);
			} else if ("tabId5".equals(tab.tabId)) {
				//args.putString("smallDesc", watch.getSmallDescription());
				if (comparingPageFragment == null) {
					comparingPageFragment = new ComparingPageFragment();
					fragmentItemList.add(comparingPageFragment);
				}
				//	comparingPageFragment.setArguments(args);
			} else if ("tabId6".equals(tab.tabId)) {
				//args.putString("smallDesc", watch.getSmallDescription());

				if (demoVideosFragment == null) {
					demoVideosFragment = new DemoVideosFragment();
					fragmentItemList.add(demoVideosFragment);
				}
				//demoVideosFragment.setArguments(args);
			}
		}

		adapter = new TabAdapter(getSupportFragmentManager(), fragmentItemList, infoTabs);

		viewPager.setAdapter(adapter);
		//todo check this
		viewPager.setPageTransformer(true, new DefaultTransformer());
		viewPager.setOffscreenPageLimit(6);
		tabLayout.setupWithViewPager(viewPager);
		Log.d("flow", "init Tabs");

		for (int i = 0; i < tabLayout.getTabCount(); i++) {
			View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
			ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
			p.setMargins(0, 0, 100, 0);
			tab.requestLayout();
		}


		viewPager.setVisibility(View.VISIBLE);
		tabLayout.setVisibility(View.VISIBLE);

//		countDownTimer.cancel();
//		countDownTimer.start();

		tabsAreShown = true;

		//todo remove for debug
		viewPager.setCurrentItem(1);

	}


	@Override
	public void onUserInteraction() {
		super.onUserInteraction();
		if (!tabsAreShown) {
			return;
		}
//		countDownTimer.cancel();
//		countDownTimer.start();

		//todo for debug
		if (!debugBooleanStarted) {
			if (isDataRecevied) {
				//todo  		//for debug		//for debug		//for debug		//for debug		//for debug		//for debug		//for debug

				//todo get sensor id Rom PROCESSS when it was picked up
				//AppSettings.getInstance().setCurrentModelIdFromSensorModelData(1);
				//startShowingContent();
				debugBooleanStarted = true; //for debug
			}
		}


	}

	private void setDefaultTabs() {
		infoTabs = new ArrayList<>();
		InfoTab tab1 = new InfoTab();
		tab1.tabId = "tabId1";
		tab1.tabTitle = new HashMap<>();
		tab1.tabTitle.put(Locale.ENGLISH.getLanguage().toLowerCase(), "Brand");
		infoTabs.add(tab1);

		InfoTab tab2 = new InfoTab();
		tab2.tabId = "tabId2";
		tab2.tabTitle = new HashMap<>();
		tab2.tabTitle.put(Locale.ENGLISH.getLanguage().toLowerCase(), "Overview");
		infoTabs.add(tab2);

		InfoTab tab3 = new InfoTab();
		tab3.tabId = "tabId3";
		tab3.tabTitle = new HashMap<>();
		tab3.tabTitle.put(Locale.ENGLISH.getLanguage().toLowerCase(), "Model");
		infoTabs.add(tab3);

		InfoTab tab4 = new InfoTab();
		tab4.tabId = "tabId4";
		tab4.tabTitle = new HashMap<>();
		tab4.tabTitle.put(Locale.ENGLISH.getLanguage().toLowerCase(), "Specs");
		infoTabs.add(tab4);

		InfoTab tab5 = new InfoTab();
		tab5.tabId = "tabId5";
		tab5.tabTitle = new HashMap<>();
		tab5.tabTitle.put(Locale.ENGLISH.getLanguage().toLowerCase(), "Compairing page");
		infoTabs.add(tab5);


		InfoTab tab6 = new InfoTab();
		tab6.tabId = "tabId6";
		tab6.tabTitle = new HashMap<>();
		tab6.tabTitle.put(Locale.ENGLISH.getLanguage().toLowerCase(), "Demo videos");
		infoTabs.add(tab6);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		arduino.unsetArduinoListener();
		arduino.close();
	}


	@Override
	public void onArduinoAttached(UsbDevice device) {
		arduino.open(device);

	}

	@Override
	public void onArduinoDetached() {

	}

	@Override
	public void onArduinoMessage(byte[] bytes) {

//		ByteBuffer wrapped = ByteBuffer.wrap(bytes); // big-endian by default
//		short num = wrapped.getShort();
		//todo
		//txtv3.setText("w " + num);
		Log.d("dwd", "dwd " + new String(bytes));

		display(new String(bytes));
		//  Log.d("dwdd", new String(bytes));

	}

	@Override
	public void onArduinoOpened() {

	}

	@Override
	public void onUsbPermissionDenied() {

	}

	public void display(final String message) {
		runOnUiThread(() -> {
			// txtv.append(message + "\n");
			// Log.d("flow", "message " + message);
			//txtv4.setText("wdw " );

			txtv.setText(message);
			if (operationRuning && !isDataRecevied) {
				return;
			}

		int sensorIdInt = -1;
		switch (message) {
			case "1\r\n":
				sensorIdInt = 1;
				break;
			case "2\r\n":
				sensorIdInt = 2;
				break;
			case "3\r\n":
				sensorIdInt = 3;
				break;
			case "4\r\n":
				sensorIdInt = 4;
				break;
			case "5\r\n":
				sensorIdInt = 5;
				break;
			case "6\r\n":
				sensorIdInt = 6;
				break;
			case "7\r\n":
				sensorIdInt = 7;
				break;
			case "8\r\n":
				sensorIdInt = 8;
				break;
			case "9\r\n":
				sensorIdInt = 9;
				break;
			case "10\r\n":
				sensorIdInt = 10;
				break;
			case "11\r\n":
				sensorIdInt = 11;
				break;
			case "12\r\n":
				sensorIdInt = 12;
				break;
			case "OFF":
			case "OFF\r\n":
				sensorIdInt = -1;
				break;
			default:
				//todo determine 12 SENSOR ID
		}

			sensorsList.add(sensorIdInt);
			if (sensorsList.size() == 1) {
				if (tabsAreShown && sensorIdInt == -1) {

					operationRuning = false;

					//tabsAreShown = false;
					sensorsList.clear();
					currentId = -1;
					return;
				}
				operationRuning = true;
				if (currentId == sensorIdInt) {
					operationRuning = false;
					sensorsList.clear();
					return;
				}
				boolean allEqual = sensorsList.stream().allMatch(sensorsList.get(0)::equals);
				if (allEqual) {
					proceedSensor(sensorIdInt);
				} else {
					operationRuning = false;
				}
				sensorsList.clear();
			}
		});

	}

	public void proceedSensor(int sensorId) {

		Watch watch = watchMap.get(sensorId);

		if (watch == null) {
			operationRuning = false;
			Log.d("dwd", "PRocessSensor, operationRuning = " + operationRuning);

		} else {
			AppSettings.getInstance().setCurrentModelIdFromSensorModelData(sensorId);
			currentId = sensorId;
			hideAndStopVideoWithFadeOut();
			fadeINTabLayouts();
			startShowingContent();
			Log.d("dwd", "PRocessSensor, sensorId = " + sensorId);
			txtv2.setText(" sensor ID = " + sensorId);
		}

	}

	private void hideAndStopVideoWithFadeOut() {
		if (exoManager != null) {
			exoManager.stopPlayer();
			playerView.setVisibility(View.GONE);
			//fadeOutPlayerView();

		}
	}


	private void fadeINTabLayouts() {
		Animation fadeIn = new AlphaAnimation(0f, 1f);
		fadeIn.setDuration(2000);
		fadeIn.setFillAfter(true);
		tabLayout.setVisibility(View.INVISIBLE);
		viewPager.setVisibility(View.INVISIBLE);
		appLogoIc.setVisibility(View.INVISIBLE);
		tabLayout.startAnimation(fadeIn);
		viewPager.startAnimation(fadeIn);
		appLogoIc.startAnimation(fadeIn);
	}

	private void closeTabsWithFadeOut() {
		Animation fadeOut = new AlphaAnimation(1f, 0f);
		fadeOut.setDuration(2000);
		fadeOut.setFillAfter(true);

		tabLayout.startAnimation(fadeOut);
		viewPager.startAnimation(fadeOut);
		appLogoIc.startAnimation(fadeOut);
		fadeOut.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				tabLayout.removeAllTabs();
				playerView.setVisibility(View.VISIBLE);
				if (currentVideoIndex == -1){
					currentVideoIndex = 0;
				}
				startVideo(currentVideoIndex);
				tabsAreShown = false;
				//countDownTimer.cancel();

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

	}


	private void startShowingContent() {

		initTabLayout(); //todo get watch key
		operationRuning = false;
	}


//	private void changeTabsFont() {
//
//		ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
//		int tabsCount = vg.getChildCount();
//		for (int j = 0; j < tabsCount; j++) {
//			ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
//			int tabChildsCount = vgTab.getChildCount();
//			for (int i = 0; i < tabChildsCount; i++) {
//				View tabViewChild = vgTab.getChildAt(i);
//				if (tabViewChild instanceof TextView) {
//					((TextView) tabViewChild).setTypeface(Font.getI, Typeface.NORMAL);
//				}
//			}
//		}
//	}

	public void changeTab(int position) {
		//todo add time delay fro swiping tabs
		viewPager.setCurrentItem(position, true);
	}

	public void voidSetOverviewFragmentFromModelTabSelection() {
		viewPager.setCurrentItem(1, true);
	}


	private void initWatchMap() {

		allModelList = AppSettings.getInstance().getAllModelList();


		watchMap.put(1, new Watch(
						"Watch 1 Lorem ipsum",
						"header1",
						"title1",
						"mainDescription1",
						"smallDescription1",
						"iconUrlMain1",
						"iconUrlMain1"
				)
		);

		watchMap.put(2, new Watch(
						"Watch 2 Lorem ipsum",
						"header2",
						"title2",
						"mainDescription2",
						"smallDescription2",
						"iconUrlMain2",
						"iconUrlMain2"
				)
		);

		watchMap.put(3, new Watch(
						"Watch 3 Lorem ipsum",
						"header3",
						"title3",
						"mainDescription3",
						"smallDescription3",
						"iconUrlMain3",
						"iconUrlMain3"
				)
		);

		watchMap.put(12, new Watch(
						"Watch 4 Lorem ipsum",
						"header4",
						"title4",
						"mainDescription4",
						"smallDescription4",
						"iconUrlMain4",
						"iconUrlMain4"
				)
		);
	}


}
