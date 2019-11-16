package com.union.travel.tvtest2.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.union.travel.tvtest2.FrescoLoader;
import com.union.travel.tvtest2.R;
import com.union.travel.tvtest2.model.BrandItem;

import java.util.ArrayList;
import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


	private List<BrandItem> items = new ArrayList<>();
	private FrescoLoader frescoLoader;
	private int selectedPosition = 0;



	public BrandAdapter(List<BrandItem> items){
		this.items = items;
		frescoLoader = new FrescoLoader();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_brand_layout, parent, false));

	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
		final ViewHolder holder = (ViewHolder) viewHolder;
		BrandItem item = items.get(position);
		//frescoLoader.loadWithParams();
		holder.brandNameTxtView.setText("jhgu");

		if (selectedPosition == position) {
			holder.underLineView.setVisibility(View.VISIBLE);
			Log.d("dwd","true");
		} else {
			Log.d("dwd","false");
			holder.underLineView.setVisibility(View.INVISIBLE);

		}

//		holder.itemView.setOnClickListener(v -> {
//			Log.d("dwd", item.getName());
//			Log.d("dwd", item.getName());
//			//lastSelectedPosition = position;
//
//			holder.underLineView.setVisibility(View.VISIBLE);
//
//		});
	}


	public int getSelectedPosition(){
		return selectedPosition;
	}

	@Override
	public int getItemCount() {
		return items.size();
	}


	private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private SimpleDraweeView underLineView = null;
		private SimpleDraweeView icBrandView = null;
		public TextView brandNameTxtView = null;


		private ViewHolder(View itemView)  {
			super(itemView);
			itemView.setOnClickListener(this);
			underLineView = itemView.findViewById(R.id.underLineView);
			brandNameTxtView = itemView.findViewById(R.id.textBrandView);
			icBrandView = itemView.findViewById(R.id.icBrandView);

		}

		@Override
		public void onClick(View v) {
			// Below line is just like a safety check, because sometimes holder could be null,
			// in that case, getAdapterPosition() will return RecyclerView.NO_POSITION
			if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

			// Updating old as well as new positions
			notifyItemChanged(selectedPosition);
			selectedPosition = getAdapterPosition();
			notifyItemChanged(selectedPosition);


			// Do your another stuff for your onClick
		}
	}
}
