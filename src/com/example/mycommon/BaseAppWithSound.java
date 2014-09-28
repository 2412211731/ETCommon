package com.example.mycommon;

import com.example.mycommon.bean.AppSound;

public class BaseAppWithSound extends BaseApp {
	protected AppSound appSound;

	@Override
	public void onCreate() {
		super.onCreate();
		appSound = new AppSound(context);
	}

	public AppSound getAppSound() {
		return appSound;
	}

}
