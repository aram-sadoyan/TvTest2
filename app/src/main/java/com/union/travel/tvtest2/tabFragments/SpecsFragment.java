package com.union.travel.tvtest2.tabFragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.drawee.view.SimpleDraweeView;
import com.union.travel.tvtest2.FrescoLoader;
import com.union.travel.tvtest2.R;
import com.union.travel.tvtest2.model.AppSettings;
import com.union.travel.tvtest2.model.CompabilitySpec;
import com.union.travel.tvtest2.model.GeneralSpec;
import com.union.travel.tvtest2.model.Model;
import com.union.travel.tvtest2.model.Spec;

import java.util.concurrent.atomic.AtomicBoolean;

public class SpecsFragment extends Fragment {

	private boolean isViewShown;
	private LinearLayout parentParamsLayout;
	private SimpleDraweeView icView = null;
	private TextView nameTxtView = null;
	private TextView titleTxtView = null;
	private Spec currentSpec = null;

	private AtomicBoolean dataIsSelectedFromHint = new AtomicBoolean();


	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		isViewShown = getView() != null && isVisibleToUser;
		if (isViewShown) {
			Log.d("dwd", "fragment 2 " + isViewShown);
			initSpecFragment();
			dataIsSelectedFromHint.set(true);
		} else {
			dataIsSelectedFromHint.set(false);

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_specs, container, false);
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		parentParamsLayout = view.findViewById(R.id.parentLayoutContainer);
		icView = view.findViewById(R.id.watchId);
		nameTxtView = view.findViewById(R.id.nameTxtView);
		titleTxtView = view.findViewById(R.id.titelTxtView);

