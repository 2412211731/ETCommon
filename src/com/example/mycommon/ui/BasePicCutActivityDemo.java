package com.example.mycommon.ui;

import java.util.HashMap;
import com.example.mycommon.R;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * 图片裁剪
 */
public class BasePicCutActivityDemo extends BasePicCutActivity {
	// 需要上传的图片
	private HashMap<String, Drawable> uploadPictures = new HashMap<String, Drawable>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.base_pic_cut_demo);
		super.onCreate(savedInstanceState);
	}

	@Override
	public int[] getImageViewId() {
		int[] imageViews = new int[] { R.id.headerImageView ,R.id.headerImageView2};
		return imageViews;
	}

	@Override
	public boolean isCropToCirclePhoto() {
		return true;
	}

	@Override
	public int setCropImageWidth() {
		return 480;
	}

	@Override
	public int setCropImageHeight() {
		return 480;
	}

	@Override
	public void uploadImage(Drawable drawable) {
		String id = settingImageView.getId() + "";
		if (uploadPictures.containsKey(id)) {
			uploadPictures.remove(id);
		}
		uploadPictures.put(id, drawable);
		System.out.println("uploadPictures size:" + uploadPictures.size());
	}
}