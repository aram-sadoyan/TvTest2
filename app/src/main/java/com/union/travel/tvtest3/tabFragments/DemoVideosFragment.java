package com.union.travel.tvtest3.tabFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.union.travel.tvtest3.R;
import com.union.travel.tvtest3.adapter.VideoVerticalAdapter;
import com.union.travel.tvtest3.model.AppSettings;
import com.union.travel.tvtest3.model.Model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DemoVideosFragment extends Fragment {


	private String videoUrl = "https://mysmartech.ru/esiminch.mp4";
	public RecyclerView recyclerView;
	private TextView nameTxtView = null;
	private TextView titleTxtView = null;

	private YouTubePlayerView youTubePlayerView = null;
	private AtomicBoolean dataIsSelectedFromHint = new AtomicBoolean();
	private List<String> videoUrlList = new ArrayList<>();

	private YouTubePlayer youTubePlayerGlobal = null;


	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		boolean isViewShown = getView() != null && isVisibleToUser;
		if (isViewShown) {
			initDemoVideosFragment();
			dataIsSelectedFromHint.set(true);
			if (youTubePlayerGlobal != null){
				youTubePlayerGlobal.play();
			}
		} else {
			if (youTubePlayerGlobal != null){
				youTubePlayerGlobal.pause();

			}
			dataIsSelectedFromHint.set(false);

		}
	}

	private void initDemoVideosFragment() {
		Model currentModel = AppSettings.getInstance().getCurrentModel();
		if (currentModel == null) {
			return;
		}
		//todo get ModelVideo list with thubnail url and video url
		nameTxtView.setText(currentModel.getName());
		titleTxtView.setText(AppSettings.getInstance().getCurrentModelColorTitle());

		videoUrlList = AppSettings.getInstance().getVideoUrlList();


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_demo_videos, container, false);


	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		recyclerView = view.findViewById(R.id.recvw_demo);
		nameTxtView = view.findViewById(R.id.nameTxtView);
		titleTxtView = view.findViewById(R.id.titeTxtView);
		youTubePlayerView = view.findViewById(R.id.youtubePlayerView);
		youTubePlayerView.setEnableAutomaticInitialization(false);

		if (!dataIsSelectedFromHint.get()) {
			initDemoVideosFragment();
			initDemoVideoVerticalRecycler();
		}



		youTubePlayerView.getYouTubePlayerWhenReady(new YouTubePlayerCallback() {
			@Override
			public void onYouTubePlayer(@NotNull YouTubePlayer youTubePlayer) {


			}
		});


		youTubePlayerView.initialize(new YouTubePlayerListener() {
			@Override
			public void onReady(@NotNull YouTubePlayer youTubePlayer) {
				youTubePlayerGlobal = youTubePlayer;
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




	}

	private void initDemoVideoVerticalRecycler() {
		RecyclerView.RecycledViewPool sharedPool = new RecyclerView.RecycledViewPool();
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
				LinearLayoutManager.VERTICAL, false));
		recyclerView.setItemViewCacheSize(10);
		recyclerView.setDrawingCacheEnabled(true);
		recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
		recyclerView.setHasFixedSize(true);
		recyclerView.setRecycledViewPool(sharedPool);
		List<String> urls = new ArrayList<>();
		urls.add("https://3.bp.blogspot.com/-MTT4RYVc0tQ/V8R1lCOrtpI/AAAAAAAAD4g/QRcIV3MNEiA8gQmSjqBrdkgLfq47_hnMgCLcB/s1600/youtube%2Bimage.png");
		urls.add("https://3.bp.blogspot.com/-MTT4RYVc0tQ/V8R1lCOrtpI/AAAAAAAAD4g/QRcIV3MNEiA8gQmSjqBrdkgLfq47_hnMgCLcB/s1600/youtube%2Bimage.png");
		urls.add("https://3.bp.blogspot.com/-MTT4RYVc0tQ/V8R1lCOrtpI/AAAAAAAAD4g/QRcIV3MNEiA8gQmSjqBrdkgLfq47_hnMgCLcB/s1600/youtube%2Bimage.png");
		urls.add("https://3.bp.blogspot.com/-MTT4RYVc0tQ/V8R1lCOrtpI/AAAAAAAAD4g/QRcIV3MNEiA8gQmSjqBrdkgLfq47_hnMgCLcB/s1600/youtube%2Bimage.png");
		urls.add("https://3.bp.blogspot.com/-MTT4RYVc0tQ/V8R1lCOrtpI/AAAAAAAAD4g/QRcIV3MNEiA8gQmSjqBrdkgLfq47_hnMgCLcB/s1600/youtube%2Bimage.png");
		urls.add("https://3.bp.blogspot.com/-MTT4RYVc0tQ/V8R1lCOrtpI/AAAAAAAAD4g/QRcIV3MNEiA8gQmSjqBrdkgLfq47_hnMgCLcB/s1600/youtube%2Bimage.png");
		urls.add("https://3.bp.blogspot.com/-MTT4RYVc0tQ/V8R1lCOrtpI/AAAAAAAAD4g/QRcIV3MNEiA8gQmSjqBrdkgLfq47_hnMgCLcB/s1600/youtube%2Bimage.png");
		urls.add("https://3.bp.blogspot.com/-MTT4RYVc0tQ/V8R1lCOrtpI/AAAAAAAAD4g/QRcIV3MNEiA8gQmSjqBrdkgLfq47_hnMgCLcB/s1600/youtube%2Bimage.png");
		urls.add("https://3.bp.blogspot.com/-MTT4RYVc0tQ/V8R1lCOrtpI/AAAAAAAAD4g/QRcIV3MNEiA8gQmSjqBrdkgLfq47_hnMgCLcB/s1600/youtube%2Bimage.png");
		urls.add("https://3.bp.blogspot.com/-MTT4RYVc0tQ/V8R1lCOrtpI/AAAAAAAAD4g/QRcIV3MNEiA8gQmSjqBrdkgLfq47_hnMgCLcB/s1600/youtube%2Bimage.png");
		VideoVerticalAdapter videoVerticalAdapter = new VideoVerticalAdapter(urls, (icUrl, adapterPosition) -> {
			//TODO CHANGE CURRENT VIDEO VIEW
			Log.d("dwd", "playAnother view");
		});
		recyclerView.setAdapter(videoVerticalAdapter);
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		//initVideoView();


	}


	public interface OnItemClickListener {
		void onItemClick(String icUrl, int adapterPosition);
	}


}
