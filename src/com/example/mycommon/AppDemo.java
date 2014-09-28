package com.example.mycommon;

public class AppDemo extends BaseAppWithSound {
	private static AppDemo self;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public static AppDemo getInstance() {
		if (null == self) {
			self = new AppDemo();
		}
		return self;
	}
}
