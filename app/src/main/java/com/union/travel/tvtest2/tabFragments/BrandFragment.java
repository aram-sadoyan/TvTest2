package com.union.travel.tvtest2.tabFragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.union.travel.tvtest2.R;
import com.union.travel.tvtest2.adapter.BrandAdapter;

public class BrandFragment extends Fragment {
    private boolean isViewShown;
    private RecyclerView recyclerView = null;
    private BrandAdapter brandAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_brand, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.brandRecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 6);


        recyclerView.addItemDecoration(new BrandGridRecyclerViewDecoration());
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        brandAdapter = new BrandAdapter();
        recyclerView.setAdapter(brandAdapter);




    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewShown = getView() != null && isVisibleToUser;
        Log.d("dwd", "fragment 2 " + isViewShown);
    }


    private class BrandGridRecyclerViewDecoration extends RecyclerView.ItemDecoration {

        private int bottomSpace = 0;

        public BrandGridRecyclerViewDecoration() {
//            Activity activity = getActivity();
//            if (activity != null && !activity.isFinishing()) {
//                bottomSpace = PicsartUtils.convertDpToPixel(94);
//            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int n = parent.getAdapter().getItemCount();
            int k = n % 6;
            if (k == 0) {
                k = 6;
            }
            if (parent.getChildAdapterPosition(view) >= 6) {//get last items for set offset
                Log.d("dwd","1ket " + String.valueOf(parent.getChildAdapterPosition(view)));
                outRect.set(0, 0, 0, 0);
            } else {
                Log.d("dwd","2ket " + String.valueOf(parent.getChildAdapterPosition(view)));
                outRect.set(0, 0, 0, 0);
            }

            if (parent.getChildAdapterPosition(view) < 6) {
                // outRect.top = 16;
               // Log.d("dwd","3ket " + String.valueOf(parent.getChildAdapterPosition(view)));

            } else {
               // Log.d("dwd","4ket " + String.valueOf(parent.getChildAdapterPosition(view)));

                outRect.top = 50;
            }


            if (parent.getChildAdapterPosition(view) % 6 == 0){
              //  outRect.left = 90;
            }

            if (parent.getChildAdapterPosition(view) % 5 == 0){
                 // outRect.right = 90;
            }
//            outRect.left = PicsartUtils.convertDpToPixel(4);
//            outRect.right = PicsartUtils.convertDpToPixel(4);
        }
    }
}