package com.example.mycommon.widget;

import com.example.mycommon.R;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public class CommonPopupWindowUtil {
	/**
	 * 
	 * @param activity
	 * @param layoutXml
	 * @param width
	 * @param height
	 *            可以是 LayoutParams.WRAP_CONTENT 也可以是具体高度
	 * @return
	 */
	public static PopupWindow iniPopupWindow(Activity activity, int layoutXml, int width, int height) {
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(layoutXml, null);

		PopupWindow pwMyPopWindow = new PopupWindow(layout);
		pwMyPopWindow.setFocusable(true);// 加上这个popupwindow中的ListView才可以接收点击事件

		// 控制popupwindow的宽度和高度自适应
		// lvPopupList.measure(View.MeasureSpec.UNSPECIFIED,
		// View.MeasureSpec.UNSPECIFIED);
		pwMyPopWindow.setWidth(width);
		pwMyPopWindow.setHeight(height);

		// 控制popupwindow点击屏幕其他地方消失
		pwMyPopWindow.setBackgroundDrawable(activity.getResources()
				.getDrawable(R.color.transparent));// 设置背景图片，不能在布局中设置，要通过代码来设置
		pwMyPopWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上

		return pwMyPopWindow;
	}
	
	public void show(Activity context){
//		PopupWindow popupWindow;
//		if (popupWindow == null) {
//			popupWindow = CommonPopupWindowUtil.iniPopupWindow(context,
//					R.layout.black_activity_popup_window, LayoutParams.MATCH_PARENT,
//					DensityUtils.dipTopx(context, 60));
//			popupWindow.setOutsideTouchable(false);
//		}
//		int[] location = new int[2];
//		findViewById(R.id.bottomLayout).getLocationOnScreen(location);
//		popupWindow.showAtLocation(context.findViewById(R.id.bottomLayout), Gravity.NO_GRAVITY,
//				location[0], location[1] - popupWindow.getHeight());
	}
}
