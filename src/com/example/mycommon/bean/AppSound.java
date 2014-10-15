package com.example.mycommon.bean;

import com.example.mycommon.R;
import com.example.mycommon.utils.LocalPreference;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class AppSound {
	private String APP_SOUND_ENABLE= "app_sound_enable";
	
	private static int soundEnable; // 是否开启按键声音
	private static SoundPool soundPool;
	private static int[] soundIds;

	public AppSound(Context context) {
		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		soundIds = new int[] { //
		soundPool.load(context, R.raw.sfx_taxi_popup, 1), // 0
				soundPool.load(context, R.raw.sfx_slide_up, 1), // 1
				soundPool.load(context, R.raw.sfx_slide_down, 1), // 2
				soundPool.load(context, R.raw.sfx_click, 1), // 3
				soundPool.load(context, R.raw.sfx_error, 1), // 4
				soundPool.load(context, R.raw.sfx_success, 1),// 5
				soundPool.load(context, R.raw.cartoon, 1),// 6
		};
		soundEnable = LocalPreference.getPreferences(context).getInt(APP_SOUND_ENABLE, 1);
	}

	public void playSound(int index) {
		if (soundEnable == 1) {
			soundPool.play(soundIds[index], 1, 1, 0, 0, 1);
		}
	}

	/**
	 * 设置声音
	 * 
	 * @param state
	 *            1表示打开，0表示关闭声音
	 */
	public void setSoundState(Context context, int state) {
		soundEnable = state;
		LocalPreference.getPreferences(context).setInt(APP_SOUND_ENABLE,state);
	}
}
