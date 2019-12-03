package com.union.travel.tvtest2.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.union.travel.tvtest2.R;
import com.union.travel.tvtest2.tabFragments.DemoVideosFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VideoVerticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	List<String> urls = new ArrayList<>();
	private int selectedPosition = 0;

	private final DemoVideosFragment.OnItemClickListener listener;



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

		itemHolder.youTubePlayerView.setEnableAutomaticInitialization(false);
		Log.d("dwd", "getEnableAutomaticInitialization " + itemHolder.youTubePlayerView.getEnableAutomaticInitialization());


		try {
			itemHolder.youTubePlayerView.initialize(new YouTubePlayerListener() {
				@Override
				public void onReady(@NotNull YouTubePlayer youTubePlayer) {
					youTubePlayer.cueVideo("6JYIGclVQdw", 0);
				}

				@Override
				public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState playerState) {
				}

				@Override
				public void onPlaybackQualityChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackQuality playbackQuality) {
				}

				@Override
				public void onPlaybackRateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackRate playbackRate) {
				}

				@Override
				public void onError(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerError playerError) {
				}

				@Override
				public void onCurrentSecond(@NotNull YouTubePlayer youTubePlayer, float v) {
				}

				@Override
				public void onVideoDuration(@NotNull YouTubePlayer youTubePlayer, float v) {
				}

				@Override
				public void onVideoLoadedFraction(@NotNull YouTubePlayer youTubePlayer, float v) {
				}

				@Override
				public void onVideoId(@NotNull YouTubePlayer youTubePlayer, @NotNull String s) {
				}

				@Override
				public void onApiChange(@NotNull YouTubePlayer youTubePlayer) {
				}
			});
		} catch (IllegalStateException ex) {
			itemHolder.youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> youTubePlayer.addListener(new YouTubePlayerListener() {
				@Override
				public void onReady(@NotNull YouTubePlayer youTubePlayer) {
					Log.d("dwd", "onREADY INNER");
					youTubePlayer.cueVideo("6JYIGclVQdw", 0);
				}

				@Override
				public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState playerState) {

				}

				@Override
				public void onPlaybackQualityChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackQuality playbackQuality) {

				}

				@Override
				public void onPlaybackRateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackRate playbackRate) {

				}

				@Override
				public void onError(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerError playerError) {
					Log.d("dwd", "onError");


				}

				@Override
				public void onCurrentSecond(@NotNull YouTubePlayer youTubePlayer, float v) {

				}

				@Override
				public void onVideoDuration(@NotNull YouTubePlayer youTubePlayer, float v) {

				}

				@Override
				public void onVideoLoadedFraction(@NotNull YouTubePlayer youTubePlayer, float v) {

				}

				@Override
				public void onVideoId(@NotNull YouTubePlayer youTubePlayer, @NotNull String s) {

				}

				@Override
				public void onApiChange(@NotNull YouTubePlayer youTubePlayer) {

				}
			}));
		}




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
		private YouTubePlayerView youTubePlayerView;
		private SimpleDraweeView underlineIcView;
		private View thumbView;

		ItemViewHolder(View view) {
			super(view);
			thumbView = view.findViewById(R.id.thumbnailView);
			underlineIcView = view.findViewById(R.id.verticalLineView);
			youTubePlayerView = view.findViewById(R.id.youtubePlayerView);
		}
	}
}
