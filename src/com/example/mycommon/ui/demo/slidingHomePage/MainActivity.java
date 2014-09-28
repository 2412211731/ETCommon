package com.example.mycommon.ui.demo.slidingHomePage;

import com.example.mycommon.R;
import com.example.mycommon.utils.UiTool;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import android.os.Bundle;
import android.content.Context;
import android.view.View;

public class MainActivity extends SlidingFragmentActivity {

	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;

		initMenu();
	}

	// 初始化菜单界面
	private void initMenu() {
		final SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.RIGHT);
		sm.setShadowWidth(UiTool.dip2px(this, 10));// 阴影宽度
		// sm.setShadowDrawable(R.drawable.shadow);// 阴影Drawable
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		// sm.setSecondaryMenu(R.layout.menu_frame);
		setBehindContentView(R.layout.menu_frame);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuFragment()).commit();

		// 切换菜单
		findViewById(R.id.menu_btn).setOnClickListener(
				new View.OnClickListener() {
					public void onClick(View v) {
						toggle();
					}
				});
	}
}
