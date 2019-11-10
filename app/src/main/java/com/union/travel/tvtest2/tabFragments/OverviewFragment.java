package com.union.travel.tvtest2.tabFragments;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.union.travel.tvtest2.R;
import com.union.travel.tvtest2.adapter.VerticalWatchAdapter;

public class OverviewFragment extends Fragment {
    private boolean isViewShown;
    public RecyclerView recyclerView;



    String name = "Grzo";
    private TextView titleTxtView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
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
        recyclerView = view.findViewById(R.id.watchesRecyclerView);
        Bundle args = getArguments();
        if (args != null){
            name = args.getString("name");
        }

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



    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewShown = getView() != null && isVisibleToUser;
        Log.d("dwd", "fragment 1 " + isViewShown);
    }
}