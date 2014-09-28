package com.example.mycommon.widget;

import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

import com.example.mycommon.R;
import com.example.mycommon.utils.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * 在activity的onCreate中调用 banner = new AppsMainActivityBanner(context,
 * (ViewGroup) findViewById(R.id.scrollview), flipper, (TextView)
 * findViewById(R.id.circles)); banner.loadBannerData();//开始加载数据
 * 
 * 在onStart()中开始轮播： banner.startTimer();
 * 
 * 在onStop()中关闭轮播： banner.stopTimer();
 * 
 * @author kaka
 * 
 */
public abstract class BannerTool {
	protected Context context;
	protected boolean startSwitchAd = false;

	protected ViewGroup parentViewGroup;
	protected ViewFlipper flipper;
	protected TextView circlesTextView;
	protected Timer timer;
	protected int adSize;
	protected int adIndex = Integer.MAX_VALUE / 2;
	protected LayoutInflater inflater;

	/**
	 * 
	 * @param context
	 * @param scrollview 可以设置为null,当外部嵌套了ScrollView的时候，需要把ScrollView传递进来，才能解决广告滑动和ScrollView滑动冲突问题
	 * @param f1 
	 * @param circles 显示图片数量的圆圈,为空时候则不显示；
	 */
	public BannerTool(Context context, ViewGroup parentViewGroup, ViewFlipper f1,
			TextView circles) {
		this.context = context;
		this.flipper = f1;
		this.circlesTextView = circles;
		this.parentViewGroup = parentViewGroup;

		inflater = LayoutInflater.from(context);
	}

	/**
	 * 开始轮播广告
	 */
	public void startTimer() {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			private Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					try {
						if (startSwitchAd) {
							switchAd(true);
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			};

			@Override
			public void run() {
				handler.sendMessage(new Message());
			}
		}, 5000, 5000);
	}

	// 停止图片轮播
	public void stopTimer() {
		if (timer != null)
			timer.cancel();
		timer = null;
	}

	public class AdSwitchListener implements OnTouchListener {

		private float downX;
		private float downY;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			try {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					parentViewGroup.requestDisallowInterceptTouchEvent(true);
					downX = event.getX();
					downY = event.getY();
					break;
				}
				case MotionEvent.ACTION_MOVE:
					float lastX1 = event.getX();
					float lastY = event.getY();
					double angle = Math.atan2(Math.abs(lastY - downY),
							Math.abs(lastX1 - downX));
					if ((180 * angle) / Math.PI > 60) {
						parentViewGroup.requestDisallowInterceptTouchEvent(false);
					}
					break;
				case MotionEvent.ACTION_UP: {
					float lastX = event.getX();

					if (lastX - downX > 100) {
						switchAd(false);
					} else if (downX - lastX > 100) {
						switchAd(true);
					} else {
						JSONObject row = (JSONObject) flipper.getCurrentView()
								.getTag();
						try {
							// Intent intent = new Intent(context,
							// NewsDetailActivity.class);
							// intent.putExtra("id", row.getString("id"));
							// intent.putExtra("type", "0");
							// intent.putExtra("url", row.getString("url"));
							// if (!StringUtil.isBlank(row.getString("url"))) {
							// context.startActivity(intent);
							// }
						} catch (Throwable e) {
							e.printStackTrace();
						}

					}

					break;
				}
				case MotionEvent.ACTION_CANCEL:
					parentViewGroup.requestDisallowInterceptTouchEvent(false);
					break;
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return true;
		}
	}

	private void switchAd(boolean isAdd) throws Exception {
		if (isAdd) {
			flipper.setInAnimation(AnimationUtils.loadAnimation(context,
					R.anim.right_in));
			flipper.setOutAnimation(AnimationUtils.loadAnimation(context,
					R.anim.left_out));
			int yu = (++adIndex) % adSize;
			flipper.setDisplayedChild(yu);
			if (circlesTextView != null) {
				circlesTextView.setText(Html.fromHtml(getStr(yu)));
			}
		} else {
			flipper.setInAnimation(AnimationUtils.loadAnimation(context,
					R.anim.left_in));
			flipper.setOutAnimation(AnimationUtils.loadAnimation(context,
					R.anim.right_out));
			int yu = (--adIndex) % adSize;
			flipper.setDisplayedChild(yu);
			if (circlesTextView != null) {
				circlesTextView.setText(Html.fromHtml(getStr(yu)));
			}
		}
	}

	public abstract void loadBannerData();

	public String getStr(int showIndex) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < adSize; i++) {
			if (i == showIndex) {
				str.append("<font color='#0bcba4'>●</font>");
			} else {
				str.append("<font color='#c9c9c9'>●</font>");
			}
		}

		return str.toString();
	}

	// 创建套餐View
	public View markSuitView(final JSONObject row) throws Exception {
		View view = inflater.inflate(R.layout.banner_item, null);

		final ImageView imageView = (ImageView) view.findViewById(R.id.image);
		ImageLoader.getInstance().displayImage(row.getString("pic"), imageView,
				ImageLoaderUtil.getNormalOption());

		view.setTag(row);
		return view;
	}
}
