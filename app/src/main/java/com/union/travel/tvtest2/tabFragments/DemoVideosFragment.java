package com.union.travel.tvtest2.tabFragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.youtubeplayer.ui.PlayerUIController;
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

//		youTubePlayerView = view.findViewById(R.id.youtubePlayerView);
//
//		youTubePlayerView.initialize(new YouTubePlayerInitListener() {
//			@Override
//			public void onInitSuccess(@NonNull YouTubePlayer youTubePlayer) {
//				Log.d("dwd","Youtube !! lk;jhgfgchj");
//
//				youTubePlayer.loadVideo("GBvjQvq18Fo",2);
//				youTubePlayer.play();
//				PlayerUIController playerUIController = youTubePlayerView.getPlayerUIController();
//				playerUIController.showFullscreenButton(false);
//
//
//
//			}
//		}, false);

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
		VideoVerticalAdapter videoVerticalAdapter = new VideoVerticalAdapter(urls);
		recyclerView.setAdapter(videoVerticalAdapter);

//		exoPlayerView = getView().findViewById(R.id.centreVideoView);
//		exoPlayerView.setVisibility(View.GONE);


//		try {
////			BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
////			TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory());
//			exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity());
//			Uri uri = Uri.parse(videoUrl);
//			DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
//			ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//			MediaSource mediaSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
//			exoPlayerView.setPlayer(exoPlayer);
//			exoPlayer.setPlaybackParameters(null);
//			exoPlayer.seekTo(5000L);
//			exoPlayer.prepare(mediaSource);
////			exoPlayer.setPlayWhenReady(true);
//		} catch (Exception e) {
//			Log.e("DemoVideosFragment", "exoplayer error" + e.toString());
//		}

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
		Log.d("dwd", "fragment 2 " + isViewShown);
	}


}
