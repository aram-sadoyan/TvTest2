package com.union.travel.tvtest2.adapter;




import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.union.travel.tvtest2.InfoTab;

import java.util.List;

public class TabAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private List<InfoTab> infoTabs;

    public TabAdapter(FragmentManager fm, List<Fragment> fragments, List<InfoTab> infoTabs) {
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