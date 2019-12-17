package com.union.travel.tvtest3.api.service;

import com.union.travel.tvtest3.api.UserApiResponse;
import com.union.travel.tvtest3.model.Brand;
import com.union.travel.tvtest3.model.SensorModelData;

import java.util.List;

import retrofit2.Call;
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


//	@GET("/TimeWatch/kyaj.html")
//	public Call<List<Brand>> getBrandList();

	@GET("/TimeWatch/NewJson.php")
	public Call<List<Brand>> getBrandList();

	@GET("/TimeWatch/JsonArduino.php")
	public Call<List<SensorModelData>> getSensorId();

}
