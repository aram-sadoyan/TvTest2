package com.union.travel.tvtest2.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.union.travel.tvtest2.R;

import java.util.ArrayList;
import java.util.List;

public class VideoVerticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	List<String> urls = new ArrayList<>();
	private int selectedPosition = 0;



	public VideoVerticalAdapter(List<String> urls) {
		this.urls = urls;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		ItemViewHolder itemHolder = (ItemViewHolder) holder;

//		try {
//			Uri uri = Uri.parse(urls.get(position));
//			DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
//			ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//			MediaSource mediaSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
//			itemHolder.exoPlayerView.setPlayer(itemHolder.exoPlayer);
//			//itemHolder.exoPlayer.setPlaybackParameters(null);
//			//itemHolder.exoPlayerView.setPlayer(null);//todo check
//
//			itemHolder.exoPlayer.prepare(mediaSource);
//		} catch (Exception e) {
//			Log.e("DemoVideosFragment", "exoplayer error" + e.toString());
//		}

		if (selectedPosition == position) {
			//holder.underlineView.setVisibility(View.VISIBLE);
			Log.d("dwd","true");
		} else {
			Log.d("dwd","false");
			itemHolder.underlineIcView.setVisibility(View.INVISIBLE);
		}


		holder.itemView.setOnClickListener(v -> {
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
		private SimpleExoPlayerView exoPlayerView;

		private SimpleExoPlayer exoPlayer;
		private SimpleDraweeView underlineIcView;

//		private FrameLayout shopHorizontalRootItem;
//		public TextView fontName;
//		protected ProgressBar progressBar;


		ItemViewHolder(View view) {
			super(view);
			exoPlayerView = view.findViewById(R.id.exoPlayerView);
			underlineIcView = view.findViewById(R.id.verticalLineView);
			exoPlayer = ExoPlayerFactory.newSimpleInstance(view.getContext());

//			categoryItemIcon = view.findViewById(R.id.shop_category_item_icon);
//			shopHorizontalRootItem = view.findViewById(R.id.shop_hotzontal_root_item);
//			progressBar = view.findViewById(R.id.shop_item_progress_bar);
//			if (ShopUtils.checkShopItemTag(shopItem) == ItemType.BACKGROUND || ShopUtils.checkShopItemTag(shopItem) == ItemType.MASK) {
//				cornerRadius = PicsartUtils.convertDpToPixel(8);
//			} else {
//				cornerRadius = PicsartUtils.convertDpToPixel(4);
//			}
//			roundingParams = RoundingParams.fromCornersRadius(cornerRadius);
//			categoryItemIcon.setHierarchy(new GenericDraweeHierarchyBuilder(activity.getResources())
//					.setRoundingParams(roundingParams)
//					.build());


		}


	}
}
