package com.union.travel.tvtest2.tabFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.union.travel.tvtest2.R;
import com.union.travel.tvtest2.model.AppSettings;
import com.union.travel.tvtest2.model.CompabilitySpec;
import com.union.travel.tvtest2.model.GeneralSpec;
import com.union.travel.tvtest2.model.Model;
import com.union.travel.tvtest2.model.Spec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComparingPageFragment extends Fragment {

	private boolean isViewShown;
	private List<Model> comparingModelList = new ArrayList<>();

	private LinearLayout compairingItemsContainer = null;
	private LinearLayout layoutComparingKeysContainer = null;


	private int compareItemCount = 0;

	//25 constants
	List<String> stringKeys = Arrays.asList(
			"Android",
			"Ios",
			"Windows Phone", "Mac",
			"Windows", "Size",
			"Material", "Battery", "Battery life",
			"Water-resistance",
			"Weight",
			"Sensor", "Display",
			"Screen size",
			"Platform",
			"Processor", "Memory",
			"Connectivity",
			"Steps",
			"Distance",
			"Calories burned", "Activity",
			"Floors",
			"Sleep",
			"Heart rate");

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_comparing_page, container, false);


	}


	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		compairingItemsContainer = view.findViewById(R.id.layoutComparingContainer);
		layoutComparingKeysContainer = view.findViewById(R.id.layoutComparingKeys);

		comparingModelList = AppSettings.getInstance().getComparingModelList();
		compareItemCount = comparingModelList.size();

		initComparingItems();
		initComparingKeyItems();

	}


	private void initComparingItems() {
		LayoutInflater inflater = LayoutInflater.from(getContext());

		if (compareItemCount > 0) {
			ViewGroup v = (ViewGroup) inflater.inflate(R.layout.layout_comparing_item, compairingItemsContainer, false);
			((LinearLayout.LayoutParams) v.getLayoutParams()).weight = 1;

			compairingItemsContainer.addView(v);
		}

		if (compareItemCount > 1) {
			ViewGroup v2 = (ViewGroup) inflater.inflate(R.layout.layout_comparing_item, compairingItemsContainer, false);
			((LinearLayout.LayoutParams) v2.getLayoutParams()).weight = 1;


			compairingItemsContainer.addView(v2);
		}

		if (compareItemCount > 2) {
			ViewGroup v3 = (ViewGroup) inflater.inflate(R.layout.layout_comparing_item, compairingItemsContainer, false);
			((LinearLayout.LayoutParams) v3.getLayoutParams()).weight = 1;


			compairingItemsContainer.addView(v3);
		}

	}

	private void initComparingKeyItems() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		List<String> valuesList1 = new ArrayList<>();
		List<String> valuesList2 = new ArrayList<>();
		List<String> valuesList3 = new ArrayList<>();

		if (compareItemCount > 0) {
			valuesList1 = getCreatedValuesList(valuesList1, 0);
		}

		if (compareItemCount > 1) {
			valuesList2 = getCreatedValuesList(valuesList2, 1);

		}
		if (compareItemCount > 2) {
			valuesList3 = getCreatedValuesList(valuesList3, 2);
		}

		@LayoutRes int resource = R.layout.new_comparing_value_item_for_1;
		switch (compareItemCount) {
			case 1:
				resource = R.layout.new_comparing_value_item_for_1;
				break;
			case 2:
				resource = R.layout.new_comparing_value_item_for_2;
				break;
			case 3:
				resource = R.layout.new_comparing_value_item_for_3;
				break;
		}

		for (int i = 0; i < stringKeys.size(); i++) {
			View comparingValuesParentView = inflater.inflate(resource, layoutComparingKeysContainer, false);
			TextView keyTxtView = comparingValuesParentView.findViewById(R.id.keyKeyTxtview);
			keyTxtView.setText(stringKeys.get(i));

			if (compareItemCount > 0) {
				TextView valueTxtView = comparingValuesParentView.findViewById(R.id.value1);
				valueTxtView.setText(valuesList1.get(i));
				if (compareItemCount > 1) {
					TextView valueTxtView2 = comparingValuesParentView.findViewById(R.id.value2);
					valueTxtView2.setText(valuesList2.get(i));
				}
				if (compareItemCount > 2) {
					TextView valueTxtView3 = comparingValuesParentView.findViewById(R.id.value3);
					valueTxtView3.setText(valuesList3.get(i));
				}
			}
			layoutComparingKeysContainer.addView(comparingValuesParentView);

		}


	}

	private List<String> getCreatedValuesList(List<String> valuesList1, int position) {
		Model model = comparingModelList.get(position);
		Spec spec = model.getSpec();
		CompabilitySpec compabilitySpec = spec.getCompabilitySpec();
		GeneralSpec generalSpec = spec.getGeneralSpec();

		boolean compabilitySpecIsNotNull = compabilitySpec != null;

		valuesList1.add((compabilitySpecIsNotNull && compabilitySpec.getAndroid()) ? "Yes" : "");  //todo add non nulls
		valuesList1.add(compabilitySpec.getIos() ? "Yes" : "");
		valuesList1.add(compabilitySpec.getWindowsPhone() ? "Yes" : "");
		valuesList1.add(compabilitySpec.getMac() ? "Yes" : "");
		valuesList1.add(compabilitySpec.getWindows() ? "Yes" : "");


		valuesList1.add(generalSpec.getSize());
		valuesList1.add(generalSpec.getMaterial());
		valuesList1.add(generalSpec.getBattery());
		valuesList1.add(generalSpec.getBatterylife());
		valuesList1.add(generalSpec.getWaterResistance());
		valuesList1.add(generalSpec.getWeight());
		valuesList1.add(generalSpec.getSensor());
		valuesList1.add(generalSpec.getDisplay());
		valuesList1.add(generalSpec.getScreenSize());
		valuesList1.add(generalSpec.getPlatform());
		valuesList1.add(generalSpec.getProcessor());
		valuesList1.add(generalSpec.getMemory());
		valuesList1.add(generalSpec.getConnectivity());
		valuesList1.add(generalSpec.getSteps() ? "Yes" : "");
		valuesList1.add(generalSpec.getDistance() ? "Yes" : "");
		valuesList1.add(generalSpec.getColories() ? "Yes" : "");
		valuesList1.add(generalSpec.getActivity() ? "Yes" : "");
		valuesList1.add(generalSpec.getFloors() ? "Yes" : "");
		valuesList1.add(generalSpec.getSleep() ? "Yes" : "");
		valuesList1.add(generalSpec.getHeart() ? "Yes" : "");

		return valuesList1;
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);


	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		isViewShown = getView() != null && isVisibleToUser;
		Log.d("dwd", "fragment 2 " + isViewShown);
	}


}
