package com.union.travel.tvtest2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.common.ImageDecodeOptionsBuilder;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;


import java.io.File;
import java.lang.ref.WeakReference;

import static com.facebook.common.util.UriUtil.LOCAL_RESOURCE_SCHEME;

/**
 * see SimpleDraweeView full attribute set
 * <p>
 * Do Not use ImageView's drawables, scale methods with SimpleDraweeView
 * <p>
 * <com.facebook.drawee.view.SimpleDraweeView
 * android:id="@+id/my_image_view"
 * android:layout_width="20dp"
 * android:layout_height="20dp"
 * fresco:fadeDuration="300"
 * fresco:actualImageScaleType="focusCrop"
 * fresco:placeholderImage="@color/wait_color"
 * fresco:placeholderImageScaleType="fitCenter"
 * fresco:failureImage="@drawable/error"
 * fresco:failureImageScaleType="centerInside"
 * fresco:retryImage="@drawable/retrying"
 * fresco:retryImageScaleType="centerCrop"
 * fresco:progressBarImage="@drawable/progress_bar"
 * fresco:progressBarImageScaleType="centerInside"
 * fresco:progressBarAutoRotateInterval="1000"
 * fresco:backgroundImage="@color/blue"
 * fresco:overlayImage="@drawable/watermark"
 * fresco:pressedStateOverlayImage="@color/red"
 * fresco:roundAsCircle="false"
 * fresco:roundedCornerRadius="1dp"
 * fresco:viewAspectRatio="1"
 * fresco:roundTopLeft="true"
 * fresco:roundTopRight="false"
 * fresco:roundBottomLeft="false"
 * fresco:roundBottomRight="true"
 * fresco:roundWithOverlayColor="@color/corner_color"
 * fresco:roundingBorderWidth="2dp"
 * fresco:roundingBorderColor="@color/border_color"
 * />
 */

/**
 * Created by Arman on 12/13/16.
 */

public class FrescoLoader {

	public static int UNDEFINED_SIZE = -1;
	public int maxLocalImageSize = 1024;

	/**
	 * parses given url to fresco loading Uri
	 */
	public static Uri parseToUri(String uriOrPath) {
		if (TextUtils.isEmpty(uriOrPath)) return null;
		Uri uri = Uri.parse(uriOrPath);
		if (!UriUtil.isNetworkUri(uri)) {
			uri = Uri.parse("file:" + (uriOrPath.startsWith("/") ? uriOrPath : "/" + uriOrPath));
		}
		return uri;
	}

	/**
	 * parses given res to fresco loading Uri
	 */
	public static Uri parseToUri(@DrawableRes int resourceID) {
		return new Uri.Builder()
				.scheme(LOCAL_RESOURCE_SCHEME)
				.path(String.valueOf(resourceID))
				.build();
	}

	public static Uri parseAssetToUri(String assetPath) {
		return new Uri.Builder()
				.scheme(UriUtil.LOCAL_ASSET_SCHEME)
				.path(assetPath)
				.build();
	}

	public static ControllerCallback getControllerCallBackWithGifHandleSparseArray(SparseArray<WeakReference<Animatable>> gifItemsAnimationMap, int vHashCode) {
		return new ControllerCallback() {
			@Override
			public void onFinalImageHasSet(String id, ImageInfo imageInfo, Animatable animatable) {
				if (animatable != null && gifItemsAnimationMap != null) {
					gifItemsAnimationMap.put(vHashCode, new WeakReference<>(animatable));
				}
			}

			@Override
			public void onFailedLoad(String id, Throwable throwable) {
				if (gifItemsAnimationMap != null) {
					gifItemsAnimationMap.remove(vHashCode);
				}
			}
		};
	}

	public void loadWithParams(String url, DraweeView imageView, ControllerListener<ImageInfo> listener, boolean isZoomable) {
		Uri uri = FrescoLoader.parseToUri(url);
		if (uri == null) return;
		loadWithParams(url, uri, imageView, listener, isZoomable, UNDEFINED_SIZE, UNDEFINED_SIZE);
	}

