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
		titleTxtView.setText("current model title");

		Runnable r = () -> initSpecTextLayouts(currentSpec);
		//initSpecTextLayouts(currentSpec);

		parentParamsLayout.post(r);

	}

	private String getCurrentModelIcUrl() {
		String mainicUrl = "";


		return mainicUrl;
	}


	private void initSpecTextLayouts(Spec currentSpec) {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		//View view = inflater.inflate(android.R.layout.list_item_recyclerView, parent, false);
		///////// CompabilitySupport 4 field ////////
		GeneralSpec generalSpec = currentSpec.getGeneralSpec();
		CompabilitySpec comSpec = currentSpec.getCompabilitySpec();
		if (generalSpec == null || comSpec == null) {
			return;
		}
		View v = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name1 = v.findViewById(R.id.startText);
		TextView textView1 = v.findViewById(R.id.valueTxtView);
		name1.setText("Android");
		textView1.setText(comSpec.getAndroid() ? "Yes" : "");

		View v2 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name2 = v2.findViewById(R.id.startText);
		TextView textView2 = v2.findViewById(R.id.valueTxtView);
		name2.setText("IOS");
		textView2.setText(comSpec.getIos() ? "Yes" : "");   //////todo set value from Spec -> CompabilitySupport class

		View v3 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name3 = v3.findViewById(R.id.startText);
		TextView textView3 = v3.findViewById(R.id.valueTxtView);
		name3.setText("Windows Phone");
		textView3.setText(comSpec.getWindowsPhone() ? "Yes" : "");   //////todo set value from Spec -> CompabilitySupport class if no set ""

		View v4 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name4 = v4.findViewById(R.id.startText);
		TextView textView4 = v4.findViewById(R.id.valueTxtView);
		name4.setText("Mac");
		textView4.setText(comSpec.getMac() ? "Yes" : "");   //////todo set value from Spec -> CompabilitySupport class if no set ""

		View v5 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name5 = v5.findViewById(R.id.startText);
		TextView textView5 = v5.findViewById(R.id.valueTxtView);
		name5.setText("Windows");
		textView5.setText(comSpec.getWindows() ? "Yes" : "");   //////todo set value from Spec -> CompabilitySupport class if no set ""

		///////// General 20 field ////////
		View generalViewTextLayout = inflater.inflate(R.layout.layout_general_spec, parentParamsLayout, false);

		View v6 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name6 = v6.findViewById(R.id.startText);
		TextView textView6 = v6.findViewById(R.id.valueTxtView);
		name6.setText("Size");
		textView6.setText(generalSpec.getSize());   //////todo set value from Spec -> CompabilitySupport class if no set ""


		View v7 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name7 = v7.findViewById(R.id.startText);
		TextView textView7 = v7.findViewById(R.id.valueTxtView);
		name7.setText("Material");
		textView7.setText(generalSpec.getMaterial());


		View v8 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name8 = v8.findViewById(R.id.startText);
		TextView textView8 = v8.findViewById(R.id.valueTxtView);
		name8.setText("Battery");
		textView8.setText(generalSpec.getBattery());

		View v9 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name9 = v9.findViewById(R.id.startText);
		TextView textView9 = v9.findViewById(R.id.valueTxtView);
		name9.setText("Battery Life");
		textView9.setText(generalSpec.getBatterylife());

		View v10 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name10 = v10.findViewById(R.id.startText);
		TextView textView10 = v10.findViewById(R.id.valueTxtView);
		name10.setText("Water-resistance");
		textView10.setText(generalSpec.getWaterResistance());

		View v11 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name11 = v11.findViewById(R.id.startText);
		TextView textView11 = v11.findViewById(R.id.valueTxtView);
		name11.setText("Weight");
		textView11.setText(generalSpec.getWeight());


		View v12 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name12 = v12.findViewById(R.id.startText);
		TextView textView12 = v12.findViewById(R.id.valueTxtView);
		name12.setText("Sensor");
		textView12.setText(generalSpec.getSensor());


		View v13 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name13 = v13.findViewById(R.id.startText);
		TextView textView13 = v13.findViewById(R.id.valueTxtView);
		name13.setText("Display");
		textView13.setText(generalSpec.getDisplay());


		View v14 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name14 = v14.findViewById(R.id.startText);
		TextView textView14 = v14.findViewById(R.id.valueTxtView);
		name14.setText("Platform");
		textView14.setText(generalSpec.getPlatform());


		View v15 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name15 = v15.findViewById(R.id.startText);
		TextView textView15 = v15.findViewById(R.id.valueTxtView);
		name15.setText("Processor");
		textView15.setText(generalSpec.getProcessor());


		View v16 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name16 = v16.findViewById(R.id.startText);
		TextView textView16 = v16.findViewById(R.id.valueTxtView);
		name16.setText("Memory");
		textView16.setText(generalSpec.getMemory());

		View v17 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name17 = v17.findViewById(R.id.startText);
		TextView textView17 = v17.findViewById(R.id.valueTxtView);
		name17.setText("Connectivity");
		textView17.setText(generalSpec.getConnectivity());

		View v18 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name18 = v18.findViewById(R.id.startText);
		TextView textView18 = v18.findViewById(R.id.valueTxtView);
		name18.setText("Steps");
		textView18.setText(generalSpec.getSteps() ? "Yes" : "");

		View v19 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name19 = v19.findViewById(R.id.startText);
		TextView textView19 = v19.findViewById(R.id.valueTxtView);
		name19.setText("Distance");
		textView19.setText(generalSpec.getDistance() ? "Yes" : "");

		View v20 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name20 = v20.findViewById(R.id.startText);
		TextView textView20 = v20.findViewById(R.id.valueTxtView);
		name20.setText("Calories burned");
		textView20.setText(generalSpec.getColories() ? "Yes" : "");

		View v21 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name21 = v21.findViewById(R.id.startText);
		TextView textView21 = v21.findViewById(R.id.valueTxtView);
		name21.setText("Activity");
		textView21.setText(generalSpec.getActivity() ? "Yes" : "");

		View v22 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name22 = v22.findViewById(R.id.startText);
		TextView textView22 = v22.findViewById(R.id.valueTxtView);
		name22.setText("Floors");
		textView22.setText(generalSpec.getFloors() ? "Yes" : "");

		View v23 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name23 = v23.findViewById(R.id.startText);
		TextView textView23 = v23.findViewById(R.id.valueTxtView);
		name23.setText("Sleep");
		textView23.setText(generalSpec.getSleep() ? "Yes" : "");

		View v24 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView name24 = v24.findViewById(R.id.startText);
		TextView textView24 = v24.findViewById(R.id.valueTxtView);
		name24.setText("Heart rate");
		textView24.setText(generalSpec.getHeart() ? "Yes" : "");

		parentParamsLayout.removeAllViews();

		parentParamsLayout.addView(v);
		parentParamsLayout.addView(v2);
		parentParamsLayout.addView(v3);
		parentParamsLayout.addView(v4);
		parentParamsLayout.addView(v5);
		parentParamsLayout.addView(generalViewTextLayout);
		parentParamsLayout.addView(v6);
		parentParamsLayout.addView(v7);
		parentParamsLayout.addView(v8);
		parentParamsLayout.addView(v9);
		parentParamsLayout.addView(v10);
		parentParamsLayout.addView(v11);
		parentParamsLayout.addView(v12);
		parentParamsLayout.addView(v13);
		parentParamsLayout.addView(v14);
		parentParamsLayout.addView(v15);
		parentParamsLayout.addView(v16);
		parentParamsLayout.addView(v17);
		parentParamsLayout.addView(v18);
		parentParamsLayout.addView(v19);
		parentParamsLayout.addView(v20);
		parentParamsLayout.addView(v21);
		parentParamsLayout.addView(v22);
		parentParamsLayout.addView(v23);
		parentParamsLayout.addView(v24);
	}


}
