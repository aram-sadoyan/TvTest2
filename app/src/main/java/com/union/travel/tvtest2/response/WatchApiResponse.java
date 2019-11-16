package com.union.travel.tvtest2.response;

import com.google.gson.annotations.SerializedName;
import com.union.travel.tvtest2.model.Brand;

import java.util.List;

public class WatchApiResponse {

	//@SerializedName("response")
	private List<Brand> brands;

	public String toString() {
		return "UserApiResponse size [brandList=" + brands.size() + "]";
	}


}
