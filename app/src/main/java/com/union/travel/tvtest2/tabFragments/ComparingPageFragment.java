package com.union.travel.tvtest2.tabFragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.net.Uri;
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
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;

import com.facebook.drawee.view.SimpleDraweeView;
import com.union.travel.tvtest2.FrescoLoader;
import com.union.travel.tvtest2.MainActivity;
import com.union.travel.tvtest2.R;
import com.union.travel.tvtest2.model.AppSettings;
import com.union.travel.tvtest2.model.CompabilitySpec;
import com.union.travel.tvtest2.model.GeneralSpec;
import com.union.travel.tvtest2.model.Spec;
import com.union.travel.tvtest2.model.tabModel.ComparingItemWithTopModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ComparingPageFragment extends Fragment {

	private boolean isViewShown;
	private List<ComparingItemWithTopModel> comparingModelList = new ArrayList<>();


	private LinearLayout compairingItemsContainer = null;
	private LinearLayout layoutComparingKeysContainer = null;
	private int compareItemCount = 0;
	private SimpleDraweeView addViewIcView = null;

	//25 constants
	List<String> stringKeys = Arrays.asList(
			"Platform",
			"Android",
			"Ios",
			"Windows Phone", "Mac",
			"Windows",
			"Size",
			"Material",
			"Battery",
			"Battery life",
			"Water-resistance",
			"Display",
			"Display size",
			"Processor",
			"Memory",
			"Connectivity",
			"Steps",
			"Distance",
			"Calories burned",
			"Activity",
			"Floors",
			"Sleep",
			"Heart rate");


	private AtomicBoolean dataIsSelectedFromHint = new AtomicBoolean();
	private Guideline guideline = null;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		isViewShown = getView() != null && isVisibleToUser;
		Log.d("dwd", "fragment 2 " + isViewShown);
		if (isViewShown) {
			initComparingragment();
			dataIsSelectedFromHint.set(true);

		} else {
			dataIsSelectedFromHint.set(false);

		}

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_comparing_page, container, false);


	}


	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		compairingItemsContainer = view.findViewById(R.id.layoutComparingContainer);
		layoutComparingKeysContainer = view.findViewById(R.id.layoutComparingKeys);
		addViewIcView = view.findViewById(R.id.addView);
		guideline = view.findViewById(R.id.guidelineEnd);


		if (!dataIsSelectedFromHint.get()) {
			initComparingragment();
		}


	}


	private void initComparingragment() {
		comparingModelList = AppSettings.getInstance().getComparingItemWithTopModel();

		compareItemCount = comparingModelList.size();

		initComparingItems();
		setGuideLineEnd();
		initComparingKeyItems();
		addViewIcView.setOnClickListener(onAdViewClickListener);
	}

	private void setGuideLineEnd() {
		addViewIcView.setVisibility(View.VISIBLE);
		switch (compareItemCount) {
			case 0:
				guideline.setGuidelineBegin(250);
				break;
			case 1:
				guideline.setGuidelineBegin(600);
				break;
			case 2:
				guideline.setGuidelineBegin(1400);
				break;
			case 3:
				//addViewIcView.setVisibility(View.GONE);
				guideline.setGuidelineBegin(1700);

				break;
		}

	}


	private void initComparingItems() {
		if (compareItemCount == 0) {
			return;
		}
		LayoutInflater inflater = LayoutInflater.from(getContext());
		FrescoLoader frescoLoader = new FrescoLoader();
		compairingItemsContainer.removeAllViews();
		if (compareItemCount > 0) {
			ViewGroup v = (ViewGroup) inflater.inflate(R.layout.layout_comparing_item, compairingItemsContainer, false);

			ComparingItemWithTopModel model = comparingModelList.get(0);
			String icUrl = model.getIcUrl();
			String name = model.getName();
			String title = model.getTitle();
			String price = model.getPrice();
			SimpleDraweeView closeBtnIc = v.findViewById(R.id.closeIcView);
			closeBtnIc.setOnClickListener(v1 -> {
				comparingModelList.remove(0);
				recreateComparingFragmentAfterRemoving();
			});

			SimpleDraweeView modelIc = v.findViewById(R.id.modelIconView);
			frescoLoader.loadWithParams(Uri.parse(icUrl), modelIc, false);

			TextView nameTxtView = v.findViewById(R.id.modelNameTxtView);
			nameTxtView.setText(name);

			TextView titleTxtView = v.findViewById(R.id.modelTitleTxtView);
			titleTxtView.setText(title);

			TextView priceTxtView = v.findViewById(R.id.priceTxtView);
			priceTxtView.setText(price);

			((LinearLayout.LayoutParams) v.getLayoutParams()).weight = 1;
			compairingItemsContainer.addView(v);
		}

		if (compareItemCount > 1) {
			ViewGroup v2 = (ViewGroup) inflater.inflate(R.layout.layout_comparing_item, compairingItemsContainer, false);

			ComparingItemWithTopModel model = comparingModelList.get(1);
			String icUrl = model.getIcUrl();
			String name = model.getName();
			String title = model.getTitle();
			String price = model.getPrice();
			SimpleDraweeView closeBtnIc = v2.findViewById(R.id.closeIcView);
			closeBtnIc.setOnClickListener(v1 -> {
				comparingModelList.remove(1);
				recreateComparingFragmentAfterRemoving();
			});

			SimpleDraweeView modelIc = v2.findViewById(R.id.modelIconView);
			frescoLoader.loadWithParams(Uri.parse(icUrl), modelIc, false);

			TextView nameTxtView = v2.findViewById(R.id.modelNameTxtView);
			nameTxtView.setText(name);

			TextView titleTxtView = v2.findViewById(R.id.modelTitleTxtView);
			titleTxtView.setText(title);

			TextView priceTxtView = v2.findViewById(R.id.priceTxtView);
			priceTxtView.setText(price);

			((LinearLayout.LayoutParams) v2.getLayoutParams()).weight = 1;

			compairingItemsContainer.addView(v2);
		}

		if (compareItemCount > 2) {
			ViewGroup v3 = (ViewGroup) inflater.inflate(R.layout.layout_comparing_item, compairingItemsContainer, false);


			ComparingItemWithTopModel model = comparingModelList.get(2);
			String icUrl = model.getIcUrl();
			String name = model.getName();
			String title = model.getTitle();
			String price = model.getPrice();
			SimpleDraweeView closeBtnIc = v3.findViewById(R.id.closeIcView);
			closeBtnIc.setOnClickListener(v1 -> {
				comparingModelList.remove(2);
				recreateComparingFragmentAfterRemoving();
			});

			SimpleDraweeView modelIc = v3.findViewById(R.id.modelIconView);
			frescoLoader.loadWithParams(Uri.parse(icUrl), modelIc, false);

			TextView nameTxtView = v3.findViewById(R.id.modelNameTxtView);
			nameTxtView.setText(name);

			TextView titleTxtView = v3.findViewById(R.id.modelTitleTxtView);
			titleTxtView.setText(title);

			TextView priceTxtView = v3.findViewById(R.id.priceTxtView);
			priceTxtView.setText(price);


			((LinearLayout.LayoutParams) v3.getLayoutParams()).weight = 1;


			compairingItemsContainer.addView(v3);
		}


		compairingItemsContainer.animate()
				.translationX(0)
				.alpha(1.0f)
				.setDuration(100)
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						super.onAnimationEnd(animation);

					}
				});


	}

	private void recreateComparingFragmentAfterRemoving() {
		compairingItemsContainer.animate()
				.translationX(-compairingItemsContainer.getWidth())
				.alpha(0.0f)
				.setDuration(300)
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						super.onAnimationEnd(animation);
						compairingItemsContainer.removeAllViews();
					}
				});

		layoutComparingKeysContainer.animate()
				.translationX(-layoutComparingKeysContainer.getWidth())
				.alpha(0.0f)
				.setDuration(310)
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						super.onAnimationEnd(animation);
						layoutComparingKeysContainer.removeAllViews();
						initComparingragment();
					}
				});

		//compairingItemsContainer.removeAllViews();
		//layoutComparingKeysContainer.removeAllViews();


	}


	private void initComparingKeyItems() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		List<String> valuesList1 = new ArrayList<>();
		List<String> valuesList2 = new ArrayList<>();
		List<String> valuesList3 = new ArrayList<>();
		layoutComparingKeysContainer.removeAllViews();
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
			View underLView = comparingValuesParentView.findViewById(R.id.value1underline);
			if (i == stringKeys.size()-1){
				underLView.setVisibility(View.INVISIBLE);
			}
			keyTxtView.setText(stringKeys.get(i));
			if (compareItemCount > 0) {
				TextView valueTxtView = comparingValuesParentView.findViewById(R.id.value1);
				if (!valuesList1.isEmpty()) {
					valueTxtView.setText(valuesList1.get(i));

				}
				if (compareItemCount > 1) {
					TextView valueTxtView2 = comparingValuesParentView.findViewById(R.id.value2);
					if (!valuesList2.isEmpty()) {
						valueTxtView2.setText(valuesList2.get(i));
					}
				}
				if (compareItemCount > 2) {
					TextView valueTxtView3 = comparingValuesParentView.findViewById(R.id.value3);
					if (!valuesList3.isEmpty()) {
						valueTxtView3.setText(valuesList3.get(i));

					}
				}
			}
			layoutComparingKeysContainer.addView(comparingValuesParentView);
		}

		layoutComparingKeysContainer.animate()
				.translationX(0)
				.alpha(1.0f)
				.setDuration(100)
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						super.onAnimationEnd(animation);
//						layoutComparingKeysContainer.removeAllViews();
//						initComparingragment();
					}
				});


	}

	private List<String> getCreatedValuesList(List<String> valuesList1, int position) {

		Spec spec = comparingModelList.get(position).getSpec();
		if (spec == null) {
			return new ArrayList<>();
		}
		CompabilitySpec compabilitySpec = spec.getCompabilitySpec();
		GeneralSpec generalSpec = spec.getGeneralSpec();
		if (compabilitySpec == null || generalSpec == null) {
			return new ArrayList<>();
		}

		valuesList1.add(generalSpec.getPlatform());
		valuesList1.add(compabilitySpec.getAndroid() ? "Yes" : "");
		valuesList1.add(compabilitySpec.getIos() ? "Yes" : "");
		valuesList1.add(compabilitySpec.getWindowsPhone() ? "Yes" : "");
		valuesList1.add(compabilitySpec.getMac() ? "Yes" : "");
		valuesList1.add(compabilitySpec.getWindows() ? "Yes" : "");


		valuesList1.add(generalSpec.getSize());
		valuesList1.add(generalSpec.getMaterial());
		valuesList1.add(generalSpec.getBattery());
		valuesList1.add(generalSpec.getBatterylife());
		valuesList1.add(generalSpec.getWaterResistance());
		//valuesList1.add(generalSpec.getWeight());
		//valuesList1.add(generalSpec.getSensor());
		valuesList1.add(generalSpec.getDisplay());
		valuesList1.add(generalSpec.getDisplaySize());
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


	private View.OnClickListener onAdViewClickListener = v -> {
		Activity activity = getActivity();
		((MainActivity) activity).changeTab(0);
	};


}
