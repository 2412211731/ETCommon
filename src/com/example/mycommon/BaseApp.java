package com.example.mycommon;

import com.example.mycommon.bean.AppInfo;
import com.example.mycommon.utils.ImageLoaderUtil;
import com.example.mycommon.utils.UiTool;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class BaseApp extends Application {
	protected Context context;
	protected AppInfo appInfo;

	@Override
	public void onCreate() {
		context = getBaseContext();
		initAppInfo();
		//初始化图片加载模块
		ImageLoaderUtil.initImageLoader(this);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	/**
	 * 初始化AppInfo
	 */
	public void initAppInfo() {
		appInfo = new AppInfo();
		appInfo.setToken(UiTool.getToken(this));

		PackageInfo info;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
			appInfo.setVersionCode(info.versionCode);
			appInfo.setVersionName(info.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Context getContext() {
		return context;
	}

	public AppInfo getAppInfo() {
		return appInfo;
	}
}
