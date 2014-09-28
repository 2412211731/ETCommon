package com.example.mycommon.ui.service;

import com.example.mycommon.ui.reciever.SMSReceiver;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;

public class SMSService extends Service {
	private SMSObserver mObserver;
	private SMSReceiver receiver;

	@Override
	public void onCreate() {
		super.onCreate();
		receiver = new SMSReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(SMSReceiver.SMS_RECEIVED_ACTION);
		filter.setPriority(Integer.MAX_VALUE);
		registerReceiver(receiver, filter);

		// 在这里启动
		ContentResolver resolver = getContentResolver();
		mObserver = new SMSObserver(resolver, new SMSHandler(this));
		resolver.registerContentObserver(Uri.parse("content://sms"), true, mObserver);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		if (receiver != null) {
			unregisterReceiver(receiver);
		}

		this.getContentResolver().unregisterContentObserver(mObserver);
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
