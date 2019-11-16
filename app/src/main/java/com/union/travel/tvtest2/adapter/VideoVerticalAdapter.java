package com.union.travel.tvtest2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.union.travel.tvtest2.R;

import java.util.ArrayList;
import java.util.List;

public class VideoVerticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	List<String> urls = new ArrayList<>();



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

	}

	@Override
	public int getItemCount() {
		return urls.size();
	}


	public class ItemViewHolder extends RecyclerView.ViewHolder {
		private SimpleDraweeView watchItem;
//		private FrameLayout shopHorizontalRootItem;
//		public TextView fontName;
//		protected ProgressBar progressBar;


		ItemViewHolder(View view) {
			super(view);
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
