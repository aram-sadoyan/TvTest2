package com.union.travel.tvtest2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.union.travel.tvtest2.tabFragments.OverViewFragmentTop;
import com.union.travel.tvtest2.tabFragments.OverviewFragmentBottom;

public class CustomVerticalViewPager extends FragmentPagerAdapter {

//	public CustomVerticalViewPager(@NonNull FragmentManager fm) {
//		super(fm);
//	}

	public CustomVerticalViewPager(@NonNull FragmentManager fm, int behavior) {
		super(fm, behavior);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case 0:
				// Your current main fragment showing how to send arguments to fragment
				OverViewFragmentTop myFragment = new OverViewFragmentTop();
				return myFragment;
			case 1:
				// Calling a Fragment without sending arguments
				return new OverviewFragmentBottom();
			default:
				return null;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}



}
