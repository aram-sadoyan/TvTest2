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

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.union.travel.tvtest2.R;
import com.union.travel.tvtest2.adapter.VideoVerticalAdapter;

import java.util.ArrayList;
import java.util.List;

public class DemoVideosFragment extends Fragment {

		VideoView video;
		String videoUrl="https://mysmartech.ru/esiminch.mp4";
		SimpleExoPlayerView exoPlayerView;
		SimpleExoPlayer exoPlayer;


	public RecyclerView recyclerView;
	private boolean isViewShown;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_demo_videos, container, false);



	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		recyclerView = view.findViewById(R.id.recvw_demo);



	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
//
//		 exoPlayerView=(SimpleExoPlayerView)getView().findViewById(R.id.esiminch);
//		try {
//			BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter();
//			TrackSelector trackSelector=new DefaultTrackSelector(new AdaptiveTrackSelection.Factory());
//			exoPlayer= ExoPlayerFactory.newSimpleInstance(getActivity());
//			Uri uri=Uri.parse(video_url);
//			DefaultHttpDataSourceFactory dataSourceFactory=new DefaultHttpDataSourceFactory("exoplayer_video");
//			ExtractorsFactory extractorsFactory=new DefaultExtractorsFactory();
//			MediaSource mediaSource=new ExtractorMediaSource(uri,dataSourceFactory,extractorsFactory,null,null);
//
//			exoPlayerView.setPlayer(exoPlayer);
//			exoPlayer.prepare(mediaSource);
//			exoPlayer.setPlayWhenReady(true);
//
//		}catch (Exception e){
//			Log.e("DemoVideosFragment", "exoplayer error"+e.toString());
//		}
//
//
//		recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
//				LinearLayoutManager.VERTICAL, false));
//		recyclerView.setItemViewCacheSize(10);
//		recyclerView.setDrawingCacheEnabled(true);
//		recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//		recyclerView.setHasFixedSize(true);
//
//
//		DemoVideoAdapter DemoVideoAdapter = new DemoVideoAdapter();
//
//		recyclerView.setAdapter(DemoVideoAdapter);

	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		isViewShown = getView() != null && isVisibleToUser;
		Log.d("dwd", "fragment 2 " + isViewShown);
	}



}
