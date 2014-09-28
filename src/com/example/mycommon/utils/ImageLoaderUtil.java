package com.example.mycommon.utils;

import java.io.File;
import android.content.Context;
import android.graphics.Bitmap;
import com.example.mycommon.BaseConfig;
import com.example.mycommon.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * 图片加载类 1.在application中调用initImageLoader()初始化图片加载模块
 * 2.在需要显示图片的地方调用ImageLoader.getInstance().displayImage()方法显示图片
 * 
 * (注:配置和使用可参考 http://blog.csdn.net/vipzjyno1/article/details/23206387 )
 */
public class ImageLoaderUtil {
	private static DisplayImageOptions detailOption;
	private static DisplayImageOptions normalOption;

	/**
	 * 初始化图片加载模块 (注：此方法最好先application类中调用，方便应用其他地方使用)
	 * 
	 * @param context
	 * 
	 */
	public static void initImageLoader(Context context) {
		// 自定义缓存路径
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				BaseConfig.DIR_IMG_CACHE_PATH);

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				// 自定义缓存路径
				.diskCache(new UnlimitedDiscCache(cacheDir))
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// .writeDebugLogs() // Remove for release app
				.build();

		ImageLoader.getInstance().init(config);
	}

	public static DisplayImageOptions getDetailOption() {
		if (detailOption == null) {
			detailOption = new DisplayImageOptions.Builder()
					.cacheInMemory(true).cacheOnDisk(true)
					.considerExifParams(true)
					.imageScaleType(ImageScaleType.NONE).build();
		}
		return detailOption;
	}

	public static DisplayImageOptions getNormalOption() {
		if (normalOption == null) {
			normalOption = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.empty_photo)
					.showImageForEmptyUri(R.drawable.empty_photo)
					.showImageOnFail(R.drawable.empty_photo)
					.cacheInMemory(true).cacheOnDisk(true)
					.considerExifParams(true)
					.bitmapConfig(Bitmap.Config.RGB_565).build();
		}
		return normalOption;
	}
}
