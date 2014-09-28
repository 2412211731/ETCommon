package com.example.mycommon.ui.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

	public static final String TAG = "SMSReceiver:";
	public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(SMS_RECEIVED_ACTION)) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] objArray = (Object[]) bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[objArray.length];
				for (int i = 0; i < objArray.length; i++) {
					messages[i] = SmsMessage.createFromPdu((byte[]) objArray[i]);
				}

				String phoneNum = "";// 电话号码
				StringBuilder sb = new StringBuilder();// 短信内容

				for (SmsMessage currentMessage : messages) {
					phoneNum = currentMessage.getDisplayOriginatingAddress();
					sb.append(currentMessage.getDisplayMessageBody());
				}
				// 根据你策略的手机端口号/拦截关键字判断进行是否中断广播(this.abortBroadcast();),
				// TODO
			}
		}

	}
}