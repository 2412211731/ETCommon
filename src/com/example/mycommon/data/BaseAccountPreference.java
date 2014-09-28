package com.example.mycommon.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.example.mycommon.utils.LocalPreference;

/**
 * 账户信息
 */
public class BaseAccountPreference extends LocalPreference {
	private static final String NAME = "account_preference";
	
	public BaseAccountPreference(Context mContext) {
		super(NAME, mContext);
	}

	public void goLogin(Context ctx,Class loginClass) {
		goLogin(ctx, null,loginClass);
	}

	public void goLogin(Context ctx, Intent refererIntent,Class loginClass) {
		Intent intent = new Intent(ctx, loginClass);
		if (!(ctx instanceof Activity)) {
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		if (refererIntent != null) {
			intent.putExtra("referer", refererIntent);
		}
		ctx.startActivity(intent);
	}
}
