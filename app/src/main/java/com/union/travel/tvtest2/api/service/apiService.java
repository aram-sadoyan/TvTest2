package com.union.travel.tvtest2.api.service;

import com.union.travel.tvtest2.api.Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface apiService {

	@FormUrlEncoded
	@GET("url/point")
	Call<Response> addShopPackage(@Path("shopPackageId") String shopPackageId,
								  @Query("key") String key,
								  @Field("data") String data);

}
