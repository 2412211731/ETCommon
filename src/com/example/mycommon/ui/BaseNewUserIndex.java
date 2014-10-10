package com.example.mycommon.ui;

import java.util.ArrayList;
import java.util.List;

import com.example.mycommon.R;
import com.example.mycommon.ui.adapter.MyPagerAdapter;
import com.example.mycommon.widget.CirclePageIndicator.CirclePageIndicator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;

/**
 * 在子类的onCreate中需要先调用 setContentView
 * @author Administrator
 *
 */
public abstract class BaseNewUserIndex extends Activity {
	
	private Context context;
	private ViewPager mViewPager;	
	private CirclePageIndicator mIndicator;
	private ArrayList<View> viewList = new ArrayList<View>();
	private int lastX;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewList = setDrawableList();
        initData();
        initListener();
    }    
    
    /**
     * 初始化View列表
     * @return
     */
    public abstract ArrayList<View> setDrawableList();
    
    /**
     * 最后一页继续往右滑动时候的行为
     */
    public abstract void onLastPageFinish();
    
    public abstract int getViewPagerId();
    public abstract int getCirclePageIndicatorId();
    
    private void initListener() {
    	mViewPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					lastX = (int)event.getX();
					break;
				case MotionEvent.ACTION_MOVE:
					if((lastX - event.getX()) >100 && (mViewPager.getCurrentItem() == viewList.size() -1)){
//						Toast.makeText(context, "已经是最后一页", Toast.LENGTH_SHORT).show();
						onLastPageFinish();
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

    public void setNoTitle(){
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    
	public void initData(){
		context = getBaseContext();
		
		MyPagerAdapter mAdapter = new MyPagerAdapter(context, viewList);
		mViewPager = (ViewPager)findViewById(getViewPagerId());
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setAdapter(mAdapter);

        mIndicator = (CirclePageIndicator)findViewById(getCirclePageIndicatorId());
        mIndicator.setViewPager(mViewPager);
        mIndicator.setStrokeColor(getResources().getColor(R.color.white));
		mIndicator.setFillColor(getResources().getColor(R.color.white));
	}
	
}
