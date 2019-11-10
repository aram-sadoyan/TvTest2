package com.union.travel.tvtest2.api;

import android.content.Context;
import android.util.Log;

import com.union.travel.tvtest2.utils.AppConstants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

import static android.webkit.CookieSyncManager.createInstance;
import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;

public class RestClient {
	private static final String TAG = "RestClient";

	public RestClient(Context context, boolean enableCaches){
		if (enableCaches) {
			createCacheAbleInstance(context);
		} else {
			createInstance(context);
		}
	}

	private void createCacheAbleInstance(Context context) {

	}


	private void createInstance(Context context) {
		Retrofit retrofit;
		OkHttpClient client;
		String baseUrl = AppConstants.DEFAULT_BASE_URL;
		client = new OkHttpClient().newBuilder().build();

		retrofit = new Retrofit.Builder()
				.baseUrl(baseUrl)
				.client(client)
				.build();

		//shopApiService = retrofit.create(ShopApiService.class);
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
}
