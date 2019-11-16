package com.union.travel.tvtest2.tabFragments;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.union.travel.tvtest2.MainActivity;
import com.union.travel.tvtest2.R;
import com.union.travel.tvtest2.adapter.ModelGridAdapter;
import com.union.travel.tvtest2.adapter.OverViewAdapter;

public class ModelFragment extends Fragment {
    private boolean isViewShown;


    SimpleDraweeView arrowRightIc = null;
    TextView selectTxtView = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_model, container, false);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewShown = getView() != null && isVisibleToUser;
        Log.d("dwd", "FRG 3 " + isViewShown);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // titleTxtView = view.findViewById(R.id.titleTxtView);
        RecyclerView recyclerView = view.findViewById(R.id.modelRecyclerView);

        arrowRightIc = view.findViewById(R.id.rightArrowView);
        selectTxtView = view.findViewById(R.id.bottomTextView);

        arrowRightIc.setOnClickListener(selectModelClickListener);
        selectTxtView.setOnClickListener(selectModelClickListener);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridRecyclerViewDecoration());
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setHasFixedSize(true);
        ModelGridAdapter modelGridAdapter = new ModelGridAdapter();
        recyclerView.setAdapter(modelGridAdapter);


    }



    private class GridRecyclerViewDecoration extends RecyclerView.ItemDecoration {

        private int bottomSpace = 0;

        public GridRecyclerViewDecoration() {
//            Activity activity = getActivity();
//            if (activity != null && !activity.isFinishing()) {
//                bottomSpace = PicsartUtils.convertDpToPixel(94);
//            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int n = parent.getAdapter().getItemCount();
            int k = n % 7;
            if (k == 0) {
                k = 7;
            }
            if (parent.getChildAdapterPosition(view) >= n - k) { //get last items for set offset
                outRect.set(0, 0, 30, 0);
            } else {
                outRect.set(0, 0, 34, 0);
            }
            if (parent.getChildAdapterPosition(view) < 7) {
               // outRect.top = 16;
            } else {
                outRect.top = 42;
            }
//            outRect.left = PicsartUtils.convertDpToPixel(4);
//            outRect.right = PicsartUtils.convertDpToPixel(4);
        }
    }


    View.OnClickListener selectModelClickListener = v -> {
        Log.d("dwd", "comparing click");
        Activity activity = getActivity();
        ((MainActivity) activity).changeTab(4);
    };

}