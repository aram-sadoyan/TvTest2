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
import com.union.travel.tvtest3.tabFragments.DemoVideosFragment;


import java.util.ArrayList;
import java.util.List;

public class VideoVerticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private List<String> urls = new ArrayList<>();
	private int selectedPosition = 0;
	private final DemoVideosFragment.OnItemClickListener listener;
	private FrescoLoader frescoLoader = new FrescoLoader();

	public VideoVerticalAdapter(List<String> urls, DemoVideosFragment.OnItemClickListener listener) {
		this.urls = urls;
		this.listener = listener;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		ItemViewHolder itemHolder = (ItemViewHolder) holder;

		frescoLoader.loadWithParams(Uri.parse(urls.get(position)), itemHolder.simpleDraweeView, false);

		if (selectedPosition == position) {
			itemHolder.underlineIcView.setVisibility(View.VISIBLE);
		} else {
			itemHolder.underlineIcView.setVisibility(View.INVISIBLE);
		}


		itemHolder.thumbView.setOnClickListener(v -> {
			if (selectedPosition != position) {
				listener.onItemClick(urls.get(position), position);
			}

			notifyItemChanged(selectedPosition);
			itemHolder.underlineIcView.setVisibility(View.VISIBLE);
			selectedPosition = position;
		});
	}

	@Override
	public int getItemCount() {
		return urls.size();
	}


	public class ItemViewHolder extends RecyclerView.ViewHolder {
		//private YouTubePlayerView youTubePlayerView;
		private SimpleDraweeView simpleDraweeView;
		private SimpleDraweeView underlineIcView;
		private View thumbView;

		ItemViewHolder(View view) {
			super(view);
			thumbView = view.findViewById(R.id.thumbnailView);
			underlineIcView = view.findViewById(R.id.verticalLineView);
			//youTubePlayerView = view.findViewById(R.id.youtubePlayerView);
			simpleDraweeView = view.findViewById(R.id.simpleDrawee);
		}
	}
}
