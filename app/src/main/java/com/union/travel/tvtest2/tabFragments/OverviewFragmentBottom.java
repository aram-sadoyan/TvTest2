package com.union.travel.tvtest2.tabFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.union.travel.tvtest2.R;

public class OverviewFragmentBottom extends Fragment {



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_overview_bottom, container, false);
	}
}