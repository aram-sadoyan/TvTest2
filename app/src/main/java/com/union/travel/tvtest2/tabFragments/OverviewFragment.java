package com.union.travel.tvtest2.tabFragments;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.union.travel.tvtest2.CustomVerticalViewPager;
import com.union.travel.tvtest2.R;
import com.union.travel.tvtest2.adapter.OverViewAdapter;
import com.union.travel.tvtest2.adapter.VerticalWatchAdapter;
import com.union.travel.tvtest2.api.RestClient;
import com.union.travel.tvtest2.api.UserApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OverviewFragment extends Fragment {
	// private boolean isViewShown;
	//public RecyclerView recyclerView;
	RecyclerView recyclerView = null;
	RadioGroup radioGroup = null;


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
		// titleTxtView = view.findViewById(R.id.titleTxtView);
		recyclerView = view.findViewById(R.id.watchesRecyclerView);
		radioGroup = view.findViewById(R.id.size_radioGroup);


		//  titleTxtView.setText(name);

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

        VerticalWatchAdapter verticalWatchAdapter = new VerticalWatchAdapter();
        recyclerView.setAdapter(verticalWatchAdapter);

		RestClient.getInstance(getContext()).getWatchApiService().getUser(2).enqueue(new Callback<UserApiResponse>() {
			@Override
			public void onResponse(Call<UserApiResponse> call, Response<UserApiResponse> response) {
				Log.d("dwd", response.toString());
			}

			@Override
			public void onFailure(Call<UserApiResponse> call, Throwable t) {
				Log.d("dwd", t.getMessage());
			}
		});
//		RestClient.getInstance(getContext()).getWatchApiService().getQiTest().enqueue(new Callback<Response>() {
//			@Override
//			public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//				Log.d("dwd", response.toString());
//			}
//
//			@Override
//			public void onFailure(Call<Response> call, Throwable t) {
//				Log.d("dwd", t.getMessage());
//			}
//		});





		//todo radio button click listener
		radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
			// This will get the radiobutton that has changed in its check state
			RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
			// This puts the value (true/false) into the variable
			boolean isChecked = checkedRadioButton.isChecked();
			// If the radiobutton that has changed in check state is now checked...
			if (isChecked) {
				Log.d("dwd", String.valueOf(checkedId) + " " + checkedRadioButton.getId() + " " + checkedRadioButton.getText());
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
}