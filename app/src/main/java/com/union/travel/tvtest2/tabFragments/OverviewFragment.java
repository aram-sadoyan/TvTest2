package com.union.travel.tvtest2.tabFragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.union.travel.tvtest2.FrescoLoader;
import com.union.travel.tvtest2.MainActivity;
import com.union.travel.tvtest2.R;
import com.union.travel.tvtest2.adapter.VerticalWatchAdapter;
import com.union.travel.tvtest2.model.AppSettings;
import com.union.travel.tvtest2.model.Color;
import com.union.travel.tvtest2.model.Model;
import com.union.travel.tvtest2.model.Price;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class OverviewFragment extends Fragment {
	private RecyclerView recyclerView = null;
	private RadioGroup radioGroup = null;
	private SimpleDraweeView plusView = null;
	private SimpleDraweeView arrowDownView = null;
	private TextView compareTxtView = null;
	private SimpleDraweeView mainIcView = null;

	private GridLayout gridLayout = null;
	private List<Price> prices = new ArrayList<>();
	private Model model = null;
	private List<Color> colorList = new ArrayList<>();

	VerticalWatchAdapter verticalWatchAdapter = null;

	private FrescoLoader frescoLoader = null;
	private TextView titleTxtView = null;
	private TextView nameTxtView = null;
	private TextView colorDescTxtView = null;
	private TextView serialTxtView = null;

	private AtomicBoolean dataIsSelectedFromHint = new AtomicBoolean();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_overview, container, false);
	}


	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		boolean isViewShown = getView() != null && isVisibleToUser;
		if (isViewShown) {
			//TODO SET Fragment components when is from global clicks
			boolean isSelectedFromModel = AppSettings.getInstance().isSelectedFromModel();
			if (isSelectedFromModel) {
				initOverViewfragment();
				dataIsSelectedFromHint.set(true);
				AppSettings.getInstance().setSelectedFromModel(false);
			}
		} else {
			dataIsSelectedFromHint.set(false);
		}

	}


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("dwd", "resumed");
	}


	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		titleTxtView = view.findViewById(R.id.titleTxtView);
		nameTxtView = view.findViewById(R.id.nameTxtView);
		colorDescTxtView = view.findViewById(R.id.colorDescTxtView);
		serialTxtView = view.findViewById(R.id.serialTxtView);
		recyclerView = view.findViewById(R.id.watchesRecyclerView);
		radioGroup = view.findViewById(R.id.size_radioGroup);
		plusView = view.findViewById(R.id.icPlus);
		compareTxtView = view.findViewById(R.id.selectForComapirTxtView);
		arrowDownView = view.findViewById(R.id.arrowDownView);
		gridLayout = view.findViewById(R.id.overviewGrid);
		mainIcView = view.findViewById(R.id.watchId);
		plusView.setOnClickListener(compareClickListener);
		compareTxtView.setOnClickListener(compareClickListener);


		if (!dataIsSelectedFromHint.get()) {
			initOverViewfragment();
		}


	}

	private void initOverViewfragment() {
		frescoLoader = new FrescoLoader();

		model = AppSettings.getInstance().getModelByModelId();
		if (model != null) {
			prices = model.getPrices();
			colorList = model.getColors();
		}
		if (colorList.isEmpty()) {
			return;
		}

		initNameAndTitles();
		initGridLayoutForColors();
		initOverviewTabVerticalRecView();

		arrowDownView.setOnClickListener(v -> {
			Log.d("dwd", "arrowDown clicked");
			Log.d("dwd", "arrowDown clicked");
		});

	}

	private void initNameAndTitles() {
		titleTxtView.setText(model.getTitle());
		nameTxtView.setText(model.getName());
		colorDescTxtView.setText(model.getColors().get(0).getColorName());
		serialTxtView.setText(model.getSdDescription());
	}

	private void initGridLayoutForColors() {
		if (colorList.isEmpty()) {
			return;
		}

		gridLayout.removeAllViews();
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		for (int i = 0; i < colorList.size(); i++) {
			View v = inflater.inflate(R.layout.overview_grid_item, gridLayout, false);
			v.setTag(i);
			v.setOnClickListener(v1 -> {
				int indexOfChild = gridLayout.indexOfChild(v1);
				frescoLoader.loadWithParams(Uri.parse(getColorUrlByPosition(indexOfChild)), mainIcView, false);
				if (verticalWatchAdapter != null) {
					verticalWatchAdapter.setItemsList(colorList.get(indexOfChild).getColorUrls());
					verticalWatchAdapter.notifyDataSetChanged();
				}

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

			SimpleDraweeView colorIc = v.findViewById(R.id.gridItemIc);
			frescoLoader.loadWithParams(Uri.parse(getColorUrlByPosition(i)), colorIc, false);

			gridLayout.addView(v);

			if (i == 0) {
				View childView = gridLayout.getChildAt(0);
				childView.findViewById(R.id.gridUnderLineView).setVisibility(View.VISIBLE);
			}
		}
	}

	private String getColorUrlByPosition(int i) {
		Color color = colorList.get(i);
		if (color != null && !color.getColorUrls().isEmpty()) {
			return color.getColorUrls().get(0);
		} else {
			return "";
		}
	}


	private void initOverviewTabVerticalRecView() {
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
				LinearLayoutManager.VERTICAL, false));
		recyclerView.setItemViewCacheSize(10);
		recyclerView.setDrawingCacheEnabled(true);
		recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
		recyclerView.setHasFixedSize(true);

		List<String> itemUrls = new ArrayList<>(colorList.get(0).getColorUrls());
		verticalWatchAdapter = new VerticalWatchAdapter(itemUrls, icUrl -> {
			frescoLoader.loadWithParams(Uri.parse(icUrl), mainIcView, false);
		});
		recyclerView.setAdapter(verticalWatchAdapter);
	}



	public interface OnItemClickListener {
		void onItemClick(String icUrl);
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);


		//todo radio button click listener
		radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
			// This will get the radiobutton that has changed in its check state
			RadioButton checkedRadioButton = group.findViewById(checkedId);
			// This puts the value (true/false) into the variable
			boolean isChecked = checkedRadioButton.isChecked();
			// If the radiobutton that has changed in check state is now checked...
			if (isChecked) {

				// Changes the textview's text to "Checked: example radiobutton text"
				//tv.setText("Checked:" + checkedRadioButton.getText());
			}
		});

	}


	View.OnClickListener compareClickListener = v -> {
		Log.d("dwd", "comparing click");
		Activity activity = getActivity();
		((MainActivity) activity).changeTab(0);
	};
}