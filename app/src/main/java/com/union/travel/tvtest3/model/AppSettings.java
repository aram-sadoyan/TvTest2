package com.union.travel.tvtest3.model;

import android.util.Size;

import com.union.travel.tvtest3.model.tabModel.BrandTabModelItem;
import com.union.travel.tvtest3.model.tabModel.ComparingItemWithTopModel;
import com.union.travel.tvtest3.model.tabModel.ModelTabModelItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class AppSettings {

	private static AppSettings instance;
	private List<Brand> allBrandList = new ArrayList<>();
	private List<Model> modelListAll = new ArrayList<>();
	private List<String> videoUrlList = new ArrayList<>();
	private SensorModelData sensorModelData = null;

	private List<ComparingItemWithTopModel> comparingItemWithTopModelList = new ArrayList<>();


	public Model getCurrentModel() {
		return currentModel;
	}

	private Model currentModel = null;

	private int currentModelId = -1;

	private String currentBrandName = "Samsung";
	private String curentIcUrl = "";
	private String currentModelColorTitle = "";

	private AtomicBoolean selectedFromBrand = new AtomicBoolean();
	private AtomicBoolean selectedFromModel = new AtomicBoolean();

	public void setSelectedFromBrand(boolean b) {
		selectedFromBrand.set(b);
	}

	public boolean isSelectedFromBrand() {
		return selectedFromBrand.get();
	}

	public void setSelectedFromModel(boolean b) {
		selectedFromModel.set(b);
	}

	public boolean isSelectedFromModel() {
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


	public void setFirstRequestedData(List<Brand> brandList) {
		this.allBrandList = brandList;
		modelListAll.clear();
		for (Brand brand : brandList) {
			modelListAll.addAll(brand.getModels());
		}
		setVideoUrlList();
	}


	public void setSensorMoelData(SensorModelData sensorModelData) {
		this.sensorModelData = sensorModelData;
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


//	public List<Model> getComparingModelList() {
//		List<Model> models = new ArrayList<>();
//
//		if (allBrandList.isEmpty()) {
//			return models;
//		}
//		models.add(allBrandList.get(0).getModels().get(1));
//		//models.add(allBrandList.get(0).getModels().get(1));
//		//models.add(allBrandList.get(0).getModels().get(1));
//
//		return models;
//	}


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
		for (Model model : modelListAll) {
			if (currentModelId == model.getId()) {
				currentModel = model;
				setCurrentBrandName(model.getBrandName());
				//todo add videObject
				List<ModelVideo> modelVideoList = new ArrayList<>();
				for (int i = 0; i < model.getVideoUrls().size(); i++){
					List<String> videoUrlList = model.getVideoUrls();
					List<String> thumbNailUrlList = model.getVideoImages();
					if (!thumbNailUrlList.isEmpty() && thumbNailUrlList.size()
							== videoUrlList.size()){
						ModelVideo modelVideo = new ModelVideo(
								thumbNailUrlList.get(i),
								videoUrlList.get(i));
						modelVideoList.add(modelVideo);
					}
				}
				model.setModelVideos(modelVideoList);
			}
		}
	}

	public List<Model> getAllModelList(){
		return modelListAll;
	}


	public Spec getCurrentSpec() {
		if (currentModel == null) {
			return null;
		}
		return currentModel.getSpec();
	}


	public void setCurrentModelIdFromSensorModelData(int selectedSensorNum) {
		switch (selectedSensorNum) {
			case 1:
				setCurrentModelId(sensorModelData.getSensorFirst());
				break;
			case 2:
				setCurrentModelId(sensorModelData.getSensorSecond());
				break;
			case 3:
				setCurrentModelId(sensorModelData.getSensorThree());
				break;
			case 4:
				setCurrentModelId(sensorModelData.getSensorFour());
				break;
			case 5:
				setCurrentModelId(sensorModelData.getSensorFive());
				break;
			case 6:
				setCurrentModelId(sensorModelData.getSensorSix());
				break;
			case 7:
				setCurrentModelId(sensorModelData.getSensorSeven());
				break;
			case 8:
				setCurrentModelId(sensorModelData.getSensorEight());
				break;
			case 9:
				setCurrentModelId(sensorModelData.getSensorNine());
				break;
			case 10:
				setCurrentModelId(sensorModelData.getSensorTen());
				break;
			case 11:
				setCurrentModelId(sensorModelData.getSensorEleven());
				break;
			case 12:
				setCurrentModelId(sensorModelData.getSensorTwelve());
				break;
		}
	}

	///////////	///	//	/	/	////////////	////////////	//////									FOR  COMPARING FRAGMENT COMPONENTS

	public void addToComparingList(Spec spec, String name, int modelId, String title, String price, String icUrl) {
		if (comparingItemWithTopModelList.size() > 2) {
			comparingItemWithTopModelList.remove(2);
		}

		ComparingItemWithTopModel comparingItemWithTopModel = new ComparingItemWithTopModel();

		comparingItemWithTopModel.setSpec(spec);
		comparingItemWithTopModel.setName(name);

		comparingItemWithTopModel.setPrice(price);
		comparingItemWithTopModel.setTitle(title);
		comparingItemWithTopModel.setIcUrl(icUrl);
		comparingItemWithTopModel.setId(modelId);
		comparingItemWithTopModelList.add(comparingItemWithTopModel);

		//todo create comparing list for TOP Values

	}


	public List<ComparingItemWithTopModel> getComparingItemWithTopModel() {
		return comparingItemWithTopModelList;
	}


	public void setCurrentMainIcUrl(String curentIcUrl) {
		this.curentIcUrl = curentIcUrl;
	}

	public String getCurentIcUrl() {
		return curentIcUrl;
	}

	public String getCurrentModelColorTitle() {
		return currentModelColorTitle;
	}

	public void setCurrentModelColorTitle(String currentModelColorTitle) {
		this.currentModelColorTitle = currentModelColorTitle;
	}
}
