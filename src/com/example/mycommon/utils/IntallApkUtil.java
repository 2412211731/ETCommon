package com.example.mycommon.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.example.mycommon.R;
import com.example.mycommon.widget.CommonDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 判断是否安装了apk; 从asset安装apk;
 * 
 * @author Kaka
 * 
 */
public class IntallApkUtil {

	/**
	 * 检测服务是否安装
	 * 
	 * @return
	 */
	public static boolean detectMobile_sp(final Context context,
			final Handler mHandler, final String assetApkName,
			String apkPackageName) {
		boolean isMobile_spExist = isMobile_spExist(context, apkPackageName);
		if (!isMobile_spExist) {
			// 获取系统缓冲绝对路径 获取/data/data//cache目录
//			File cacheDir = context.getCacheDir();
//			final String cachePath = cacheDir.getAbsolutePath() + "/"
//					+ assetApkName;
			File cacheDir = Environment.getExternalStorageDirectory();
			final String cachePath = cacheDir.getAbsolutePath() + "/"
					+ assetApkName;

			File f = new File(cachePath);
			if(f.exists()){
				f.delete();
			}
			// 实例新线程检测是否有新版本进行下载
			new Thread(new Runnable() {
				public void run() {
					boolean copyOk = retrieveApkFromAssets(context,
							assetApkName, cachePath);
					if (copyOk) {
						Message msg = new Message();
						msg.what = 1;
						msg.obj = cachePath;
						mHandler.sendMessage(msg);
					}
				}
			}).start();

		}

		return isMobile_spExist;
	}

	/**
	 * 遍历程序列表，判断是否安装特定服务
	 * 
	 * @return
	 */
	public static boolean isMobile_spExist(Context context, String packageName) {
		PackageManager manager = context.getPackageManager();
		List<PackageInfo> pkgList = manager.getInstalledPackages(0);
		for (int i = 0; i < pkgList.size(); i++) {
			PackageInfo pI = pkgList.get(i);
			if (pI.packageName.equalsIgnoreCase(packageName))
				return true;
		}

		return false;
	}

	/**
	 * 安装assets文件夹下的apk
	 * 
	 * @param context
	 *            上下文环境
	 * @param fileName
	 *            apk名称
	 * @param path
	 *            安装路径
	 * @return
	 */
	public static boolean retrieveApkFromAssets(Context context,
			String fileName, String path) {
		boolean bRet = false;

		try {
			InputStream is = context.getAssets().open(fileName);

			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);

			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}

			fos.close();
			is.close();

			bRet = true;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return bRet;
	}

	/**
	 * 显示确认安装的提示
	 * 
	 * @param context
	 *            上下文环境
	 * @param cachePath
	 *            安装文件路径
	 */
	public static void showInstallConfirmDialog(final Context context,
			final String cachePath) {
		CommonDialog commonDialog = new CommonDialog(context,
				R.layout.common_dialog, R.style.MyDialog) {
			public void initListener() {
				TextView titleView = (TextView) findViewById(R.id.title);
				titleView.setText("提示");

				TextView comment = (TextView) findViewById(R.id.comment);
				comment.setText("您还没有安装本组件,是否从本地安装");

				Button okBtn = (Button) findViewById(R.id.ok);
				okBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						closeDialog();
						// 修改apk权限
						chmod("777", cachePath);
						// install the apk.
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.setDataAndType(Uri.parse("file://" + cachePath),
								"application/vnd.android.package-archive");
						context.startActivity(intent);
					}
				});

				Button cancelBtn = (Button) findViewById(R.id.cancel);
				cancelBtn.setText("取消");
				cancelBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						closeDialog();
					}
				});
			}
		};
		commonDialog.show();
	}

	/**
	 * 获取权限
	 * 
	 * @param permission
	 *            权限
	 * @param path
	 *            路径
	 */
	public static void chmod(String permission, String path) {
		try {
			String command = "chmod " + permission + " " + path;
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
