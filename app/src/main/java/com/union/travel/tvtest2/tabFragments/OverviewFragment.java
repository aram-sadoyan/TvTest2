package com.union.travel.tvtest2.tabFragments;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.util.Size;
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
import com.union.travel.tvtest2.MainActivity;
import com.union.travel.tvtest2.R;
import com.union.travel.tvtest2.adapter.VerticalWatchAdapter;
import com.union.travel.tvtest2.model.AppSettings;
import com.union.travel.tvtest2.model.Model;
import com.union.travel.tvtest2.model.Price;

import java.util.ArrayList;
import java.util.List;


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


	String name = "Grzo";
	//  private TextView titleTxtView = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_overview, container, false);
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
		recyclerView = view.findViewById(R.id.watchesRecyclerView);
		radioGroup = view.findViewById(R.id.size_radioGroup);
		plusView = view.findViewById(R.id.icPlus);
		compareTxtView = view.findViewById(R.id.selectForComapirTxtView);
		arrowDownView = view.findViewById(R.id.arrowDownView);
		gridLayout = view.findViewById(R.id.overviewGrid);
		mainIcView = view.findViewById(R.id.watchId);
		plusView.setOnClickListener(compareClickListener);
		compareTxtView.setOnClickListener(compareClickListener);

		model = AppSettings.getInstance().getModelById(1);
		if (model != null) {
			prices = model.getPrices();
		}


		initGridLayoutForColors();


		//  titleTxtView.setText(name);

		arrowDownView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("dwd", "arrowDown clicked");
			}
		});

	}

	private void initGridLayoutForColors() {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		for (int i = 0; i < 10; i++) {
			View v = inflater.inflate(R.layout.overview_grid_item, gridLayout, false);
			v.setTag(i);
			v.setOnClickListener(v1 -> {
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
			gridLayout.addView(v);
		}
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
				LinearLayoutManager.VERTICAL, false));
		recyclerView.setItemViewCacheSize(10);
		recyclerView.setDrawingCacheEnabled(true);
		recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
		recyclerView.setHasFixedSize(true);


		List<String> itemUrls = new ArrayList<>();
		itemUrls.add("https://png.pngtree.com/element_our/20190528/ourmid/pngtree-small-url-icon-opened-on-the-computer-image_1132275.jpg");
		itemUrls.add("https://png.pngtree.com/element_our/20190528/ourmid/pngtree-small-url-icon-opened-on-the-computer-image_1132275.jpg");
		itemUrls.add("https://png.pngtree.com/element_our/20190528/ourmid/pngtree-small-url-icon-opened-on-the-computer-image_1132275.jpg");
		itemUrls.add("https://png.pngtree.com/element_our/20190528/ourmid/pngtree-small-url-icon-opened-on-the-computer-image_1132275.jpg");
		itemUrls.add("https://png.pngtree.com/element_our/20190528/ourmid/pngtree-small-url-icon-opened-on-the-computer-image_1132275.jpg");
		itemUrls.add("https://png.pngtree.com/element_our/20190528/ourmid/pngtree-small-url-icon-opened-on-the-computer-image_1132275.jpg");

		VerticalWatchAdapter verticalWatchAdapter = new VerticalWatchAdapter(itemUrls);
		recyclerView.setAdapter(verticalWatchAdapter);


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

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		// isViewShown = getView() != null && isVisibleToUser;
		// Log.d("dwd", "fragment 1 " + isViewShown);
	}


	View.OnClickListener compareClickListener = v -> {
		Log.d("dwd", "comparing click");
		Activity activity = getActivity();
		((MainActivity) activity).changeTab(0);
	};
}