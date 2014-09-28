package com.example.mycommon.ui;

import java.io.FileNotFoundException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * 图片裁剪
 */
public abstract class BasePicCutActivity extends Activity {
	private final String TAG = "BasePicCutActivity";
	// 设置图片宽高
	private final int DEFAULT_WIDTH = 480;
	private final int DEFAULT_HEIGHT = 480;
	private int width = DEFAULT_WIDTH;
	private int height = DEFAULT_HEIGHT;
	//
	private static final int TAKE_PICTURE = 1;
	private static final int CROP_PICTURE = 2;
	private static final int CHOOSE_PICTURE = 3;
	// 保存图片地址
	private static final String SAVE_IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";
	private Uri saveImageUri;
	// 当前设置图片的imageView
	protected ImageView settingImageView;
	// 可选择图片的imageView列表
	private int[] imageViewIds;
	private boolean isCropToCirclePhoto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		saveImageUri = Uri.parse(SAVE_IMAGE_FILE_LOCATION);
		if (setCropImageWidth() > 0) {
			width = setCropImageWidth();
		}
		if (setCropImageHeight() > 0) {
			height = setCropImageHeight();
		}
		// 初始化要选择图片的imageView控件
		imageViewIds = getImageViewId();
		for (int i = 0; i < imageViewIds.length; i++) {
			final ImageView img = (ImageView) findViewById(imageViewIds[i]);
			img.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					settingImageView = img;
					ShowPickDialog("选择图片");
				}
			});
		}
		// 图片是否裁剪为圆形
		isCropToCirclePhoto = isCropToCirclePhoto();
	}

	/** 设置裁剪后图片宽度 **/
	public abstract int setCropImageWidth();

	/** 设置裁剪后图片高度 **/
	public abstract int setCropImageHeight();

	/** 获取需要设置图片的imageView的id **/
	public abstract int[] getImageViewId();

	/**
	 * 是否裁剪为圆形
	 **/
	public abstract boolean isCropToCirclePhoto();

	/**
	 * 上传图片
	 * 
	 * @param settingImageView
	 *            当前设置的imageView
	 * @param drawable
	 *            imageView显示的图片数据
	 */
	public abstract void uploadImage(Drawable drawable);

	/**
	 * 选择提示对话框
	 */
	private void ShowPickDialog(String title) {
		new AlertDialog.Builder(this).setTitle(title).setNegativeButton("相册", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
				intent.setType("image/*");
				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 1);
				intent.putExtra("aspectY", 1);
				// intent.putExtra("outputX", width);
				// intent.putExtra("outputY", height);
				intent.putExtra("scale", true);
				intent.putExtra("return-data", false);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, saveImageUri);
				intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
				intent.putExtra("noFaceDetection", false);
				startActivityForResult(intent, CROP_PICTURE);
			}
		}).setPositiveButton("拍照", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, saveImageUri);
				startActivityForResult(intent, TAKE_PICTURE);
			}
		}).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			Log.e(TAG, "requestCode = " + requestCode);
			Log.e(TAG, "resultCode = " + resultCode);
			Log.e(TAG, "data = " + data);
			return;
		} else {
			switch (requestCode) {
			case TAKE_PICTURE:
				Log.d(TAG, "TAKE_BIG_PICTURE: data = " + data);
				cropImageUri(saveImageUri, CROP_PICTURE);
				break;
			case CROP_PICTURE:
				Log.d(TAG, "CROP_BIG_PICTURE: data = " + data);
				if (saveImageUri != null) {
					Bitmap bitmap = decodeUriAsBitmap(saveImageUri);
					if (isCropToCirclePhoto) {
						bitmap = toRoundBitmap(bitmap);
					}
					bitmap = zoomBitmap(bitmap, width, height);

					Drawable drawable = new BitmapDrawable(bitmap);
					settingImageView.setImageDrawable(drawable);
					uploadImage(drawable);
				}
				break;
			default:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 对图片进行缩放
	 * 
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix(); // 创建操作图片用的 Matrix 对象
		float scaleWidth = ((float) w / width); // 计算缩放比例
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidth, scaleHeight); // 设置缩放比例
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true); // 建立新的
		return newbmp; // 把 bitmap 转换成 drawable 并返回
	}

	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	private void cropImageUri(Uri uri, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// intent.putExtra("outputX", outputX);//这里如果设置了值，那么会造成如果图片很小，这里的值大于了裁剪后图片宽，则会把多余部分生成为黑色，不好看
		// intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, requestCode);
	}

	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
}