		if (!dataIsSelectedFromHint.get()) {
			initSpecFragment();
		}


	}

	private void initSpecFragment() {
		currentSpec = AppSettings.getInstance().getCurrentSpec();

		if (currentSpec == null) {
			return;
		}

		//todo here we need current selected model
		FrescoLoader frescoLoader = new FrescoLoader();
		frescoLoader.loadWithParams(Uri.parse(getCurrentModelIcUrl()), icView, false);

		Model currentModel = AppSettings.getInstance().getCurrentModel();
		nameTxtView.setText(currentModel.getName());
		//todo set current selected color name
		titleTxtView.setText(AppSettings.getInstance().getCurrentModelColorTitle());

		initSpecTextLayouts(currentSpec);

	}

	private String getCurrentModelIcUrl() {
		return AppSettings.getInstance().getCurentIcUrl();
	}


	private void initSpecTextLayouts(Spec currentSpec) {
		LayoutInflater inflater = LayoutInflater.from(getActivity());

		///////// CompabilitySupport 4 field ////////
		GeneralSpec generalSpec = currentSpec.getGeneralSpec();
		CompabilitySpec comSpec = currentSpec.getCompabilitySpec();
		if (generalSpec == null || comSpec == null) {
			return;
		}

		parentParamsLayout.removeAllViews();

		View compatibilitySupport = inflater.inflate(R.layout.compatibility_support_layout, parentParamsLayout, false);
		parentParamsLayout.addView(compatibilitySupport);

		String str14 = generalSpec.getPlatform();
		if (!str14.isEmpty()) {
			View v14 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name14 = v14.findViewById(R.id.startText);
			TextView textView14 = v14.findViewById(R.id.valueTxtView);
			name14.setText("Platform");
			textView14.setText(str14);
			parentParamsLayout.addView(v14);
		}

		String str1 = comSpec.getAndroid() ? "Yes" : "";
		if (!str1.isEmpty()) {
			View v = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name1 = v.findViewById(R.id.startText);
			TextView textView1 = v.findViewById(R.id.valueTxtView);
			name1.setText("Android");
			textView1.setText(str1);
			parentParamsLayout.addView(v);
		}


		String str2 = comSpec.getIos() ? "Yes" : "";
		if (!str2.isEmpty()) {
			View v2 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name2 = v2.findViewById(R.id.startText);
			TextView textView2 = v2.findViewById(R.id.valueTxtView);
			name2.setText("IOS");
			textView2.setText(str2);
			parentParamsLayout.addView(v2);

		}


		String str3 = comSpec.getWindowsPhone() ? "Yes" : "";
		if (!str3.isEmpty()) {
			View v3 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name3 = v3.findViewById(R.id.startText);
			TextView textView3 = v3.findViewById(R.id.valueTxtView);
			name3.setText("Windows Phone");
			textView3.setText(comSpec.getWindowsPhone() ? "Yes" : "");
			parentParamsLayout.addView(v3);
		}


		String str4 = comSpec.getMac() ? "Yes" : "";
		if (!str4.isEmpty()) {
			View v4 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name4 = v4.findViewById(R.id.startText);
			TextView textView4 = v4.findViewById(R.id.valueTxtView);
			name4.setText("Mac");
			textView4.setText(str4);
			parentParamsLayout.addView(v4);

		}


		String str5 = comSpec.getWindows() ? "Yes" : "";
		if (!str5.isEmpty()) {
			View v5 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name5 = v5.findViewById(R.id.startText);
			TextView textView5 = v5.findViewById(R.id.valueTxtView);
			name5.setText("Windows");
			textView5.setText(str5);
			parentParamsLayout.addView(v5);

		}


		///////// General 20 field ////////
		View generalViewTextLayout = inflater.inflate(R.layout.layout_general_spec, parentParamsLayout, false);
		parentParamsLayout.addView(generalViewTextLayout);

		String str6 = generalSpec.getSize();
		if (!str6.isEmpty()) {
			View v6 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name6 = v6.findViewById(R.id.startText);
			TextView textView6 = v6.findViewById(R.id.valueTxtView);
			name6.setText("Size");
			textView6.setText(str6);
			parentParamsLayout.addView(v6);
		}

		String str7 = generalSpec.getMaterial();
		if (!str7.isEmpty()) {
			View v7 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name7 = v7.findViewById(R.id.startText);
			TextView textView7 = v7.findViewById(R.id.valueTxtView);
			name7.setText("Material");
			textView7.setText(str7);
			parentParamsLayout.addView(v7);
		}


		String str8 = generalSpec.getBattery();
		if (!str8.isEmpty()) {
			View v8 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name8 = v8.findViewById(R.id.startText);
			TextView textView8 = v8.findViewById(R.id.valueTxtView);
			name8.setText("Battery");
			textView8.setText(str8);
			parentParamsLayout.addView(v8);
		}

		String str9 = generalSpec.getBatterylife();
		if (!str9.isEmpty()) {
			View v9 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name9 = v9.findViewById(R.id.startText);
			TextView textView9 = v9.findViewById(R.id.valueTxtView);
			name9.setText("Battery Life");
			textView9.setText(str9);
			parentParamsLayout.addView(v9);
		}

		String str10 = generalSpec.getWaterResistance();
		if (!str10.isEmpty()) {
			View v10 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name10 = v10.findViewById(R.id.startText);
			TextView textView10 = v10.findViewById(R.id.valueTxtView);
			name10.setText("Water-resistance");
			textView10.setText(str10);
			parentParamsLayout.addView(v10);
		}


		String str13 = generalSpec.getDisplay();
		if (!str13.isEmpty()) {
			View v13 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name13 = v13.findViewById(R.id.startText);
			TextView textView13 = v13.findViewById(R.id.valueTxtView);
			name13.setText("Display");
			textView13.setText(str13);
			parentParamsLayout.addView(v13);
		}

		String str15 = generalSpec.getProcessor();
		if (!str15.isEmpty()) {
			View v15 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name15 = v15.findViewById(R.id.startText);
			TextView textView15 = v15.findViewById(R.id.valueTxtView);
			name15.setText("Processor");
			textView15.setText(str15);
			parentParamsLayout.addView(v15);
		}


		String str16 = generalSpec.getMemory();
		if (!str16.isEmpty()) {
			View v16 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name16 = v16.findViewById(R.id.startText);
			TextView textView16 = v16.findViewById(R.id.valueTxtView);
			name16.setText("Memory");
			textView16.setText(str16);
			parentParamsLayout.addView(v16);
		}

		String str17 = generalSpec.getConnectivity();
		if (!str17.isEmpty()) {
			View v17 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name17 = v17.findViewById(R.id.startText);
			TextView textView17 = v17.findViewById(R.id.valueTxtView);
			name17.setText("Connectivity");
			textView17.setText(str17);
			parentParamsLayout.addView(v17);
		}

		String str18 = generalSpec.getSteps() ? "Yes" : "";
		if (!str18.isEmpty()) {
			View v18 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name18 = v18.findViewById(R.id.startText);
			TextView textView18 = v18.findViewById(R.id.valueTxtView);
			name18.setText("Steps");
			textView18.setText(str18);
			parentParamsLayout.addView(v18);
		}


		String str19 = generalSpec.getDistance() ? "Yes" : "";
		if (!str19.isEmpty()) {
			View v19 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name19 = v19.findViewById(R.id.startText);
			TextView textView19 = v19.findViewById(R.id.valueTxtView);
			name19.setText("Distance");
			textView19.setText(str19);
			parentParamsLayout.addView(v19);
		}

		String str20 = generalSpec.getColories() ? "Yes" : "";
		if (!str20.isEmpty()) {
			View v20 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name20 = v20.findViewById(R.id.startText);
			TextView textView20 = v20.findViewById(R.id.valueTxtView);
			name20.setText("Calories burned");
			textView20.setText(str20);
			parentParamsLayout.addView(v20);
		}

		String str21 = generalSpec.getActivity() ? "Yes" : "";
		if (!str21.isEmpty()) {
			View v21 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name21 = v21.findViewById(R.id.startText);
			TextView textView21 = v21.findViewById(R.id.valueTxtView);
			name21.setText("Activity");
			textView21.setText(str21);
			parentParamsLayout.addView(v21);
		}

		String str22 = generalSpec.getFloors() ? "Yes" : "";
		if (!str22.isEmpty()) {
			View v22 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name22 = v22.findViewById(R.id.startText);
			TextView textView22 = v22.findViewById(R.id.valueTxtView);
			name22.setText("Floors");
			textView22.setText(str22);
			parentParamsLayout.addView(v22);
		}

		String str23 = generalSpec.getSleep() ? "Yes" : "";
		if (!str22.isEmpty()) {
			View v23 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name23 = v23.findViewById(R.id.startText);
			TextView textView23 = v23.findViewById(R.id.valueTxtView);
			name23.setText("Sleep");
			textView23.setText(str23);
			parentParamsLayout.addView(v23);
		}

		String str24 = generalSpec.getHeart() ? "Yes" : "";
		if (!str24.isEmpty()) {
			View v24 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
			TextView name24 = v24.findViewById(R.id.startText);
			TextView textView24 = v24.findViewById(R.id.valueTxtView);
			name24.setText("Heart rate");
			textView24.setText(str24);
			parentParamsLayout.addView(v24);
		}
	}


}
