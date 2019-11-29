package com.union.travel.tvtest2.tabFragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.drawee.view.SimpleDraweeView;
import com.union.travel.tvtest2.FrescoLoader;
import com.union.travel.tvtest2.MainActivity;
import com.union.travel.tvtest2.R;
import com.union.travel.tvtest2.model.AppSettings;
import com.union.travel.tvtest2.model.tabModel.ModelTabModelItem;

import java.util.concurrent.atomic.AtomicBoolean;


public class ModelFragment extends Fragment {
	private boolean isViewShown;
	private SimpleDraweeView arrowRightIc = null;
	private SimpleDraweeView watchIc = null;
	private TextView nameTxtView = null;
	private TextView selectTxtView = null;
	private GridLayout gridLayout = null;

	private ModelTabModelItem modelTabModelItem = null;
	private FrescoLoader frescoLoader = null;
	private String selectedModelName = "";
	private int selectedModelId = -1;

	private AtomicBoolean dataIsSelectedFromHint = new AtomicBoolean();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_model, container, false);
	}


	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		isViewShown = getView() != null && isVisibleToUser;
		Log.d("dwd", "ModelFragment is " + isViewShown);
		if (isViewShown) {
			//TODO SET Fragment components when is from global clicks
			boolean isSelectedFromBrand = AppSettings.getInstance().isSelectedFromBrand();
			if (isSelectedFromBrand) {
				initModelFragment();
				dataIsSelectedFromHint.set(true);
				AppSettings.getInstance().setSelectedFromBrand(false);
			} else {
				dataIsSelectedFromHint.set(false);
			}
		}


	}


	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		frescoLoader = new FrescoLoader();

		arrowRightIc = view.findViewById(R.id.rightArrowView);
		selectTxtView = view.findViewById(R.id.bottomTextView);
		gridLayout = view.findViewById(R.id.modelGridView);
		watchIc = view.findViewById(R.id.watchId);
		nameTxtView = view.findViewById(R.id.nameTxtView);

		if (!dataIsSelectedFromHint.get()) {
			initModelFragment();
		}


	}

	private void initModelFragment() {
		modelTabModelItem = null;
		modelTabModelItem = AppSettings.getInstance().getModelTabItem();
		if (modelTabModelItem == null || modelTabModelItem.getModelItemList().isEmpty()) {

		} else {
			initGridModelItems();

			arrowRightIc.setOnClickListener(selectModelClickListener);
			selectTxtView.setOnClickListener(selectModelClickListener);
		}


	}

	private void initGridModelItems() {
		//setGlobalSelectedModelItemName(0);
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		nameTxtView.setText(getNameStringFromModelByPosition(0));
		gridLayout.removeAllViews();
		selectedModelId = modelTabModelItem.getModelItemList().get(0).getModelId();

		frescoLoader.loadWithParams(Uri.parse(modelTabModelItem.getModelItemList().get(0).getModelUrl()), watchIc, false);

		for (int i = 0; i < modelTabModelItem.getModelItemList().size(); i++) {
			View v = inflater.inflate(R.layout.overview_grid_item, gridLayout, false);
			v.setTag(i);
			v.setOnClickListener(v1 -> {
				int indexOfChild = gridLayout.indexOfChild(v1);
				setGlobalSelectedModelItemName(indexOfChild);
				nameTxtView.setText(getNameStringFromModelByPosition(indexOfChild));
				frescoLoader.loadWithParams(Uri.parse(modelTabModelItem.getModelItemList().get(indexOfChild).getModelUrl()), watchIc, false);
				int count = gridLayout.getChildCount();
				for (int i1 = 0; i1 < count; i1++) {
					View childView = gridLayout.getChildAt(i1);
					if (childView.getTag() == v1.getTag()) {
						childView.findViewById(R.id.gridUnderLineView).setVisibility(View.VISIBLE);
					} else {
						childView.findViewById(R.id.gridUnderLineView).setVisibility(View.INVISIBLE);
					}
				}
			});

			SimpleDraweeView modelGridItemIcView = v.findViewById(R.id.gridItemIc);
			frescoLoader.loadWithParams(Uri.parse(getColorIcUrlByPosition(i)), modelGridItemIcView, false);
			gridLayout.addView(v);

			if (i == 0) {
				View childView = gridLayout.getChildAt(0);
				childView.findViewById(R.id.gridUnderLineView).setVisibility(View.VISIBLE);
			}
		}
	}


	private void setGlobalSelectedModelItemName(int position) {
		selectedModelName = modelTabModelItem.getModelItemList().get(position).getModelName();
		selectedModelId = modelTabModelItem.getModelItemList().get(position).getModelId();
		//AppSettings.getInstance().setCurrentModelId(selectedModelId);
	}


	private String getNameStringFromModelByPosition(int position) {
		return modelTabModelItem.getModelItemList().get(position).getModelName();
	}

	private String getColorIcUrlByPosition(int position) {
		return modelTabModelItem.getModelItemList().get(position).getModelUrl();
	}


	private View.OnClickListener selectModelClickListener = v -> {
		AppSettings.getInstance().setCurrentModelId(selectedModelId);
		AppSettings.getInstance().setSelectedFromModel(true);
		Activity activity = getActivity();
		if (activity != null) {
			((MainActivity) activity).voidSetOverviewFragmentFromModelTabSelection();
		}
	};

}