	public void loadWithParams(String url, DraweeView imageView, int width, int height, ControllerListener<ImageInfo> listener) {
		Uri uri = FrescoLoader.parseToUri(url);
		if (uri == null) return;
		loadWithParams(url, uri, null, imageView, width, height, listener);
	}

	public void loadWithParams(String hiResurl, String lowResUrl, DraweeView imageView, final ControllerCallback
			listener, boolean isZoomable) {
		Uri uriHi = FrescoLoader.parseToUri(hiResurl);
		Uri uriLowRes = FrescoLoader.parseToUri(lowResUrl);
		if (uriHi == null) return;
		loadWithParams(hiResurl, uriHi, uriLowRes, imageView, listener, isZoomable, UNDEFINED_SIZE, UNDEFINED_SIZE, false);
	}

	public void loadWithParams(String hiResurl, String lowResUrl, DraweeView imageView, final ControllerCallback
			listener, boolean isZoomable, boolean retainImageOnFailure) {
		Uri uriHi = FrescoLoader.parseToUri(hiResurl);
		Uri uriLowRes = FrescoLoader.parseToUri(lowResUrl);
		if (uriHi == null) return;
		loadWithParams(hiResurl, uriHi, uriLowRes, imageView, listener, isZoomable, UNDEFINED_SIZE, UNDEFINED_SIZE, retainImageOnFailure);
	}

	public void loadWithParams(String url, DraweeView imageView, ControllerListener<ImageInfo> listener, @DrawableRes int placeHolder) {
		Uri uri = FrescoLoader.parseToUri(url);
		if (uri == null) return;
		if (imageView.getHierarchy() != null && placeHolder != 0 && imageView.getHierarchy() instanceof GenericDraweeHierarchy) {
			((GenericDraweeHierarchy) (imageView.getHierarchy())).setPlaceholderImage(placeHolder);
		}
		loadWithParams(url, uri, imageView, listener, false, UNDEFINED_SIZE, UNDEFINED_SIZE);
	}

	public void loadWithParams(String url, DraweeView imageView, ControllerListener<ImageInfo> listener, Drawable placeHolder) {
		Uri uri = FrescoLoader.parseToUri(url);
		if (uri == null) return;
		if (imageView.getHierarchy() != null && placeHolder != null && imageView.getHierarchy() instanceof GenericDraweeHierarchy) {
			((GenericDraweeHierarchy) (imageView.getHierarchy())).setPlaceholderImage(placeHolder);
		}
		loadWithParams(url, uri, imageView, listener, false, UNDEFINED_SIZE, UNDEFINED_SIZE);
	}

//	public void loadWithParams(@DrawableRes int resourceId, BitmapCallback callBack, int size) {
//		Uri uri = FrescoLoader.parseToUri(resourceId);
//		loadWithParamsAndGetBitmap(uri, callBack, size);
//	}

	public void loadWithParams(@DrawableRes int resourceId, DraweeView imageView, ControllerListener<ImageInfo> listener) {
		Uri uri = FrescoLoader.parseToUri(resourceId);
		loadWithParams(null, uri, imageView, listener, false, UNDEFINED_SIZE, UNDEFINED_SIZE);
	}

	public void loadWithParams(String pathOrUrl, DraweeView imageView, ControllerListener<ImageInfo> listener) {
		Uri uri = FrescoLoader.parseToUri(pathOrUrl);
		loadWithParams(pathOrUrl, uri, imageView, listener, false, UNDEFINED_SIZE, UNDEFINED_SIZE);
	}

	public void loadWithParams(@DrawableRes int resourceId, DraweeView imageView, ControllerListener<ImageInfo> listener,
							   @DrawableRes int placeHolder) {
		Uri uri = FrescoLoader.parseToUri(resourceId);
		if (imageView.getHierarchy() != null && placeHolder != 0 && imageView.getHierarchy() instanceof GenericDraweeHierarchy) {
			((GenericDraweeHierarchy) (imageView.getHierarchy())).setPlaceholderImage(placeHolder);
		}
		loadWithParams(null, uri, imageView, listener, false, UNDEFINED_SIZE, UNDEFINED_SIZE);
	}

