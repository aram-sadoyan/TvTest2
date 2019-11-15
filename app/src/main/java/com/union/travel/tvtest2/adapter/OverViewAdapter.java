package com.union.travel.tvtest2.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.union.travel.tvtest2.R;

public class OverViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (viewType == 0) {
			return new TopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_overview_top, parent, false));
		} else {
			return new BottomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_overview_bottom, parent, false));
		}
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

		//Top Layout
		if (position == 0) {

//			TopViewHolder viewHolder = (TopViewHolder) holder;
//			viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
//                LinearLayoutManager.VERTICAL, false));
//        recyclerView.setItemViewCacheSize(10);
//        recyclerView.setDrawingCacheEnabled(true);
//        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//        recyclerView.setHasFixedSize(true);
//
//
//        VerticalWatchAdapter verticalWatchAdapter = new VerticalWatchAdapter();
//
//        recyclerView.setAdapter(verticalWatchAdapter);




		} else if (position == 1) {
			//Bottom Layout
			BottomOverviewAdapter bottomOverviewAdapter = new BottomOverviewAdapter();
			BottomViewHolder bottomHolder = (BottomViewHolder) holder;
			bottomHolder.recyclerView.setAdapter(bottomOverviewAdapter);


		}


	}

	@Override
	public int getItemCount() {
		return 1;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return 0;
		} else if (position == 1) {
			return 1;
		}
		return super.getItemViewType(position);

	}

	class TopViewHolder extends RecyclerView.ViewHolder {
		//final Recy isNewIndicator;
//		final ProgressBar loadingView;
//		final RoundRotateImageView image;

		private TopViewHolder(View itemView) {
			super(itemView);
//			isNewIndicator = itemView.findViewById(R.id.isNewIndicator);
//			loadingView = itemView.findViewById(R.id.loadingView);
//			image = itemView.findViewById(R.id.image);
//
//			orientationHandler.addListener(image);
		}
	}


	class BottomViewHolder extends RecyclerView.ViewHolder {
		private RecyclerView recyclerView = null;


		private BottomViewHolder(View itemView) {
			super(itemView);
			recyclerView = itemView.findViewById(R.id.bottomRecyclerView);

			//todo for grid
			//GridLayoutManager layoutManager = new GridLayoutManager(itemView.getContext(), 5);
			//recyclerView.setLayoutManager(layoutManager);
			recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
					LinearLayoutManager.HORIZONTAL, false));
			recyclerView.addItemDecoration(new RecyclerViewDecoration(itemView.getContext()));
			recyclerView.setNestedScrollingEnabled(true);
			recyclerView.setItemViewCacheSize(10);
			recyclerView.setDrawingCacheEnabled(true);
			recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
			recyclerView.setHasFixedSize(true);


//			loadingView = itemView.findViewById(R.id.loadingView);
//			image = itemView.findViewById(R.id.image);
//
//			orientationHandler.addListener(image);
		}
	}



	private class RecyclerViewDecoration extends RecyclerView.ItemDecoration {

		private int bottomSpace = 0;

		public RecyclerViewDecoration(Context context) {
//			Activity activity = getActivity();
//			if (activity != null && !activity.isFinishing()) {
//				bottomSpace = PicsartUtils.convertDpToPixel(94);
//			}
		}

		@Override
		public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
			super.getItemOffsets(outRect, view, parent, state);
			int n = parent.getAdapter().getItemCount();
			Log.d("dwd", String.valueOf(parent.getChildAdapterPosition(view)));

			if (parent.getChildAdapterPosition(view) == 0){
				outRect.set(90,0,0,0);
			}else {
				outRect.set(120,0,0,0);

			}

//			int k = n % previewCellsCount;
//			if (k == 0) {
//				k = previewCellsCount;
//			}
//			if (parent.getChildAdapterPosition(view) >= n - k) { //get last items for set offset
//				outRect.set(0, 0, 0, bottomSpace);
//			} else {
//				outRect.set(0, 0, 0, 0);
//			}
//			if (parent.getChildAdapterPosition(view) < previewCellsCount) {
//				outRect.top = PicsartUtils.convertDpToPixel(16);
//			} else {
//				outRect.top = PicsartUtils.convertDpToPixel(8);
//			}
//			outRect.left = PicsartUtils.convertDpToPixel(4);
//			outRect.right = PicsartUtils.convertDpToPixel(4);
		}
	}


}
