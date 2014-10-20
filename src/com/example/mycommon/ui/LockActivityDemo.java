package com.example.mycommon.ui;

import com.example.mycommon.R;
import com.example.mycommon.widget.LockView;
import com.example.mycommon.widget.LockView.OnLockFinishListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

/**
 * 解锁demo
 * 
 * @author kaka
 * 
 */
public class LockActivityDemo extends Activity {
	private LockView mSudokoView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lock_activity_demo);
		mSudokoView = (LockView) findViewById(R.id.sudoko);
		mSudokoView.setOnLockFinishListener(new OnLockFinishListener() {
			@Override
			public void finish(StringBuilder lockString) {
				Toast.makeText(getApplicationContext(), lockString, 2000)
						.show();
				mSudokoView.showWrongIcons();
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						mSudokoView.reset();
					}
				}, 2000);
			}
		});
	}

}
