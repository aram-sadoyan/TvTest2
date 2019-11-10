package com.union.travel.tvtest2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.union.travel.tvtest2.R;


public class VerticalWatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	public VerticalWatchAdapter() {


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
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

	}

	@Override
	public int getItemCount() {
		return 5;
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