	public void loadWithParams(@DrawableRes int resourceId, DraweeView imageView, ControllerListener<ImageInfo> listener, Drawable placeHolder) {
		Uri uri = new Uri.Builder()
				.scheme(LOCAL_RESOURCE_SCHEME)
				.path(String.valueOf(resourceId))
				.build();
		if (imageView.getHierarchy() != null && placeHolder != null && imageView.getHierarchy() instanceof GenericDraweeHierarchy) {
			((GenericDraweeHierarchy) (imageView.getHierarchy())).setPlaceholderImage(placeHolder);
		}
		loadWithParams(null, uri, imageView, listener, false, UNDEFINED_SIZE, UNDEFINED_SIZE);
	}

	public void loadWithParamsWithCustomWidthHeight(String path, DraweeView imageView, ControllerListener<ImageInfo> listener,
													int width, int height) {
		Uri uri = FrescoLoader.parseToUri(path);
		loadWithParams(path, uri, imageView, listener, false, width, height);
	}

//	public void loadWithParamsWithCustomJpegDecoder(String path, DraweeView imageView, ControllerListener<ImageInfo> listener,
//													int customWidth, int customHeight) {
//		Uri uri = FrescoLoader.parseToUri(path);
//		if (uri == null) return;
//
//		ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
//		if (UriUtil.isNetworkUri(uri)) {
//			imageRequestBuilder.setProgressiveRenderingEnabled(isJpg(uri.toString()));
//		} else {
//			int width = 0, height = 0;
//			if (imageView.getLayoutParams().width <= 0) {
//				ViewGroup.LayoutParams parentLayoutParams = ((ViewGroup) imageView.getParent()).getLayoutParams();
//				if (parentLayoutParams != null) {
//					width = parentLayoutParams.width;
//					height = parentLayoutParams.height;
//				}
//			} else {
//				width = imageView.getLayoutParams().width;
//				height = imageView.getLayoutParams().height;
//			}
//			if (width <= 0 || height <= 0) {
//				width = height = maxLocalImageSize;
//			}
//
//			if (customWidth != UNDEFINED_SIZE) {
//				width = customWidth;
//				if (customHeight != UNDEFINED_SIZE) {
//					height = customHeight;
//				}
//				ImageDecodeOptionsBuilder imageDecodeOptionsBuilder = new ImageDecodeOptionsBuilder();
//				if (isJpg(uri.toString())) {
//					imageDecodeOptionsBuilder.setCustomImageDecoder(new JpegDecoderFresco());
//				}
//				CustomImageDecodeOptions ops = new CustomImageDecodeOptions(imageDecodeOptionsBuilder);
//				ops.setCustomDecodeWidthHeight(width, height, path);
//				imageRequestBuilder.setImageDecodeOptions(ops);
//			}
//
//			imageRequestBuilder.setResizeOptions(new ResizeOptions(width, height));
//		}
//
//		PipelineDraweeControllerBuilder draweeControllerBuilder = Fresco.newDraweeControllerBuilder()
//				.setImageRequest(imageRequestBuilder.build())
//				.setOldController(imageView.getController())
//				.setRetainImageOnFailure(false)
//				.setControllerListener(listener)
//				.setAutoPlayAnimations(true);
//
//		imageView.setController(draweeControllerBuilder.build());
//	}

	public void loadAssetWithParamsWithCustomWidthHeight(String path, DraweeView imageView, ControllerListener<ImageInfo> listener,
														 int width, int height) {
		Uri uri = FrescoLoader.parseAssetToUri(path);
		loadWithParams(path, uri, imageView, listener, false, width, height);
	}

	public void loadWithParams(String path, Uri uri, DraweeView imageView, ControllerListener<ImageInfo> listener, boolean
			isZoomable, int customWidth, int customHeight) {
		loadWithParams(path, uri, null, imageView, listener, isZoomable, customWidth, customHeight, false);
	}

