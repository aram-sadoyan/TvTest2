package com.union.travel.tvtest2;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class TabAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private List<InfoTab> infoTabs;

    TabAdapter(FragmentManager fm, List<Fragment> fragments, List<InfoTab> infoTabs) {
        super(fm);
        this.fragments = fragments;
        this.infoTabs = infoTabs;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

//    public void addFragment(Fragment fragment, String title) {
//        mFragmentList.add(fragment);
//        mFragmentTitleList.add(title);
//    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return infoTabs.get(position).getTabNameForLocale();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}