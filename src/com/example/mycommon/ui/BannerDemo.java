package com.example.mycommon.ui;

import org.json.JSONObject;
import com.example.mycommon.R;
import com.example.mycommon.widget.BannerTool;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class BannerDemo extends Activity {

	private Adget banner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.banner_demo);
		super.onCreate(savedInstanceState);
		banner = new Adget(this, (ViewGroup) findViewById(R.id.scrollview),
				(ViewFlipper) findViewById(R.id.f1), null);
		banner.loadBannerData();
	}

	@Override
	protected void onStart() {
		if (banner != null) {
			banner.startTimer();
		}
		super.onStart();
	}

	@Override
	protected void onStop() {
		if (banner != null) {
			banner.stopTimer();
		}
		super.onStop();
	}

	class Adget extends BannerTool {
		public Adget(Context context, ViewGroup scrollview, ViewFlipper f1,
				TextView circles) {
			super(context, scrollview, f1, circles);
		}

		@Override
		public void loadBannerData() {

			adSize = 2;

//			if (adSize == 0) {
//				// 添加一副默认图片
//				View view = inflater.inflate(R.layout.banner_item, null);
//				ImageView imageView = (ImageView) view.findViewById(R.id.image);
//				imageView.setImageBitmap(BitmapFactory.decodeResource(
//						context.getResources(), R.drawable.ic_launcher));
//				flipper.addView(view, new LayoutParams(
//						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//				adSize++;
//			}

			for (int i = 0; i < adSize; i++) {
				JSONObject row = new JSONObject();
				try {
					row.put("pic",
							"http://img2.imgtn.bdimg.com/it/u=701972200,1713908872&fm=21&gp=0.jpg");
					flipper.addView(markSuitView(row), new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT));
					startSwitchAd = true;
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			if (flipper.getChildCount() <= 1) {
				startSwitchAd = false;
			}

			int yu = adIndex % adSize;
			adIndex = adIndex - yu;
			yu = adIndex % adSize; // 当前应该显示的策略索引

			// 设置初始显示策略
			flipper.setDisplayedChild(yu);
			if (circlesTextView != null) {
				circlesTextView.setText(Html.fromHtml(getStr(yu)));
			}
			flipper.setOnTouchListener(new AdSwitchListener());

			// 隐藏加载提示
			((Activity) context).findViewById(R.id.fad)
					.setVisibility(View.GONE);
		}

	}

}
