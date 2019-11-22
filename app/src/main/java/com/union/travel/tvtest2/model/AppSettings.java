package com.union.travel.tvtest2.model;

import android.util.Size;

import com.union.travel.tvtest2.model.tabModel.BrandTabModelItem;
import com.union.travel.tvtest2.model.tabModel.ModelTabModelItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class AppSettings {

	private static AppSettings instance;
	private List<Brand> allBrandList = new ArrayList<>();
	private List<String> videoUrlList = new ArrayList<>();
	private int currentModelId = -1;
	private String currentBrandName = "Samsung";
	private List<Model> comparingModelList = new ArrayList<>();

	private AtomicBoolean selectedFromBrand = new AtomicBoolean();
	private AtomicBoolean selectedFromModel = new AtomicBoolean();

	public void setSelectedFromBrand(boolean b){
		selectedFromBrand.set(b);
	}

	public boolean isSelectedFromBrand(){
		return selectedFromBrand.get();
	}

	public void setSelectedFromModel(boolean b){
		selectedFromModel.set(b);
	}

	public boolean isSelectedFromModel(){
		return selectedFromModel.get();
	}


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

	public void setAllBrandList(List<Brand> allBrandList) {
		this.allBrandList = allBrandList;
		setVideoUrlList();
	}

	private void setVideoUrlList() {
		if (!allBrandList.isEmpty()) {
			for (Brand brand : allBrandList) {
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


	public List<String> getVideoUrlList() {
		return videoUrlList;
	}

	public void setVideoUrlList(List<String> videoUrlList) {
		this.videoUrlList = videoUrlList;
	}

	public Model getModelById(int i) {
		for (Brand brand : allBrandList) {
			List<Model> models = brand.getModels();
			if (!models.isEmpty()) {
				for (Model model : models) {
					if (model != null) {
						if (model.getId() == i) {
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


	public List<Model> getComparingModelList() {
		List<Model> models = new ArrayList<>();

		if (allBrandList.isEmpty()) {
			return models;
		}
		models.add(allBrandList.get(0).getModels().get(1));
		//models.add(allBrandList.get(0).getModels().get(1));
		//models.add(allBrandList.get(0).getModels().get(1));

		return models;
	}


	///////////	///	//	/	/	////////////	////////////	//////									FOR  BRAND FRAGMENT COMPONENTS
	public List<BrandTabModelItem> getBrandItemListForBrandTab() {
		List<BrandTabModelItem> brandTabItems = new ArrayList<>();
		if (allBrandList.isEmpty()) {
			return brandTabItems;
		}
		for (Brand brand : allBrandList) {
			BrandTabModelItem item = new BrandTabModelItem();
			item.setBrandName(brand.getName());
			item.setBrandIcUrl(brand.getIcUrl());
			brandTabItems.add(item);
		}
		return brandTabItems;
	}


	private Brand getBrandByBrandName() {
		for (Brand brandItem : allBrandList) {
			if (currentBrandName.equals(brandItem.getName())) {
				return brandItem;
			}
		}
		return null;
	}

	///////////	///	//	/	/	////////////	////////////	//////									FOR  MODEL FRAGMENT COMPONENTS


	public ModelTabModelItem getModelTabItem() {
		ModelTabModelItem modelItem = new ModelTabModelItem();
		Brand brand = getBrandByBrandName();
		if (brand == null) {
			return modelItem;
		}
		List<ModelTabModelItem.ModelItem> modelItemList = new ArrayList<>();
		for (Model model : brand.getModels()) {
			ModelTabModelItem.ModelItem modelItem1 = new ModelTabModelItem.ModelItem();
			modelItem1.setModelName(model.getName());
			modelItem1.setModelId(model.getId());
			modelItem1.setModelUrl(getModelTabItemUrl(model));
			modelItemList.add(modelItem1);
		}
		modelItem.setModelItemList(modelItemList);
		return modelItem;
	}

	///the first icons from Colors
	private String getModelTabItemUrl(Model model) {

		List<Color> colors = model.getColors();
		if (colors.isEmpty()) {
			return "";
		}

		Color color = colors.get(0);
		if (color == null) {
			return "";
		}

		List<String> colorUrls = color.getColorUrls();
		if (colorUrls.isEmpty()) {
			return "";
		}

		return colorUrls.get(0);
	}


	///////////	///	//	/	/	////////////	////////////	//////									FOR  OVERVIEW FRAGMENT COMPONENTS
	public Model getModelByModelId() {
		//todo improve this, sarqel modelneri list ndhanur brand listic
		for (Brand brand : allBrandList) {
			for (Model model : brand.getModels()) {
				if (model.getId() == currentModelId) {
					return model;
				}
			}
		}
		return null;
	}

	///////////	///	//	/	/	////////////	////////////	//////									FOR  SPEC FRAGMENT COMPONENTS
//	public Model getMode(){
//		for (Brand brand : allBrandList){
//
//		}
//	}




	public String getCurrentBrandName() {
		return currentBrandName;
	}

	public void setCurrentBrandName(String currentBrandName) {
		this.currentBrandName = currentBrandName;
	}


	//todo set this before pick upping Watch from Sensor
	public void setBrandNameFromModelPicking(String brandFromModelsParent) {
		this.currentBrandName = brandFromModelsParent;
	}


	public int getCurrentModelId() {
		return currentModelId;
	}

	public void setCurrentModelId(int currentModelId) {
		this.currentModelId = currentModelId;
	}




	public Spec getCurrentSpec(){
		return new Spec();
	}

}