	public void loadWithParams(String path, Uri uri, Uri lowResUri, DraweeView imageView, ControllerListener<ImageInfo> listener,
							   boolean isZoomable, int customWidth, int customHeight, boolean retainImageOnFailure) {
		if (uri == null) return;

		ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
		if (UriUtil.isNetworkUri(uri)) {
			imageRequestBuilder.setProgressiveRenderingEnabled(isJpg(uri.toString()));
		} else {
			int width = 0, height = 0;
			if (imageView.getLayoutParams().width <= 0) {
				ViewGroup.LayoutParams parentLayoutParams = ((ViewGroup) imageView.getParent()).getLayoutParams();
				if (parentLayoutParams != null) {
					width = parentLayoutParams.width;
					height = parentLayoutParams.height;
				}
			} else {
				width = imageView.getLayoutParams().width;
				height = imageView.getLayoutParams().height;
			}
			if (width <= 0 || height <= 0) {
				width = height = maxLocalImageSize;
			}

			if (customWidth != UNDEFINED_SIZE) {
				width = customWidth;
				if (customHeight != UNDEFINED_SIZE) {
					height = customHeight;
				}
				CustomImageDecodeOptions ops = new CustomImageDecodeOptions(new ImageDecodeOptionsBuilder());
				ops.setCustomDecodeWidthHeight(width, height, path);
				imageRequestBuilder.setImageDecodeOptions(ops);
			}

			imageRequestBuilder.setResizeOptions(new ResizeOptions(width, height));
		}

		PipelineDraweeControllerBuilder draweeControllerBuilder = Fresco.newDraweeControllerBuilder()
				.setImageRequest(imageRequestBuilder.build())
				.setOldController(imageView.getController())
				.setRetainImageOnFailure(retainImageOnFailure)
				.setControllerListener(listener)
				.setAutoPlayAnimations(true);
		if (lowResUri != null && !lowResUri.equals(uri)) {
			draweeControllerBuilder.setLowResImageRequest(ImageRequestBuilder.newBuilderWithSource(lowResUri).build());
		}

//		if (isZoomable) {
//			AnimatedZoomableController controller = AnimatedZoomableController.newInstance();
//			controller.setTransform(((ZoomableDraweeView) imageView).getZoomableController().getTransform());
//			((ZoomableDraweeView) imageView).setZoomableController(controller);
//			((ZoomableDraweeView) imageView).getZoomableController().setEnabled(true);
//		}

		imageView.setController(draweeControllerBuilder.build());
	}

	public void loadWithParams(String path, Uri uri, Uri lowResUri, DraweeView imageView, int imageWidth, int imageHeight, ControllerListener<ImageInfo> listener) {
		if (uri == null) return;

		ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
		CustomImageDecodeOptions ops = new CustomImageDecodeOptions(new ImageDecodeOptionsBuilder());
		ops.setCustomDecodeWidthHeight(imageWidth, imageHeight, path);
		imageRequestBuilder.setImageDecodeOptions(ops);
		imageRequestBuilder.setResizeOptions(new ResizeOptions(imageWidth, imageHeight));

		PipelineDraweeControllerBuilder draweeControllerBuilder = Fresco.newDraweeControllerBuilder()
				.setImageRequest(imageRequestBuilder.build())
				.setOldController(imageView.getController())
				.setControllerListener(listener)
				.setAutoPlayAnimations(false);
		if (lowResUri != null) {
			draweeControllerBuilder.setLowResImageRequest(ImageRequestBuilder.newBuilderWithSource(lowResUri).build());
		}

		imageView.setController(draweeControllerBuilder.build());
	}

	public void loadWithParams(final Uri uri, DraweeView imageView, boolean disableCache) {
		loadWithParams(uri, imageView, false, null, disableCache);
	}

	public void loadWithParams(final Uri uri, DraweeView imageView, boolean animateGifs, ControllerListener<ImageInfo> listener) {
		loadWithParams(uri, imageView, animateGifs, listener, false);
	}

