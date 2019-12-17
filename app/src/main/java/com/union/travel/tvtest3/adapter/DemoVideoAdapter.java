package com.union.travel.tvtest3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.union.travel.tvtest3.R;

import org.jetbrains.annotations.NotNull;

public class DemoVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public DemoVideoAdapter() {


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.watch_category_vertical_item, parent, false);
        return new DemoVideoAdapter.ItemViewHolder(new SimpleExoPlayerView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder itemHolder, int position) {
        ItemViewHolder holder = (ItemViewHolder) itemHolder;

        holder.youTubePlayerView.setEnableAutomaticInitialization(false);
        holder.youTubePlayerView.initialize(new YouTubePlayerListener() {
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


    }

    @Override
    public int getItemCount() {
        return 7;
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private YouTubePlayerView youTubePlayerView;
//		private FrameLayout shopHorizontalRootItem;
//		public TextView fontName;
//		protected ProgressBar progressBar;


     ItemViewHolder(SimpleExoPlayerView simpleExoPlayerView) {
         super(simpleExoPlayerView);

            //watchItem = view.findViewById(R.id.watchitem);
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
