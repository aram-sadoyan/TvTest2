package com.union.travel.tvtest2.tabFragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.union.travel.tvtest2.FrescoLoader;
import com.union.travel.tvtest2.R;
import com.union.travel.tvtest2.adapter.VerticalWatchAdapter;
import com.union.travel.tvtest2.model.AppSettings;
import com.union.travel.tvtest2.model.Color;
import com.union.travel.tvtest2.model.Model;
import com.union.travel.tvtest2.model.Price;
import com.union.travel.tvtest2.model.tabModel.ComparingItemWithTopModel;

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
	private TextView priceTxtView = null;
	private List<Price> priceList = new ArrayList<>();

	private AtomicBoolean dataIsSelectedFromHint = new AtomicBoolean();


	private String selectedPrice = "";
	private String selectedColorUrl = "";
	private String selectedColorTitle = "";


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
				initOverViewFragment();
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
		priceTxtView = view.findViewById(R.id.priceTxtView);


		if (!dataIsSelectedFromHint.get()) {
			initOverViewFragment();
		}


	}

	private void initOverViewFragment() {
		frescoLoader = new FrescoLoader();
		model = AppSettings.getInstance().getModelByModelId();
		if (model == null) {
			return;
		}
		prices = model.getPrices();
		colorList = model.getColors();
		if (colorList.isEmpty()) {
			return;
		}

		initGridLayoutForColors();
		initOverviewTabVerticalRecView();

		initNameAndTitles();
		initPriceSizeGroup();
		setBottomText(model.getId());

		arrowDownView.setOnClickListener(v -> {
			Log.d("dwd", "arrowDown clicked");
			Log.d("dwd", "arrowDown clicked");
		});

	}

	@SuppressLint("ResourceType")
	private void initPriceSizeGroup() {
		priceList = model.getPrices();
		if (priceList.isEmpty()) {
			return;
		}

		final RadioButton[] rb = new RadioButton[priceList.size()];
		radioGroup.removeAllViews();
		RadioGroup rg = new RadioGroup(getContext()); //create the RadioGroup
		rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
		for (int i = 0; i < priceList.size(); i++) {
			rb[i] = new RadioButton(getContext());
			if (i == 0) {
				rb[i].setChecked(true);
			}
			rb[i].setBackgroundResource(R.drawable.radio_flat_selector);
			RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			lp.leftMargin = 20;
			rb[i].setLayoutParams(lp);
			rb[i].setGravity(Gravity.CENTER);
			rb[i].setButtonDrawable(R.color.transparent);
			rb[i].setTextColor(ContextCompat.getColorStateList(getContext(), R.drawable.radio_flat_text_selector));
			rb[i].setTextSize(18);
			rb[i].setText(priceList.get(i).getSize() + " mm");
			rb[i].setId(i);
			rb[i].setOnCheckedChangeListener(onCheckedChangedListener);
			rg.addView(rb[i]);
		}
		radioGroup.addView(rg);
		String slectedPriceString = priceList.get(0).getPricetext();
		priceTxtView.setText(slectedPriceString);
		selectedPrice = slectedPriceString;
	}

	private final CompoundButton.OnCheckedChangeListener onCheckedChangedListener =
			(buttonView, isChecked) -> {
				if (isChecked) {
					String slectedPriceString = priceList.get(buttonView.getId()).getPricetext();
					priceTxtView.setText(slectedPriceString);
					selectedPrice = slectedPriceString;
				}
			};

	private void initNameAndTitles() {
		String colorTitle = model.getColors().get(0).getColorName();
		selectedColorTitle = colorTitle;
		titleTxtView.setText(model.getTitle());
		nameTxtView.setText(model.getName());
		colorDescTxtView.setText(colorTitle);
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
				String colorTitle = getColorNameByPosition(indexOfChild);
				selectedColorTitle = colorTitle;
				colorDescTxtView.setText(colorTitle);
				String curentIcUrl = getColorUrlByPosition(indexOfChild);
				selectedColorUrl = curentIcUrl;
				frescoLoader.loadWithParams(Uri.parse(curentIcUrl), mainIcView, false);
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
			String curentIcUrl = getColorUrlByPosition(i);
			selectedColorUrl = curentIcUrl;
			frescoLoader.loadWithParams(Uri.parse(curentIcUrl), colorIc, false);

			gridLayout.addView(v);

			if (i == 0) {
				View childView = gridLayout.getChildAt(0);
				childView.findViewById(R.id.gridUnderLineView).setVisibility(View.VISIBLE);
			}
		}
	}

	private String getColorUrlByPosition(int i) {
		if (colorList.isEmpty()) {
			return "";
		}
		Color color = colorList.get(i);
		if (color != null && !color.getColorUrls().isEmpty()) {
			return color.getColorUrls().get(0);
		} else {
			return "";
		}
	}

	private String getColorNameByPosition(int i) {
		if (colorList.isEmpty()) {
			return "";
		}
		Color color = colorList.get(i);
		if (color != null && !color.getColorUrls().isEmpty()) {
			return color.getColorName();
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


	}


	private void setBottomText(int modelId) {
		List<ComparingItemWithTopModel> comparingItemWithTopModelList = AppSettings.getInstance().getComparingItemWithTopModel();

		//TODO get opttimise this get item id
		List<Integer> comparedListid = new ArrayList<>();
		for (ComparingItemWithTopModel model : comparingItemWithTopModelList) {
			comparedListid.add(model.getId());
		}

		boolean currentModelIsAddedForComparing = false;
		for (int comparingId : comparedListid) {
			if (comparingId == modelId) {
				currentModelIsAddedForComparing = true;
			}
		}

		if (currentModelIsAddedForComparing) {
			setTextADDED();
		} else {
			setTextNOTAdded();
		}

	}

	private void setTextADDED() {
		plusView.setVisibility(View.GONE);
		compareTxtView.setText("Added");
		plusView.setOnClickListener(null);
		compareTxtView.setOnClickListener(null);
	}

	private void setTextNOTAdded() {
		plusView.setVisibility(View.VISIBLE);
		compareTxtView.setText("Add to compare");
		plusView.setOnClickListener(compareClickListener);
		compareTxtView.setOnClickListener(compareClickListener);
	}

	View.OnClickListener compareClickListener = v -> {
		AppSettings.getInstance().addToComparingList(model.getSpec(), model.getName(), model.getId(),
				selectedColorTitle, selectedPrice, selectedColorUrl);
		setTextADDED();
	};
}