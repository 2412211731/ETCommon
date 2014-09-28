package com.example.mycommon.utils.event;

import android.util.Log;

public class ELog {
	final String TAG = "forum_log";
	static final boolean isDev = true;

	public static void d(String TAG, String msg) {
		if (isDev) {
			Log.d(TAG, ">>" + TAG + ":" + msg);
		}
	}

	public static void dAlways(String TAG, String msg) {
		Log.d(TAG, ">>" + TAG + ":" + msg);
	}

	public static void e(String TAG, String msg) {
		Log.e(TAG, ">>" + TAG + ":" + msg);
	}

	public static void e(String TAG, String msg, Throwable tr) {
		Log.e(TAG, ">>" + TAG + ":" + msg, tr);
	}
}
