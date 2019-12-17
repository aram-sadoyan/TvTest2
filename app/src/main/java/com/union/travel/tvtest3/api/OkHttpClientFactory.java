//package com.union.travel.tvtest2.api;
//
//import android.content.Context;
//import android.util.Log;
//
//import androidx.annotation.Nullable;
//
//import com.airbnb.lottie.L;
//
//import java.io.File;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.Cache;
//import okhttp3.Interceptor;
//import okhttp3.OkHttpClient;
//import okhttp3.Protocol;
//
//public class OkHttpClientFactory {
//
//	private static OkHttpClientFactory instance;
//	//10MB;
//	public static final int CACHE_SIZE = 10 * 1024 * 1024;
//	private static final int CONNECTION_TIMEOUT = 10;
//	private static final int READ_TIMEOUT = 10;
//	private static final int WRITE_TIMEOUT = 20;
//
//	private OkHttpNetworkPerformanceInterceptor networkPerformanceInterceptor;
//	private AnalyticsInterceptor analyticsInterceptor;
//	private UserAgentInterceptor userAgentInterceptor;
//
//	private boolean cloudflareMode = false;
//
//	private OkHttpClientFactory(Context context) {
//		networkPerformanceInterceptor = new OkHttpNetworkPerformanceInterceptor();
//		analyticsInterceptor = new AnalyticsInterceptor(context);
//		userAgentInterceptor = new UserAgentInterceptor();
//	}
//
//	public static OkHttpClientFactory getInstance() {
//		if (instance == null) {
//			Log.d("dwd","OkHttpClientFactory is not initialized");
//		}
//		return instance;
//	}
//
//	public static void init(Context context) {
//		instance = new OkHttpClientFactory(context);
//	}
//
//	public void setCloudflareMode(boolean cloudflareMode) {
//		this.cloudflareMode = cloudflareMode;
//	}
//
//	public OkHttpClient getDefaultClient(File cacheDir, int connectionTimeout, int readTimeout, int writeTimeout, Interceptor... additionalInterceptor) {
//		Cache cache = new Cache(cacheDir, CACHE_SIZE);
//		if (cloudflareMode) {
//			return CFMobile.createOkHttp3Client(getClientBuilder(cache, connectionTimeout, writeTimeout, readTimeout, TimeUnit.SECONDS, null, additionalInterceptor));
//		} else {
//			return getClientBuilder(cache, connectionTimeout, writeTimeout, readTimeout, TimeUnit.SECONDS, null, additionalInterceptor).build();
//		}
//	}
//
//	public OkHttpClient getDefaultClient(File cacheDir, int cacheSize, Interceptor... additionalInterceptor) {
//		Cache cache = new Cache(cacheDir, cacheSize);
//		if (cloudflareMode) {
//			return CFMobile.createOkHttp3Client(getClientBuilder(cache, CONNECTION_TIMEOUT, WRITE_TIMEOUT, READ_TIMEOUT, TimeUnit.SECONDS, null, additionalInterceptor));
//		} else {
//			return getClientBuilder(cache, CONNECTION_TIMEOUT, WRITE_TIMEOUT, READ_TIMEOUT, TimeUnit.SECONDS, null, additionalInterceptor).build();
//		}
//	}
//
//	public OkHttpClient getDefaultClient(File cacheDir, int cacheSize, List<Protocol> protocols, Interceptor... additionalInterceptor) {
//		Cache cache = new Cache(cacheDir, cacheSize);
//		if (cloudflareMode) {
//			return CFMobile.createOkHttp3Client(getClientBuilder(cache, CONNECTION_TIMEOUT, WRITE_TIMEOUT, READ_TIMEOUT, TimeUnit.SECONDS, protocols, additionalInterceptor));
//		} else {
//			return getClientBuilder(cache, CONNECTION_TIMEOUT, WRITE_TIMEOUT, READ_TIMEOUT, TimeUnit.SECONDS, protocols, additionalInterceptor).build();
//		}
//	}
//
//	public OkHttpClient getDefaultClient(Interceptor... additionalInterceptor) {
//		if (cloudflareMode) {
//			return CFMobile.createOkHttp3Client(getClientBuilder(null, CONNECTION_TIMEOUT, WRITE_TIMEOUT, READ_TIMEOUT, TimeUnit.SECONDS, null, additionalInterceptor));
//		} else {
//			return getClientBuilder(null, CONNECTION_TIMEOUT, WRITE_TIMEOUT, READ_TIMEOUT, TimeUnit.SECONDS, null, additionalInterceptor).build();
//		}
//	}
//
//	public OkHttpClient getDefaultClient(File cacheDir) {
//		Cache cache = new Cache(cacheDir, CACHE_SIZE);
//		if (cloudflareMode) {
//			return CFMobile.createOkHttp3Client(getClientBuilder(cache, CONNECTION_TIMEOUT, WRITE_TIMEOUT, READ_TIMEOUT, TimeUnit.SECONDS, null));
//		} else {
//			return getClientBuilder(cache, CONNECTION_TIMEOUT, WRITE_TIMEOUT, READ_TIMEOUT, TimeUnit.SECONDS, null).build();
//		}
//	}
//
//	public OkHttpClient getDefaultClient() {
//		if (cloudflareMode) {
//			return CFMobile.createOkHttp3Client(getClientBuilder(null, CONNECTION_TIMEOUT, WRITE_TIMEOUT, READ_TIMEOUT, TimeUnit.SECONDS, null));
//		} else {
//			return getClientBuilder(null, CONNECTION_TIMEOUT, WRITE_TIMEOUT, READ_TIMEOUT, TimeUnit.SECONDS, null).build();
//		}
//	}
//
//	public OkHttpClient getClientBuilder(File cacheDir, int connectionTimeout, int readTimeout, int writeTimeout, Interceptor... additionalInterceptor) {
//		Cache cache = new Cache(cacheDir, CACHE_SIZE);
//		if (cloudflareMode) {
//			return CFMobile.createOkHttp3Client(getClientBuilder(cache, connectionTimeout, writeTimeout, readTimeout, TimeUnit.SECONDS, null, additionalInterceptor));
//		} else {
//			return getClientBuilder(cache, connectionTimeout, writeTimeout, readTimeout, TimeUnit.SECONDS, null, additionalInterceptor).build();
//		}
//	}
//
//	public OkHttpClient getDefaultClient(File cacheDir, Interceptor... additionalInterceptor) {
//		Cache cache = new Cache(cacheDir, CACHE_SIZE);
//		if (cloudflareMode) {
//			return CFMobile.createOkHttp3Client(getClientBuilder(cache, CONNECTION_TIMEOUT, WRITE_TIMEOUT, READ_TIMEOUT, TimeUnit.SECONDS, null, additionalInterceptor));
//		} else {
//			return getClientBuilder(cache, CONNECTION_TIMEOUT, WRITE_TIMEOUT, READ_TIMEOUT, TimeUnit.SECONDS, null, additionalInterceptor).build();
//		}
//	}
//
//
//	private OkHttpClient.Builder getClientBuilder(@Nullable Cache cache, long connectionTimeout, long writeTimeout, long readTimeout, TimeUnit timeoutTimeUnit, @Nullable List<Protocol> protocols, @Nullable Interceptor... additionalInterceptors) {
//
//		OkHttpClient.Builder builder = new OkHttpClient.Builder()
//				.connectTimeout(connectionTimeout, timeoutTimeUnit)
//				.readTimeout(readTimeout, timeoutTimeUnit)
//				.writeTimeout(writeTimeout, timeoutTimeUnit);
//
//		if (additionalInterceptors != null) {
//			for (Interceptor interceptor : additionalInterceptors) {
//				if (interceptor != null) {
//					builder.addInterceptor(interceptor);
//				}
//			}
//		}
//
//		builder.addInterceptor(this.analyticsInterceptor)
//				.addInterceptor(this.networkPerformanceInterceptor)
//				.addInterceptor(this.userAgentInterceptor);
//
//		if (cache != null) {
//			builder.cache(cache);
//		}
//		if (protocols != null) {
//			builder.protocols(protocols);
//		}
//		NetService.enableTls12OnPreLollipop(builder);
//		return builder;
//	}
//
//}