	public void loadWithParams(final Uri uri, DraweeView imageView, boolean animateGifs, ControllerListener<ImageInfo> listener, boolean disableCache) {
		if (uri == null) {
			return;
		}

		ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
		if (UriUtil.isNetworkUri(uri)) {
			imageRequestBuilder.setProgressiveRenderingEnabled(isJpg(uri.toString()));
		} else {
			int width, height;
			if (imageView.getLayoutParams().width <= 0) {
				width = ((ViewGroup) imageView.getParent()).getLayoutParams().width;
				height = ((ViewGroup) imageView.getParent()).getLayoutParams().height;
			} else {
				width = imageView.getLayoutParams().width;
				height = imageView.getLayoutParams().height;
			}
			if (width <= 0 || height <= 0) {
				width = height = maxLocalImageSize;
			}

			CustomImageDecodeOptions ops = new CustomImageDecodeOptions(new ImageDecodeOptionsBuilder().setForceStaticImage(true));
			ops.setCustomDecodeWidthHeight(width, height, uri.getPath());
			imageRequestBuilder.setImageDecodeOptions(ops);
			imageRequestBuilder.setResizeOptions(new ResizeOptions(width, height));
		}

		DraweeController draweeController = Fresco.newDraweeControllerBuilder()
				.setImageRequest(imageRequestBuilder.build())
				.setAutoPlayAnimations(animateGifs)
				.setOldController(imageView.getController())
				.setControllerListener(listener)
				.build();

		imageView.setController(draweeController);
	}

	public void loadWithParams(final String path, int width, int height, DraweeView imageView, ControllerListener<ImageInfo> listener) {
		if (path == null) {
			return;
		}

		Uri uri = FrescoLoader.parseAssetToUri(path);

		ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
		CustomImageDecodeOptions ops = new CustomImageDecodeOptions(new ImageDecodeOptionsBuilder());
		ops.setCustomDecodeWidthHeight(width, height, uri.getPath());
		imageRequestBuilder.setImageDecodeOptions(ops);

		imageRequestBuilder.setResizeOptions(new ResizeOptions(width, height));

		DraweeController draweeController = Fresco.newDraweeControllerBuilder()
				.setImageRequest(imageRequestBuilder.build())
				.setOldController(imageView.getController())
				.setControllerListener(listener)
				.build();

		imageView.setController(draweeController);
	}

