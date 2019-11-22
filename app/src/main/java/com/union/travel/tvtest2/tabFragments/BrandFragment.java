package com.union.travel.tvtest2.tabFragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.drawee.view.SimpleDraweeView;
import com.union.travel.tvtest2.FrescoLoader;
import com.union.travel.tvtest2.MainActivity;
import com.union.travel.tvtest2.R;
import com.union.travel.tvtest2.model.AppSettings;
import com.union.travel.tvtest2.model.tabModel.BrandTabModelItem;

import java.util.ArrayList;
import java.util.List;

public class BrandFragment extends Fragment {

    //TODO check with Hayk for BrandId String or int
    private String selectedBrandName = "";

    private boolean isViewShown;
   // private RecyclerView recyclerView = null;
    //private BrandAdapter brandAdapter = null;
    private SimpleDraweeView rightArrovView = null;
    private TextView selectBrandTxtView = null;
    private GridLayout gridLayout = null;

    private List<BrandTabModelItem> brandTabModelItemList = new ArrayList<>();


    private FrescoLoader frescoLoader = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_brand, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        brandTabModelItemList = AppSettings.getInstance().getBrandItemListForBrandTab();
        frescoLoader = new FrescoLoader();
        if (brandTabModelItemList.isEmpty()){
            return;
        }

        rightArrovView = view.findViewById(R.id.rightArrowView);
        selectBrandTxtView = view.findViewById(R.id.brandSelectTxtView);
        gridLayout = view.findViewById(R.id.brandGridLayout);

        initBrandTab();


    }

    private void initBrandTab() {
        initBrandGridLayout();
        rightArrovView.setOnClickListener(selectBrandClickListener);
        selectBrandTxtView.setOnClickListener(selectBrandClickListener);
    }

    private void initBrandGridLayout() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        setGlobalSelectedBrandItemName(0);
        int columnCount = brandTabModelItemList.size() < 7 ? brandTabModelItemList.size() : 6 ;
        gridLayout.setColumnCount(columnCount);
        for (int i = 0; i < brandTabModelItemList.size(); i++) {
            View v = inflater.inflate(R.layout.item_brand_layout, gridLayout, false);
            v.setTag(i);
            v.setOnClickListener(v1 -> {
                int indexOfChild = gridLayout.indexOfChild(v1);
                setGlobalSelectedBrandItemName(indexOfChild);
                int count = gridLayout.getChildCount();
                for (int i1 = 0; i1 < count; i1++) {
                    View childView = gridLayout.getChildAt(i1);
                    if (childView.getTag() == v1.getTag()) {
                        childView.findViewById(R.id.underLineView).setVisibility(View.VISIBLE);
                    } else {
                        childView.findViewById(R.id.underLineView).setVisibility(View.INVISIBLE);
                    }
                }
            });

            BrandTabModelItem brandTabModelItem = brandTabModelItemList.get(i);
            SimpleDraweeView brandIcView = v.findViewById(R.id.icBrandView);
            frescoLoader.loadWithParams(Uri.parse(brandTabModelItem.getBrandIcUrl()), brandIcView, false);
            TextView brandNameTxtView = v.findViewById(R.id.textBrandView);
            brandNameTxtView.setText(brandTabModelItem.getBrandName());

            gridLayout.addView(v);
            if (i == 0) {
                View childView = gridLayout.getChildAt(0);
                childView.findViewById(R.id.underLineView).setVisibility(View.VISIBLE);
            }
        }
    }


    private void setGlobalSelectedBrandItemName(int position){
        selectedBrandName = brandTabModelItemList.get(position).getBrandName();
        AppSettings.getInstance().setCurrentBrandName(selectedBrandName);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewShown = getView() != null && isVisibleToUser;
        Log.d("dwd", "BrandFragment " + isViewShown);
        if (isViewShown){
            //TODO SET Fragment components when is from global clicks

        }
    }


    private View.OnClickListener selectBrandClickListener = v -> {
        //Log.d("dwd", "comparing click " + brandAdapter.getSelectedPosition());
        AppSettings.getInstance().setCurrentBrandName(selectedBrandName);
        AppSettings.getInstance().setSelectedFromBrand(true);
        Activity activity = getActivity();
        ((MainActivity) activity).changeTab(2);

    };
}