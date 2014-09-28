package com.example.mycommon.ui.service;

import com.example.mycommon.utils.event.EventBus;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

/**
 * @author 短信的处理
 * 
 */
public class SMSHandler extends Handler {
	private Context mcontext;
    public static final String GET_MSG = "GET_MSG";
	public SMSHandler(Context context) {
		this.mcontext = context;
	}

	@Override
	public void handleMessage(Message msg) {
		SMSInfo smsInfo = (SMSInfo) msg.obj;

		if (smsInfo.action == 1) {
			ContentValues values = new ContentValues();
			values.put("read", "1");
			mcontext.getContentResolver().update(Uri.parse("content://sms/inbox"), values, "thread_id=?",
					new String[] { smsInfo.thread_id });
		} else if (smsInfo.action == 2) {
			Uri mUri = Uri.parse("content://sms/");
			mcontext.getContentResolver().delete(mUri, "_id=?", new String[] { smsInfo._id });
		}
		
		//广播短信
		EventBus.getInstance().notifyObservers(GET_MSG, smsInfo);
		
	}
}