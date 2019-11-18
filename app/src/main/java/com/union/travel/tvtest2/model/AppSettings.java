package com.union.travel.tvtest2.model;

import android.util.Size;

import java.util.ArrayList;
import java.util.List;

public class AppSettings {

	private static AppSettings instance;
	private List<Brand> brandList = new ArrayList<>();
	private List<String> videoUrlList = new ArrayList<>();


	public static AppSettings getInstance() {
		if (instance == null) {
			instance = new AppSettings();
		}
		return instance;
	}

	private AppSettings() {
//		if (context == null) {
//			setContext(SocialinV3.getInstance().getContext());
//		}
//		loadSharedPreferences(false);
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
		setVideoUrlList();
	}

	private void setVideoUrlList() {
		if (!brandList.isEmpty()) {
			for (Brand brand : brandList) {
				if (brand != null) {
					for (Model model : brand.getModels()) {
						if (model != null) {
							videoUrlList.addAll(model.getVideoUrls());
						}
					}
				}
			}
		}
	}

	public List<Brand> getBrandList() {
		return brandList;
	}


	public List<String> getVideoUrlList() {
		return videoUrlList;
	}

	public void setVideoUrlList(List<String> videoUrlList) {
		this.videoUrlList = videoUrlList;
	}

	public Model getModelById(int i) {
		for (Brand brand : brandList){
			List<Model> models = brand.getModels();
			if (!models.isEmpty()){
				for (Model model: models){
					if (model != null){
						if (model.getId() == i){
							return model;
						}
					}
				}
			}
		}

		return null;
	}


	public Size getSizeByModelId(int i) {


		return null;
	}
}
