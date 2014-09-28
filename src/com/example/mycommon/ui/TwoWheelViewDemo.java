package com.example.mycommon.ui;

import com.example.mycommon.R;
import com.example.mycommon.widget.wheel.Callback;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class TwoWheelViewDemo extends Activity {
    private TextView chooseValueTxt;
    String[] hours;
    String[] minites;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wheel_pupop_two_activity_demo);
		
		initHours();
		initMinites();
		
		chooseValueTxt = (TextView) findViewById(R.id.chooseValue);
		findViewById(R.id.showWheelView).setOnClickListener(new TwoWheelSelectListenter(this,findViewById(android.R.id.content) ,chooseValueTxt,"请选择上课时间","取消","确定", hours,minites, new Callback<String>() {
			@Override
			public void handle(String param) {
				chooseValueTxt.setText(param);
			}
		}));
		
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void initHours(){
		hours = new String[]{"8点","9点","10点","11点","12点","13点","14点","15点","16点","17点","18点","19点","20点","21点"};
	}
	
	public void initMinites(){
		minites = new String[60];
		for(int i=0;i<60;i++){
			minites[i]=i+"分";
		}
	}

}
