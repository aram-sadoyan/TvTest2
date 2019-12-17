package com.union.travel.tvtest3.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.union.travel.tvtest3.FrescoLoader;
import com.union.travel.tvtest3.R;
import com.union.travel.tvtest3.tabFragments.OverviewFragment;
import com.union.travel.tvtest3.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;


public class VerticalWatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


	private List<String> itemUrls = new ArrayList<>();
	private int selectedPosition = 0;
	private FrescoLoader frescoLoader;

	private final OverviewFragment.OnItemClickListener listener;


	public VerticalWatchAdapter(List<String> itemUrls, OverviewFragment.OnItemClickListener onItemClickListener) {
		this.itemUrls = itemUrls;
		frescoLoader = new FrescoLoader();
		this.listener = onItemClickListener;
	}


	public void setItemsList(List<String> itemUrls) {
		this.itemUrls.clear();
		this.itemUrls.addAll(itemUrls);
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view;
		view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.watch_category_vertical_item, parent, false);
		return new VerticalWatchAdapter.ItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
		final ItemViewHolder holder = (ItemViewHolder) viewHolder;
		String itemUrl = itemUrls.get(position);

		frescoLoader.loadWithParams(Uri.parse(AppConstants.IMG_URL_PREFFIX + itemUrl), holder.icModel, false);

		if (selectedPosition == position) {
			holder.indicatorView.setVisibility(View.VISIBLE);
		} else {
			holder.indicatorView.setVisibility(View.INVISIBLE);
		}


	}

	@Override
	public int getItemCount() {
		return itemUrls.size();
	}


	public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		private SimpleDraweeView icModel;
		private SimpleDraweeView indicatorView;


		ItemViewHolder(View view) {
			super(view);
			itemView.setOnClickListener(this);
			icModel = view.findViewById(R.id.watchitem);
			indicatorView = view.findViewById(R.id.indicatorView);

		}


		@Override
		public void onClick(View v) {
			if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
			if (selectedPosition != getAdapterPosition()) {
				int adapterPosition = getAdapterPosition();
				listener.onItemClick(itemUrls.get(adapterPosition), adapterPosition);
			}
			notifyItemChanged(selectedPosition);
			selectedPosition = getAdapterPosition();
			notifyItemChanged(selectedPosition);
		}
	}
}
