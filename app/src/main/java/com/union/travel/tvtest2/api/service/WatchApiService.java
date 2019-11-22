package com.union.travel.tvtest2.api.service;

import com.union.travel.tvtest2.api.Response;
import com.union.travel.tvtest2.api.UserApiResponse;
import com.union.travel.tvtest2.model.Brand;
import com.union.travel.tvtest2.response.WatchApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WatchApiService {

//	@FormUrlEncoded
//	@GET("")
//	Call<Response> addShopPackage();

	@GET("/api/users/{id}")
	public Call<UserApiResponse> getUser(@Path("id") long id);


//	@GET("TimeWatch/NewJson.php")
//	public Call<Response> getQiTest();


	@GET("/TimeWatch/uploads/NewJson.php")
	public Call<List<Brand>> getBrandList();

}
