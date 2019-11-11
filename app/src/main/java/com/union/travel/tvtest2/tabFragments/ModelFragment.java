package com.union.travel.tvtest2.tabFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;

import com.union.travel.tvtest2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelFragment extends Fragment {
    private boolean isViewShown;


    // Array of strings storing country names
    String[] title = new String[] {
            "name1",
            "name2",
            "name3",
            "name4",
            "name4",
            "name5",
            "name6",
            "name6",
            "name7",
            "name8"
    };

    String[] name = new String[] {
            "name1",
            "name2",
            "name3",
            "name4",
            "name4",
            "name5",
            "name6",
            "name6",
            "name7",
            "name8"
    };

    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] icon = new int[]{
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_model, container, false);


        GridView gridView = (GridView) view.findViewById(R.id.grid1);


        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<8;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("title", title[i]);
            hm.put("icon", Integer.toString(icon[i]) );
            aList.add(hm);
        }

        String[] from = { "icon","title"};

        int[] to = { R.id.icon,R.id.title};

        SimpleAdapter adapter = new SimpleAdapter(getContext(), aList, R.layout.lb_vertical_grid, from, to);


        gridView.setAdapter(adapter);

       return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewShown = getView() != null && isVisibleToUser;
        Log.d("dwd", "FRG 3 " + isViewShown);









    }
}

