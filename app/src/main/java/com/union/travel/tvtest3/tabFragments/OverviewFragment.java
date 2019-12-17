package com.union.travel.tvtest3.tabFragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
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
import com.union.travel.tvtest3.FrescoLoader;
import com.union.travel.tvtest3.R;
import com.union.travel.tvtest3.adapter.VerticalWatchAdapter;
import com.union.travel.tvtest3.model.AppSettings;
import com.union.travel.tvtest3.model.Color;
import com.union.travel.tvtest3.model.Model;
import com.union.travel.tvtest3.model.Price;
import com.union.travel.tvtest3.model.tabModel.ComparingItemWithTopModel;
import com.union.travel.tvtest3.utils.AppConstants;

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
	private int selectedAdapterPosition = 0;
	//private View transparentView = null;


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
			//boolean isSelectedFromModel = AppSettings.getInstance().isSelectedFromModel();
			//	if (isSelectedFromModel) {
			//todo check this change
			initOverViewFragment();
			dataIsSelectedFromHint.set(true);
			AppSettings.getInstance().setSelectedFromModel(false);
			//}
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
		//Log.d("dwd", "resumed");
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
		//transparentView = view.findViewById(R.id.transparentView);


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

//		arrowDownView.setOnClickListener(v -> {
//		//	Log.d("dwd", "arrowDown clicked");
//		//	Log.d("dwd", "arrowDown clicked");
//		});

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
					if (priceList.isEmpty()) {
						return;
					}
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
		AppSettings.getInstance().setCurrentModelColorTitle(colorTitle);
		serialTxtView.setText(model.getPartNumber());
	}

	private void initGridLayoutForColors() {
		if (colorList.isEmpty()) {
			return;
		}

		gridLayout.removeAllViews();
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		String curentIcUrl = getColorUrlByPosition(0);
		selectedColorUrl = curentIcUrl;
		frescoLoader.loadWithParams(Uri.parse(AppConstants.IMG_URL_PREFFIX + curentIcUrl), mainIcView, false);
		AppSettings.getInstance().setCurrentMainIcUrl(curentIcUrl);

		for (int i = 0; i < colorList.size(); i++) {
			View v = inflater.inflate(R.layout.overview_grid_item, gridLayout, false);
			v.setTag(i);
			//GRID ITEMS CLICK
			v.setOnClickListener(v1 -> {
				int indexOfChild = gridLayout.indexOfChild(v1);
				String colorTitle = getColorNameByPosition(indexOfChild);
				selectedColorTitle = colorTitle;
				colorDescTxtView.setText(colorTitle);
				AppSettings.getInstance().setCurrentModelColorTitle(colorTitle);
				String curentIcUrl2 = getColorUrlByPosition(indexOfChild);
				selectedColorUrl = curentIcUrl2;
				frescoLoader.loadWithParams(Uri.parse(AppConstants.IMG_URL_PREFFIX + curentIcUrl2), mainIcView, false);
				AppSettings.getInstance().setCurrentMainIcUrl(curentIcUrl2);
				if (verticalWatchAdapter != null) {
					//todo for debug pls remove
					//TODO PLS CHEK THIS CASES
//					List<String> itemUrls = new ArrayList<>();
//					itemUrls.addAll(colorList.get(indexOfChild).getColorUrls());
//					itemUrls.addAll(colorList.get(indexOfChild).getColorUrls());
					//
					verticalWatchAdapter.setItemsList(colorList.get(indexOfChild).getColorUrls());
					verticalWatchAdapter.notifyDataSetChanged();
					if (colorList.get(indexOfChild).getColorUrls().size() > 4) {
						arrowDownView.setVisibility(View.VISIBLE);
						arrowDownView.setOnClickListener(arrowDownClickListener);
					} else {
						arrowDownView.setVisibility(View.GONE);
						arrowDownView.setOnClickListener(null);
					}
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
			String curentIcUrl3 = getColorUrlByPosition(i);
			frescoLoader.loadWithParams(Uri.parse(AppConstants.IMG_URL_PREFFIX + curentIcUrl3), colorIc, false);

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
		verticalWatchAdapter = new VerticalWatchAdapter(itemUrls, (icUrl, adapterPosition) -> {
			selectedAdapterPosition = adapterPosition;
			frescoLoader.loadWithParams(Uri.parse(AppConstants.IMG_URL_PREFFIX + icUrl), mainIcView, false);
			AppSettings.getInstance().setCurrentMainIcUrl(icUrl);


		});
		recyclerView.setAdapter(verticalWatchAdapter);
		if (colorList.get(0).getColorUrls().size() > 4) {
			arrowDownView.setVisibility(View.VISIBLE);
			arrowDownView.setOnClickListener(arrowDownClickListener);
		} else {
			arrowDownView.setVisibility(View.GONE);
			arrowDownView.setOnClickListener(null);
		}
	}


	public interface OnItemClickListener {
		void onItemClick(String icUrl, int adapterPosition);
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
		//transparentView.setOnClickListener(null);
	}

	private void setTextNOTAdded() {
		plusView.setVisibility(View.VISIBLE);
		compareTxtView.setText("Add to compare");
		plusView.setOnClickListener(compareClickListener);
		compareTxtView.setOnClickListener(compareClickListener);
		//transparentView.setOnClickListener(compareClickListener);
	}

	private View.OnClickListener arrowDownClickListener = v -> {
		v.setOnClickListener(null);
		int movedPos;

		if (verticalWatchAdapter != null) {
			if (selectedAdapterPosition == verticalWatchAdapter.getItemCount() - 1) {
				movedPos = 0;
			} else {
				movedPos = selectedAdapterPosition + 1;
			}

			recyclerView.smoothScrollToPosition(movedPos);
			int finalMovedPos = movedPos;
			int finalMovedPos1 = movedPos;
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					recyclerView.findViewHolderForAdapterPosition(finalMovedPos).itemView.performClick();
					v.setOnClickListener(arrowDownClickListener);
					selectedAdapterPosition = finalMovedPos1;
				}
			}, 30);
		}
	};

	private View.OnClickListener compareClickListener = v -> {
		AppSettings.getInstance().addToComparingList(model.getSpec(), model.getName(), model.getId(),
				selectedColorTitle, selectedPrice, selectedColorUrl);
		setTextADDED();
	};
}