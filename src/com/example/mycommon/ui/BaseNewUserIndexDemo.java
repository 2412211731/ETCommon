package com.example.mycommon.ui;

import java.util.ArrayList;

import com.example.mycommon.R;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

public class BaseNewUserIndexDemo extends BaseNewUserIndex {
	private int[] drawableList = new int[]{R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setNoTitle();
        setContentView(R.layout.new_user_index_activity);
        super.onCreate(savedInstanceState);
    }

	@Override
	public ArrayList<View> setDrawableList() {
		ArrayList<View> list = new ArrayList<View>();
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		for(int i:drawableList){
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(layoutParams);
			imageView.setImageResource(i);
			list.add(imageView);
		}
		return list;
	}

	@Override
	public void onLastPageFinish() {
		finish();
	}

	@Override
	public int getViewPagerId() {
		return R.id.new_user_index_viewPager;
	}

	@Override
	public int getCirclePageIndicatorId() {
		return R.id.new_user_index_circlePageIndicator;
	}

	
}
