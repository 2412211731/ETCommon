package com.example.mycommon;

import android.os.Environment;

/**
 * 应用配置类
 */
public final class BaseConfig {
	//true表示开启debug模式，在正式发布版本时候请设置为false
//	public final static boolean isDebug = true;
	public final static String DIR_PATH = Environment.getExternalStorageDirectory().getPath() + "/mycommon/";
	/**图片缓存目录**/
	public final static String DIR_IMG_CACHE_PATH = DIR_PATH + "imgCache/";
}
