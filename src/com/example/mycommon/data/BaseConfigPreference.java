package com.example.mycommon.data;

import android.content.Context;
import com.example.mycommon.utils.LocalPreference;

/**
 * 应用配置
 */
public class BaseConfigPreference extends LocalPreference {
	private static final String  NAME = "config_preference";

	/** 声音开关状态:1表示开，0表示关 **/
	protected static final String SOUND_STATE = "SOUND_STATE";

	public BaseConfigPreference(Context mContext) {
		super(NAME, mContext);
	}

	public int getSoundState() {
		return getInt(SOUND_STATE, 1);
	}

	public void setSoundState(int value) {
		setInt(SOUND_STATE, value);
	}
}
