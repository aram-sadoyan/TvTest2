package com.union.travel.tvtest2;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OverviewFragment extends Fragment {
    private boolean isViewShown;


    String name = "Grzo";
    private TextView titleTxtView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one, container, false);
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
        Bundle args = getArguments();
        if (args != null){
            name = args.getString("name");
        }

      //  titleTxtView.setText(name);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewShown = getView() != null && isVisibleToUser;
        Log.d("dwd", "fragment 1 " + isViewShown);
    }
}