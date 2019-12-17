package com.union.travel.tvtest3.response;

import com.union.travel.tvtest3.model.Brand;

import java.util.List;

public class WatchApiResponse {

	//@SerializedName("response")
	private List<Brand> brands;

	public String toString() {
		return "UserApiResponse size [brandList=" + brands.size() + "]";
	}


}
