package com.union.travel.tvtest2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.union.travel.tvtest2.R;

public class BottomOverviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_overview, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

	}

	@Override
	public int getItemCount() {
		return 40;
	}


	class ViewHolder extends RecyclerView.ViewHolder {
//		final View isNewIndicator;
//		final ProgressBar loadingView;
//		final RoundRotateImageView image;

		private ViewHolder(View itemView) {
			super(itemView);
//			isNewIndicator = itemView.findViewById(R.id.isNewIndicator);
//			loadingView = itemView.findViewById(R.id.loadingView);
//			image = itemView.findViewById(R.id.image);
//
//			orientationHandler.addListener(image);
		}
	}
}
