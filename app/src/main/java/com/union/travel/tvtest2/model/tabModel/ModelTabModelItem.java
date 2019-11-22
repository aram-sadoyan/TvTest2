package com.union.travel.tvtest2.model.tabModel;


import java.util.ArrayList;
import java.util.List;

public class ModelTabModelItem {

	private List<ModelItem> modelItemList = new ArrayList<>();

	public List<ModelItem> getModelItemList() {
		return modelItemList;
	}
	public void setModelItemList(List<ModelItem> modelItemList) {
		this.modelItemList = modelItemList;
	}


	public static class ModelItem {

		private String modelName = "";
		private int modelId = -1;
		private String modelUrl = "";

		public String getModelName() {
			return modelName;
		}

		public void setModelName(String modelName) {
			this.modelName = modelName;
		}

		public String getModelUrl() {
			return modelUrl;
		}

		public void setModelUrl(String modelUrl) {
			this.modelUrl = modelUrl;
		}

		public int getModelId() {
			return modelId;
		}

		public void setModelId(int modelId) {
			this.modelId = modelId;
		}
	}
}
