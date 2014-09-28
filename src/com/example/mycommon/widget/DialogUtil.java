package com.example.mycommon.widget;

import com.example.mycommon.R;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogUtil {

	public static CommonDialog alert(final int icon, final Context context,final String title, final String text,final Handler handler) {
		CommonDialog commonDialog = new CommonDialog(context, R.layout.common_alert, R.style.MyDialog) {
			public void initListener() {
				TextView titleView = (TextView) findViewById(R.id.title);
				titleView.setText(title);
				
				ImageView iconImageView=(ImageView)findViewById(R.id.dialog_icon);
				iconImageView.setImageResource(icon);
				
				TextView comment = (TextView) findViewById(R.id.comment);
				comment.setText(text);
				
				Button okBtn = (Button) findViewById(R.id.ok);
				okBtn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						closeDialog();
						if(handler!=null)
							handler.sendMessage(new Message());
					}
				});
			}
		};
		commonDialog.show();
		return commonDialog;
	}
	
	public static CommonDialog alert(final int icon,final Context context,final String title, final String text){
		return alert(icon,context,title,text,null);
	}
	
	public static CommonDialog waiting(final Context context) {
		CommonDialog commonDialog = new CommonDialog(context, R.layout.common_waiting, R.style.MyDialog) {
			private ImageView imageView;
			
			public void initListener() {
				 imageView=(ImageView)findViewById(R.id.waiting);
				Animation animation=AnimationUtils.loadAnimation(context,R.anim.wait);
				animation.setInterpolator( new LinearInterpolator());
				imageView.startAnimation(animation);
			}

			@Override
			public void closeListener() {
				imageView.clearAnimation();
			}
		};
		commonDialog.show();
		return commonDialog;
	}
	
	public static CommonDialog waitingNew(final Context context) {
		CommonDialog commonDialog = new CommonDialog(context, R.layout.common_waiting, R.style.MyDialog) {
			private ImageView imageView;
			
			public void initListener() {
				 imageView=(ImageView)findViewById(R.id.waiting);
				Animation animation=AnimationUtils.loadAnimation(context,R.anim.wait);
				animation.setInterpolator( new LinearInterpolator());
				imageView.startAnimation(animation);
			}

			@Override
			public void closeListener() {
				imageView.clearAnimation();
			}
		};
		return commonDialog;
	}
	

}
