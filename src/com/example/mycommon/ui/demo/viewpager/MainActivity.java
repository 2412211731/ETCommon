package com.example.mycommon.ui.demo.viewpager;

import java.util.ArrayList;
import java.util.List;

import com.example.mycommon.R;
import com.example.mycommon.ui.adapter.MyPagerAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends Activity {
	Context context;
	RadioGroup footRadioGroup;

	private LocalActivityManager manager;
	private ViewPager viewPage;
	private List<View> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		context = getBaseContext();

		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);

		viewPage = (ViewPager) findViewById(R.id.viewPage);
		footRadioGroup = (RadioGroup) findViewById(R.id.footRadioGroup);
		footRadioGroup.setOnCheckedChangeListener(listener);

		View v1 = manager.startActivity("a1", new Intent(this, OneActivity.class))
				.getDecorView();
		View v2 = manager.startActivity("a2", new Intent(this, TwoActivity.class))
				.getDecorView();
		View v3 = manager.startActivity("a3", new Intent(this, ThreeActivity.class))
				.getDecorView();
		
		View v4 = manager.startActivity("a4", new Intent(this, FourActivity.class))
				.getDecorView();
		
		list = new ArrayList<View>();
		list.add(v1);
		list.add(v2);
		list.add(v3);
		list.add(v4);

		viewPage.setAdapter(new MyPagerAdapter(context, list));
		viewPage.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				footRadioGroup.check(footRadioGroup.getChildAt(arg0).getId());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	OnCheckedChangeListener listener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.noDeal:
				viewPage.setCurrentItem(0);
				break;
			case R.id.haveDeal:
				viewPage.setCurrentItem(1);
				break;
			case R.id.myinfo:
				viewPage.setCurrentItem(2);
				break;
			case R.id.sort:
				viewPage.setCurrentItem(3);
				break;
			}
		};
	};
}
