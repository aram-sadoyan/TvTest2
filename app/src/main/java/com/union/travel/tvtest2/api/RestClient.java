package com.union.travel.tvtest2.api;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import com.union.travel.tvtest2.api.service.WatchApiService;
import com.union.travel.tvtest2.utils.AppConstants;
import com.union.travel.tvtest2.utils.DefaultGsonBuilder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.webkit.CookieSyncManager.createInstance;
import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;

public class RestClient {
	private static final String TAG = "RestClient";
	private static RestClient thisInstance = null;

	public static final int CACHE_SIZE = 10 * 1024 * 1024;


	private WatchApiService watchApiService ;


	public static final String TEST_BASE_URL = "https://www.qicharge.am/"; //todo rename

	public RestClient(Context context){
//		if (enableCaches) {
//			createCacheAbleInstance(context);
//		} else {
			createInstance(context);
		//}
	}

	private void createCacheAbleInstance(Context context) {

	}


	private void createInstance(Context context) {

//		Map<Type, JsonDeserializer> map = new HashMap<>();
//		map.put(CardCollectionResponse.class, new CardCollectionResponseDeserializer());
//		Gson gson = DefaultGsonBuilder.getGsonWithTypeAdapters(map);

		Retrofit retrofit;
		//String baseUrl = AppConstants.DEFAULT_BASE_URL;

		File cacheDir = new File(context.getCacheDir().getAbsolutePath() + "/NewWatchCache2");
		Cache cache = new Cache(cacheDir, CACHE_SIZE);

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				//.cache(cache)
				.addInterceptor(new LoggingInterceptor())
				.build();

		retrofit = new Retrofit.Builder()
				.baseUrl(TEST_BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.client(okHttpClient)
				.build();

		watchApiService = retrofit.create(WatchApiService.class);
	}




	class LoggingInterceptor implements Interceptor {
		@Override
		public Response intercept(Interceptor.Chain chain) throws IOException {
			Request request = chain.request();

			Log.d(TAG, String.format("Sending request %s on %s%n%s",
					request.url(), chain.connection(), request.headers()));
			long t1 = System.nanoTime();

			Response response = chain.proceed(request);

			long t2 = System.nanoTime();
			Log.d(TAG, String.format("Received response for %s with code %d in %.1fms%n%s",
					response.request().url(), response.code(), (t2 - t1) / 1e6d, response.headers()));

			return response;
		}
	}


	public static RestClient getInstance(Context context) {
		if (thisInstance == null) {
			thisInstance = new RestClient(context);
		}
		return thisInstance;
	}

	public WatchApiService getWatchApiService() {
		return watchApiService;
	}
}
