package com.example.mycommon.ui;

import com.example.mycommon.AppDemo;
import com.example.mycommon.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public abstract class BaseActivity extends Activity {
	public Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		findViewById(R.id.back_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AppDemo.getInstance().getAppSound().playSound(3);
				finish();
			}
		});

		((TextView) findViewById(R.id.title)).setText(getTitleStr());
		
		initView();
		initListener();
	}

	public abstract void initListener();

	public abstract void initView();

	public abstract String getTitleStr();
	
	
	public <T> void gotoActivity(Context context , Class<T> className){
		Intent intent =new Intent(context,className);
		context.startActivity(intent);
	}
}
