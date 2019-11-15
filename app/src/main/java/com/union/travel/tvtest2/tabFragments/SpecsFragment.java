package com.union.travel.tvtest2.tabFragments;

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

import com.union.travel.tvtest2.R;

public class SpecsFragment extends Fragment {

	private boolean isViewShown;
	private LinearLayout parentParamsLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_specs, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		parentParamsLayout = view.findViewById(R.id.parentLayoutContainer);

		LayoutInflater inflater = LayoutInflater.from(getActivity());
		//View view = inflater.inflate(android.R.layout.list_item_recyclerView, parent, false);
		View v = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		View v2 = inflater.inflate(R.layout.layout_params_2, parentParamsLayout, false);
		TextView textView = v2.findViewById(R.id.valueTxtView);
		TextView name = v2.findViewById(R.id.startText);
		name.setText("Bezel material");
		textView.setText("stainless steel, titanium or Diamond-like Carbon (DLC) coated steel");

		parentParamsLayout.addView(v);
		parentParamsLayout.addView(v2);


	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		isViewShown = getView() != null && isVisibleToUser;
		Log.d("dwd", "fragment 2 " + isViewShown);
	}


}
