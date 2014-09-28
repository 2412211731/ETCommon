package com.example.mycommon.utils;

import java.io.File;
import java.util.List;
import java.util.UUID;

import com.example.mycommon.BaseConfig;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.View.MeasureSpec;

public class UiTool {

	public static int dip2px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public static int px2dip(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
	

	public static String sign(long timestamp, String... params) {
		StringBuffer buf = new StringBuffer();
		for (String param : params) {
			buf.append(param);
		}
		buf.append(timestamp);
		buf.append("8iu*d7&i327^&%&)");
		return MD5.md5(buf.toString());
	}

	public static boolean isAction(final Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (topActivity.getPackageName().equals(context.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	public static File createFilePath() {
		return createFilePath(null);
	}

	public static File createFilePath(String fileName) {
		if (fileName == null) {
			fileName = UUID.randomUUID().toString();
		}
		File dir = new File(BaseConfig.DIR_PATH);
		if (!dir.exists()) {
			dir.getPath();
			dir.mkdir();
		}
		File f = new File(BaseConfig.DIR_PATH + fileName);
		return f;
	}

	public static int getFileSize() {
		File dir = new File(BaseConfig.DIR_PATH);
		if (dir.exists()) {
			return dir.list().length;
		} else {
			return 0;
		}
	}

	public static int clearFiles() {
		File dir = new File(BaseConfig.DIR_PATH);
		if (dir.exists()) {
			for (File f : dir.listFiles()) {
				f.delete();
			}
		}
		return 0;
	}

	public static String getToken(Context context) {
		return MD5.md5(context.getPackageName() + Secure.getString(context.getContentResolver(), Secure.ANDROID_ID));
	}

	public static Bitmap convertViewToBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}
}