	/**
	 * Deprecated pls use fresco:roundAsCircle="true" instead
	 */
	@Deprecated
	public void loadWithParamsAsCircle(String url, SimpleDraweeView imageView, ControllerListener listener) {

		if (imageView.getHierarchy() != null) {
			RoundingParams roundingParams = RoundingParams.asCircle();
			imageView.getHierarchy().setRoundingParams(roundingParams);
			loadWithParams(url, imageView, listener, false);
		} else {}

			//L.e("loadWithParamsAsCircle", " can't load as circle if no DraweeHierarchy is set");
	}

//	public void loadWithParamsAndGetDrawable(String url, final Context context, final CallBack callBack) {
//		loadWithParamsAndGetDrawable(url, context, callBack, UNDEFINED_SIZE, UNDEFINED_SIZE);
//	}

//	public void loadWithParamsAndGetDrawable(String url, final Context context, final CallBack callBack, int customWidth, int customHeight) {
//		Uri uri = FrescoLoader.parseToUri(url);
//		if (uri == null) return;
//
//		ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
//
//		if (UriUtil.isNetworkUri(uri)) {
//			imageRequestBuilder.setProgressiveRenderingEnabled(isJpg(uri.toString()));
//		} else {
//			if (customWidth != UNDEFINED_SIZE) {
//				int width = customWidth;
//				int height = customWidth;
//				if (customHeight != UNDEFINED_SIZE) {
//					height = customHeight;
//				}
//				CustomImageDecodeOptions ops = new CustomImageDecodeOptions(new ImageDecodeOptionsBuilder());
//				ops.setCustomDecodeWidthHeight(width, height, url);
//				imageRequestBuilder.setImageDecodeOptions(ops);
//			}
//		}
//
//		ImageRequest imageRequest = imageRequestBuilder.setResizeOptions(new ResizeOptions(maxLocalImageSize,
//				maxLocalImageSize)).build();
//
//		ImagePipeline imagePipeline = Fresco.getImagePipeline();
//
//		final DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, null);
//
//		final CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(imageRequest, null);
//
//		dataSource.subscribe(new UniversalBitmapDataSubscriber() {
//			@Override
//			protected void onNewResultImpl(Bitmap bitmap) {
//				if (callBack != null && context != null && bitmap != null && !bitmap.isRecycled()) {
//
//					File file = null;
//					if (cacheKey != null) {
//						BinaryResource resource = ImagePipelineFactory.getInstance().getMainFileCache().getResource(cacheKey);
//						if (resource != null) {
//							file = ((FileBinaryResource) resource).getFile();
//						}
//					}
//
//					callBack.onDrawableReady(new BitmapDrawable(context.getResources(), bitmap), file);
//					dataSource.close();
//				} else {
//					onFailureImpl(dataSource);
//				}
//				if (shouldRecycleBitmap && bitmap != null && !bitmap.isRecycled()) {
//					bitmap.recycle();
//					shouldRecycleBitmap = false;
//				}
//			}
//
//			@Override
//			protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
//				dataSource.close();
//				if (callBack != null) {
//					callBack.OnLoadFailed();
//				}
//			}
//		}, CallerThreadExecutor.getInstance());
//	}

//	public DataSource loadWithParamsAndGetBitmap(String url, BitmapCallback callBack, int size) {
//		Uri uri = FrescoLoader.parseToUri(url);
//		return loadWithParamsAndGetBitmap(uri, callBack, size);
//	}

//	public DataSource loadWithParamsAndGetBitmap(Uri uri, BitmapCallback callBack, int size) {
//		if (uri == null) {
//			return null;
//		}
//
//		ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
//		ImageDecodeOptionsBuilder imageDecodeOptionsBuilder = new ImageDecodeOptionsBuilder();
//
//		if (size != UNDEFINED_SIZE) {
//			if (isJpg(uri.toString())) {
//				imageDecodeOptionsBuilder.setCustomImageDecoder(new JpegDecoderFresco());
//			}
//			CustomImageDecodeOptions ops = new CustomImageDecodeOptions(imageDecodeOptionsBuilder);
//			ops.setCustomDecodeWidthHeight(size, size, uri.getPath());
//			imageRequestBuilder.setImageDecodeOptions(ops);
//		}
//
//		final DataSource<CloseableReference<CloseableImage>> dataSource =
//				Fresco.getImagePipeline().fetchDecodedImage(imageRequestBuilder.build(), null);
//
//		dataSource.subscribe(new UniversalBitmapDataSubscriber() {
//			@Override
//			protected void onNewResultImpl(Bitmap bitmap) {
//				if (callBack != null && bitmap != null && !bitmap.isRecycled()) {
//					Bitmap copyBitmap = bitmap.copy(bitmap.getConfig(), false);
//					callBack.onBitmapReady(copyBitmap, UriUtil.isNetworkUri(uri) ? uri.toString() : uri.getPath());
//				} else if (callBack != null) {
//					callBack.onLoadFailed();
//				}
//				dataSource.close();
//			}
//
//			@Override
//			protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
//				if (callBack != null) {
//					callBack.onLoadFailed();
//				}
//				dataSource.close();
//			}
//		}, CallerThreadExecutor.getInstance());
//		return dataSource;
//	}

//	public DataSource loadWithParamsAndGetBitmap(String url, BitmapCallback callBack, int width, int height) {
//		Uri uri = FrescoLoader.parseToUri(url);
//		if (uri == null) {
//			return null;
//		}
//
//		ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
//		ImageDecodeOptionsBuilder imageDecodeOptionsBuilder = new ImageDecodeOptionsBuilder();
//
//		if (width != UNDEFINED_SIZE && height != UNDEFINED_SIZE) {
//			if (isJpg(uri.toString())) {
//				imageDecodeOptionsBuilder.setCustomImageDecoder(new JpegDecoderFresco());
//			}
//			CustomImageDecodeOptions ops = new CustomImageDecodeOptions(imageDecodeOptionsBuilder);
//			ops.setCustomDecodeWidthHeight(width, height, uri.getPath());
//			imageRequestBuilder.setImageDecodeOptions(ops);
//		}
//
//		final DataSource<CloseableReference<CloseableImage>> dataSource =
//				Fresco.getImagePipeline().fetchDecodedImage(imageRequestBuilder.build(), null);
//
//		dataSource.subscribe(new UniversalBitmapDataSubscriber() {
//			@Override
//			protected void onNewResultImpl(Bitmap bitmap) {
//				if (callBack != null && bitmap != null && !bitmap.isRecycled()) {
//					Bitmap copyBitmap = bitmap.copy(bitmap.getConfig(), false);
//					callBack.onBitmapReady(copyBitmap, UriUtil.isNetworkUri(uri) ? uri.toString() : uri.getPath());
//				} else if (callBack != null) {
//					callBack.onLoadFailed();
//				}
//				dataSource.close();
//			}
//
//			@Override
//			protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
//				if (callBack != null) {
//					callBack.onLoadFailed();
//				}
//				dataSource.close();
//			}
//		}, CallerThreadExecutor.getInstance());
//		return dataSource;
//	}

//	public DataSource loadWithParamsAndGetBitmapForMaxPixels(String url, BitmapCallback callBack, int size) {
//		Uri uri = FrescoLoader.parseToUri(url);
//		return loadWithParamsAndGetBitmapForMaxPixels(uri, callBack, size);
//	}

//	public DataSource loadWithParamsAndGetBitmapForMaxPixels(Uri uri, BitmapCallback callBack, int maxPixel) {
//		if (uri == null) {
//			return null;
//		}
//
//		ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
//		ImageDecodeOptionsBuilder imageDecodeOptionsBuilder = new ImageDecodeOptionsBuilder();
//
//		if (maxPixel != UNDEFINED_SIZE) {
//			if (isJpg(uri.toString())) {
//				imageDecodeOptionsBuilder.setCustomImageDecoder(new JpegDecoderFresco());
//			}
//			CustomImageDecodeOptions ops = new CustomImageDecodeOptions(imageDecodeOptionsBuilder);
//			ops.setCustomDecodeMaxPixel(maxPixel);
//			imageRequestBuilder.setImageDecodeOptions(ops);
//		}
//
//		final DataSource<CloseableReference<CloseableImage>> dataSource =
//				Fresco.getImagePipeline().fetchDecodedImage(imageRequestBuilder.build(), null);
//
//		dataSource.subscribe(new UniversalBitmapDataSubscriber() {
//			@Override
//			protected void onNewResultImpl(Bitmap bitmap) {
//				if (callBack != null && bitmap != null && !bitmap.isRecycled()) {
//					Bitmap copyBitmap = bitmap.copy(bitmap.getConfig(), false);
//					callBack.onBitmapReady(copyBitmap, UriUtil.isNetworkUri(uri) ? uri.toString() : uri.getPath());
//				} else if (callBack != null) {
//					callBack.onLoadFailed();
//				}
//				dataSource.close();
//			}
//
//			@Override
//			protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
//				if (callBack != null) {
//					callBack.onLoadFailed();
//				}
//				dataSource.close();
//			}
//		}, CallerThreadExecutor.getInstance());
//		return dataSource;
//	}

