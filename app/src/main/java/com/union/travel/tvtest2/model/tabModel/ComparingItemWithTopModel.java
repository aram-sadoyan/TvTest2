package com.union.travel.tvtest2.model.tabModel;

import com.union.travel.tvtest2.model.Spec;

public class ComparingItemWithTopModel {


	private Spec spec = null;

	private String name = "";
	private int id = -1;
	private String title = "";
	private String price = "";
	private String icUrl = "";


	public Spec getSpec() {
		return spec;
	}

	public void setSpec(Spec spec) {
		this.spec = spec;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIcUrl() {
		return icUrl;
	}

	public void setIcUrl(String icUrl) {
		this.icUrl = icUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
