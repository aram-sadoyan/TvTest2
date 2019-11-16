package com.union.travel.tvtest2.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.union.travel.tvtest2.FrescoLoader;
import com.union.travel.tvtest2.R;

import java.util.ArrayList;
import java.util.List;

public class ModelGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private List<String> itemUrls = new ArrayList<>();
	private int selectedPosition = 0;
	private FrescoLoader frescoLoader;

	public ModelGridAdapter(List<String> itemUrls) {
		this.itemUrls = itemUrls;
		frescoLoader = new FrescoLoader();
	}


	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_model, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
		final ViewHolder holder = (ViewHolder) viewHolder;
		String itemUrl = itemUrls.get(position);

		frescoLoader.loadWithParams(Uri.parse(itemUrl), holder.watchIc, false);
		if (selectedPosition == position) {
			//holder.underlineView.setVisibility(View.VISIBLE);
			Log.d("dwd","true");
		} else {
			Log.d("dwd","false");
			holder.underlineView.setVisibility(View.INVISIBLE);
		}


		holder.itemView.setOnClickListener(v -> {
			notifyItemChanged(selectedPosition);
			holder.underlineView.setVisibility(View.VISIBLE);
			selectedPosition = position;
		});
	}

	@Override
	public int getItemCount() {
		return itemUrls.size();
	}

	private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
		private SimpleDraweeView underlineView;
		private SimpleDraweeView watchIc;

		private ViewHolder(View itemView) {
			super(itemView);
			//itemView.setOnClickListener(this);

			watchIc = itemView.findViewById(R.id.watchIc);
			underlineView = itemView.findViewById(R.id.underLineView);

		}


		@Override
		public void onClick(View v) {
			if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
			notifyItemChanged(selectedPosition);
			selectedPosition = getAdapterPosition();
			notifyItemChanged(selectedPosition);
		}
	}


}
