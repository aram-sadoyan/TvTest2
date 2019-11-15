package com.union.travel.tvtest2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.union.travel.tvtest2.R;

public class ModelGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_model, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

	}

	@Override
	public int getItemCount() {
		return 44;
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
