package com.union.travel.tvtest2.tabFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.union.travel.tvtest2.ExoPlayerManager;
import com.union.travel.tvtest2.FrescoLoader;
import com.union.travel.tvtest2.R;
import com.union.travel.tvtest2.adapter.VideoVerticalAdapter;
import com.union.travel.tvtest2.model.AppSettings;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DemoVideosFragment extends Fragment {


	private VideoView video;
	private String videoUrl = "https://mysmartech.ru/esiminch.mp4";
	private SimpleExoPlayer exoPlayer;
	private SimpleDraweeView playBtn;
	private List<String> videoUrlList = new ArrayList<>();
	private FrescoLoader frescoLoader = new FrescoLoader();


	private ExoPlayerManager exoManager;
	private PlayerView playerView = null;


	public RecyclerView recyclerView;
	private boolean isViewShown;


	private YouTubePlayerView youTubePlayerView = null;
	//private SimpleExoPlayerView exoPlayerView;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_demo_videos, container, false);


	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		recyclerView = view.findViewById(R.id.recvw_demo);
		playBtn = view.findViewById(R.id.playBtn);

		youTubePlayerView = view.findViewById(R.id.youtubePlayerView);
		youTubePlayerView.setEnableAutomaticInitialization(false);
		youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
			@Override
			public void onYouTubePlayer(@NotNull YouTubePlayer youTubePlayer) {
				youTubePlayer.cueVideo("6JYIGclVQdw", 0);
			}
		});



		youTubePlayerView.initialize(new YouTubePlayerListener() {
			@Override
			public void onReady(@NotNull YouTubePlayer youTubePlayer) {


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
				Log.d("dwd", "error  " + playerError.toString());
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


		videoUrlList = AppSettings.getInstance().getVideoUrlList();


//		playBtn.setOnClickListener(v -> {
//			playBtn.setVisibility(View.GONE);
//			exoManager.playStream(0L);
//		});


	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		//initVideoView();


		RecyclerView.RecycledViewPool sharedPool = new RecyclerView.RecycledViewPool();

		recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
				LinearLayoutManager.VERTICAL, false));
		recyclerView.setItemViewCacheSize(10);
		recyclerView.setDrawingCacheEnabled(true);
		recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
		recyclerView.setHasFixedSize(true);
		recyclerView.setRecycledViewPool(sharedPool);


		List<String> urls = new ArrayList<>();
		urls.add(videoUrl);
		urls.add(videoUrl);
		urls.add(videoUrl);
		urls.add(videoUrl);
		urls.add(videoUrl);
		urls.add(videoUrl);
		urls.add(videoUrl);
		urls.add(videoUrl);

		VideoVerticalAdapter videoVerticalAdapter = new VideoVerticalAdapter(urls, (icUrl, adapterPosition) -> {
			//TODO CHANGE CURRENT VIDEO VIEW
			Log.d("dwd","playAnother view");
		});
		recyclerView.setAdapter(videoVerticalAdapter);


	}

	private void initVideoView() {

//		Bitmap thumb = ThumbnailUtils.createVideoThumbnail("https://mysmartech.ru/esiminch.mp4",
//				MediaStore.Images.Thumbnails.MINI_KIND);
//
//		BitmapDrawable bitmapDrawable = new BitmapDrawable(thumb);
//
//		Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
//		Canvas canvas = new Canvas(bitmap);
//		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//
//		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//		canvas.save();
//		canvas.drawBitmap(thumb, 0f, 0f, paint);
//
//		testIc.setImageDrawable(bitmapDrawable);

		//	playerView.setBackground(bitmapDrawable);


		//todo bring back videos
//		exoManager = ExoPlayerManager.createInstance(this, getContext(), playerView);
//		exoManager.setVideoPath("https://mysmartech.ru/esiminch.mp4");
//		exoManager.seekToMls(1);
//		exoManager.setVideoCallback(new ExoPlayerManager.VideoCallback() {
//			@Override
//			public void onVideoStart(@NotNull String url) {
//				Log.d("dwd", "on video start");
//
//			}
//
//			@Override
//			public void onVideoEnd(@NotNull String url) {
//				Log.d("dwd", "on video end");
//				playBtn.setVisibility(View.GONE);
//
//			}
//
//			@Override
//			public void onVideoFail(@NotNull String url, @NotNull String errorMsg) {
//				Log.d("dwd", "fail video ");
//
//			}
//
//			@Override
//			public void onVideoBufferingEnd() {
//				Log.d("dwd", "video buffering end");
//			}
//		});
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		isViewShown = getView() != null && isVisibleToUser;
		//Log.d("dwd", "fragment 2 " + isViewShown);
	}


	public interface OnItemClickListener {
		void onItemClick(String icUrl, int adapterPosition);
	}


}
