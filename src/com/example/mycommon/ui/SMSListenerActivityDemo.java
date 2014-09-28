package com.example.mycommon.ui;

import com.example.mycommon.R;
import com.example.mycommon.ui.service.SMSHandler;
import com.example.mycommon.ui.service.SMSInfo;
import com.example.mycommon.ui.service.SMSService;
import com.example.mycommon.utils.event.EventBus;
import com.example.mycommon.utils.event.EventObserver;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 短信监听demo
 * 
 * @author kaka
 * 
 */
public class SMSListenerActivityDemo extends Activity {

	private EditText vcodeEditText;
	private Button openSmsListenerBtn;
	private EventObserver eventObserver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_demo);

		initView();
		initListener();

		// 获取验证码
		eventObserver = new EventObserver() {

			@Override
			public void update(Object data) {
				SMSInfo info = (SMSInfo) data;
				String msg = info.smsBody;
				vcodeEditText.setText(msg);
			}
		};
		EventBus.getInstance().addObserver(SMSHandler.GET_MSG, eventObserver);

	}

	private void initListener() {
		openSmsListenerBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startService(new Intent(SMSListenerActivityDemo.this,
						SMSService.class));
				openSmsListenerBtn.setText("已开启监听，请发送一条短信来");
			}
		});
	}

	private void initView() {
		vcodeEditText = (EditText) findViewById(R.id.editText);
		openSmsListenerBtn = (Button) findViewById(R.id.openSmsListenerBtn);
	}

	@Override
	protected void onDestroy() {
		stopService(new Intent(this, SMSService.class));
		if (eventObserver != null) {
			EventBus.getInstance().delObserver(eventObserver);
		}
		super.onDestroy();
	}

}
