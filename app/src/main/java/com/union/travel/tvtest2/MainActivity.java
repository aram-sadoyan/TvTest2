package com.union.travel.tvtest2;

import android.hardware.usb.UsbDevice;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;

public class MainActivity extends AppCompatActivity implements ArduinoListener {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private LottieAnimationView lottieAnimationView;

    TextView txtv = null;
    TextView txtv2 = null;

    Arduino arduino;
    private boolean operationRuning = false;
    private boolean tabsAreShown = false;
    private int currentId = -2;
    Map<Integer, Watch> watchMap = new HashMap<>();

    private ArrayList<InfoTab> infoTabs;
    private TabFragment1 tabFragment1;
    private TabFragment2 tabFragment2;
    private TabFragment3 tabFragment3;

    private List<Fragment> fragmentItemList = new ArrayList<>();


    private List<Integer> sensorsList = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        arduino.setArduinoListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        watchMap.put(1, new Watch(
                        "name1",
                        "header1",
                        "title1",
                        "mainDescription1",
                        "smallDescription1",
                        "iconUrlMain1",
                        "iconUrlMain1"
                )
        );

        watchMap.put(2, new Watch(
                        "name2",
                        "header2",
                        "title2",
                        "mainDescription2",
                        "smallDescription2",
                        "iconUrlMain2",
                        "iconUrlMain2"
                )
        );

        watchMap.put(3, new Watch(
                        "name3",
                        "header3",
                        "title3",
                        "mainDescription3",
                        "smallDescription3",
                        "iconUrlMain3",
                        "iconUrlMain3"
                )
        );

        watchMap.put(4, new Watch(
                        "name4",
                        "header4",
                        "title4",
                        "mainDescription4",
                        "smallDescription4",
                        "iconUrlMain4",
                        "iconUrlMain4"
                )
        );

        watchMap.put(5, new Watch(
                        "name5",
                        "header5",
                        "title5",
                        "mainDescription5",
                        "smallDescription5",
                        "iconUrlMain5",
                        "iconUrlMain5"
                )
        );


        setContentView(R.layout.activity_main);
        lottieAnimationView = findViewById(R.id.lmi_menu_item);
        lottieAnimationView.playAnimation();

        lottieAnimationView.loop(true);
        //lottieAnimationView.setRepeatMode(LottieDrawable.REVERSE);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);


        arduino = new Arduino(this);
        arduino.addVendorId(6790);


        txtv = findViewById(R.id.textView);
        txtv2 = findViewById(R.id.textView2);

        setDefaultTabs();


    }

    private void initTabLayout(Watch watch) {
        for (InfoTab tab : infoTabs) {
            Bundle args = new Bundle();
            TabLayout.Tab newTab = tabLayout.newTab();
            newTab.setText(tab.getTabNameForLocale());
            newTab.setTag(tab);
            if ("tabId1".equals(tab.tabId)) {
                //todo args put data for tabId1
                args.putString("name", watch.getName());
                if (tabFragment1 == null) {
                    tabFragment1 = new TabFragment1();
                    fragmentItemList.add(tabFragment1);
                }
                tabFragment1.setArguments(args);
            } else if ("tabId2".equals(tab.tabId)) {
                //todo args put data for tabId2
//                args.putParcelable(ShopConstants.SHOP_GENERIC_QUERY, ShopPackageQuery.getInstance().hasTag(category).purchased(false)
//                        .orderBy(ShopPackageQuery.OrderBy.IS_PURCHASED_ASC_IS_PURCHASED_WITH_PICSART_ASC_INSTALLED_DESC_ITEM_INDEX_ASC));
                args.putString("mainDesc", watch.getMainDescription());

                if (tabFragment2 == null) {
                    tabFragment2 = new TabFragment2();
                    fragmentItemList.add(tabFragment2);
                }
                tabFragment2.setArguments(args);
            } else if ("tabId3".equals(tab.tabId)) {
                //todo args put data for tabId3
//                args.putParcelable(ShopConstants.SHOP_GENERIC_QUERY, ShopPackageQuery.getInstance().hasTag(category).purchased(false)
//                        .orderBy(ShopPackageQuery.OrderBy.IS_PURCHASED_ASC_IS_PURCHASED_WITH_PICSART_ASC_INSTALLED_DESC_ITEM_INDEX_ASC));
                args.putString("smallDesc", watch.getSmallDescription());

                if (tabFragment3 == null) {
                    tabFragment3 = new TabFragment3();
                    fragmentItemList.add(tabFragment3);
                }
                tabFragment2.setArguments(args);
            }
        }

        adapter = new TabAdapter(getSupportFragmentManager(), fragmentItemList, infoTabs);

        viewPager.setAdapter(adapter);
        //todo check this
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        Log.d("flow","init Tabs");

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 50, 0);
            tab.requestLayout();
        }


        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        tabsAreShown = true;

    }


    private void setDefaultTabs() {
        infoTabs = new ArrayList<>();
        InfoTab tab1 = new InfoTab();
        tab1.tabId = "tabId1";
        tab1.tabTitle = new HashMap<>();
        tab1.tabTitle.put(Locale.ENGLISH.getLanguage().toLowerCase(), "tab1EnglishTitle");
        infoTabs.add(tab1);

        InfoTab tab2 = new InfoTab();
        tab2.tabId = "tabId2";
        tab2.tabTitle = new HashMap<>();
        tab2.tabTitle.put(Locale.ENGLISH.getLanguage().toLowerCase(), "tab2EnglishTitle");
        infoTabs.add(tab2);

        InfoTab tab3 = new InfoTab();
        tab3.tabId = "tabId3";
        tab3.tabTitle = new HashMap<>();
        tab3.tabTitle.put(Locale.ENGLISH.getLanguage().toLowerCase(), "tab3EnglishTitle");
        infoTabs.add(tab3);
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

            txtv.setText(message);
            if (operationRuning) {
                return;
            }

           // int count = 0;

            int sensorIdInt = -1;
            switch (message) {
                case "d1":
                    sensorIdInt = 1;
                    break;
                case "d2\r\n":
                    sensorIdInt = 2;
                    break;
                case "d3\r\n":
                    sensorIdInt = 3;
                    break;
                case "d4\r\n":
                    sensorIdInt = 4;
                    break;
                case "d5\r\n":
                    sensorIdInt = 5;
                    break;
                case "d6":
                    sensorIdInt = 6;
                    break;
                case "d7\r\n":
                    sensorIdInt = 7;
                    break;
                case "d8":
                    sensorIdInt = 8;
                    break;
                case "d9":
                    sensorIdInt = 9;
                    break;
                case "d10":
                    sensorIdInt = 10;
                    break;
                case "d11":
                    sensorIdInt = 11;
                    break;
                case "d12":
                    sensorIdInt = 12;
                    break;
                case "OFF":
                    sensorIdInt = -1;
                    break;
                default:
            }
           // Log.d("flow","sensor list size " + sensorsList.size() + " " + sensorIdInt);

            sensorsList.add(sensorIdInt);
            if (sensorsList.size() == 1) {
                if (tabsAreShown && sensorIdInt == -1) {
                    operationRuning = true;
                    closeTabs();
                    operationRuning = false;

                    tabsAreShown = false;
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
        } else {
            currentId = sensorId;
            startShowingContent(watch);
        }

    }


    private void closeTabs() {
        Log.d("flow","close Tabs");

        tabLayout.removeAllTabs();
        viewPager.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
    }

    private void startShowingContent(Watch watch) {
        //todo start showing fragments
        initTabLayout(watch);
        operationRuning = false;
    }
}
