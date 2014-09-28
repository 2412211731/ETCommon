package com.example.mycommon.utils.event1;

import java.util.HashMap;
import java.util.Map;

import com.example.mycommon.AppDemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

/**
 * @ClassName: AppMessager
 * @Description: TODO 应用消息收发处理
 * @author Brook xu
 * @date 2014-04-16 下午2:54:57
 * @version 2.0
 * 
 *1 因广播数据在本应用范围内传播，你不用担心隐私数据泄露的问题。 
 *2 不用担心别的应用伪造广播，造成安全隐患。
 *3 相比在系统内发送全局广播，它更高效。
 */
public class AppMessager extends BroadcastReceiver {
	private LocalBroadcastManager mLocalBroadcastManager;
	public static final String ACTION_APP_DEFAULT_MSG = "action.app.broadcast.default.msg";
	private Map<String, Callback<Intent>> mCallbackMap;

	private static AppMessager mInstance;
	
	public static AppMessager getmInstance(Context mContext){
		if(mInstance == null){
			mInstance = new AppMessager(mContext);
		}
		
		return mInstance;
	}
	
	private AppMessager(Context mContext) {
		super();
		mLocalBroadcastManager = LocalBroadcastManager.getInstance(mContext);
		mCallbackMap = new HashMap<String, Callback<Intent>>();
		// 添加默认的广播，do nothing
		// addRegister(ACTION_APP_DEFAULT_MSG, null);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		dealMsg(intent.getAction(), intent);
	}

	/**
	 * 消息处理
	 * 
	 * @param msgType
	 * @param param
	 * @return void
	 */
	private synchronized void dealMsg(String action, Intent intent) {
		// TODO Auto-generated method stub
		Callback<Intent> callback = mCallbackMap.get(action);
		if (callback != null) {
			callback.onStart();
			callback.onSuccess(intent);
			callback.onFinish();
		}
	}

	/**
	 * 发送广播消息
	 * 
	 * @param type
	 * @param param
	 * @return void
	 */
	public static synchronized void sendBroadcast(Context context, Intent intent) {
		getmInstance(context).mLocalBroadcastManager.sendBroadcast(intent);
	}

	/**
	 * 在使用类中注册消息处理监听
	 * 
	 * @param msgType
	 * @param msgCallBack
	 * @return void
	 */
	public synchronized void addRegister(String action, Callback<Intent> msgCallBack) {
		if (TextUtils.isEmpty(action)) {
			action = ACTION_APP_DEFAULT_MSG;
		}

		mCallbackMap.put(action, msgCallBack);
		mLocalBroadcastManager.registerReceiver(this, new IntentFilter(action));
	}

	/**
	 * to unregister this BroadcastReceiver
	 * 
	 * @throws Exception
	 * @return void
	 */
	public void unRegisterSelf() {
		try {
			mLocalBroadcastManager.unregisterReceiver(this); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 模拟使用方法
	 * 
	 * @param args
	 * @return void
	 */
	private static void main(String[] args) {
		// 创建实例
		AppMessager mAppMessager = AppMessager.getmInstance(AppDemo.getInstance());
		
		//消息处理callback
		Callback<Intent> mCallback = new Callback<Intent>() {
			@Override
			public void onSuccess(Intent param) {
				// TODO Auto-generated method stub
				// do something
				System.out.println(param);
			}
		};

		// 添加消息处理
		mAppMessager.addRegister("action1", mCallback);
		mAppMessager.addRegister("action2", mCallback);

		// 发送消息
		AppMessager.sendBroadcast(AppDemo.getInstance().getApplicationContext(), new Intent(ACTION_APP_DEFAULT_MSG));

		//可在onDestory中调用 unRegister
		if (mAppMessager != null) {
			mAppMessager.unRegisterSelf();
			mAppMessager = null;
		}
	}
}