	public String getImageExtension(String uri) {
		String ext = uri;
		if (ext.contains("?")) {
			ext = ext.substring(0, ext.indexOf("?"));
		}
		if (ext.contains(".")) {
			ext = ext.substring(ext.lastIndexOf("."));
		}
		return ext;
	}

	public boolean isJpg(String uri) {
		String ext = getImageExtension(uri);
		return ".jpg".equals(ext) || ".jpeg".equals(ext);
	}

	public interface CallBack {
		void onDrawableReady(Drawable drawable, File cachedResource);

		void OnLoadFailed();
	}

	public interface BitmapCallback {
		void onBitmapReady(Bitmap bitmap, String url);

		void onLoadFailed();
	}

	public static abstract class ControllerCallback implements ControllerListener<ImageInfo> {

		public abstract void onFinalImageHasSet(String id, ImageInfo imageInfo, Animatable animatable);

		public abstract void onFailedLoad(String id, Throwable throwable);


		@Override
		public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
			onFinalImageHasSet(id, imageInfo, animatable);
		}

		@Override
		public void onFailure(String id, Throwable throwable) {
			onFailedLoad(id, throwable);
		}

		@Override
		public void onIntermediateImageFailed(String id, Throwable throwable) {

		}

		@Override
		public void onIntermediateImageSet(String id, ImageInfo imageInfo) {

		}

		@Override
		public void onRelease(String id) {

		}

		@Override
		public void onSubmit(String id, Object callerContext) {

		}
	}
}