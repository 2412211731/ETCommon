package com.example.mycommon.ui.demo.slidingHomePage;

import com.example.mycommon.R;
import com.example.mycommon.widget.CommonDialog;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuFragment extends Fragment {
    private Activity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		container.findViewById(R.id.update_btn).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				getNewestVersionInfo();
			}
		});

		container.findViewById(R.id.help_btn).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				Intent intent = new Intent(getActivity(), HelpActivity.class);
//				startActivity(intent);
			}
		});

		container.findViewById(R.id.about_btn).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				Intent intent = new Intent(getActivity(), AboutActivity.class);
//				startActivity(intent);
			}
		});

		container.findViewById(R.id.reg_btn).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				Intent intent = new Intent(getActivity(), AuthActivity.class);
//				String phone= PreferenceManager.getDefaultSharedPreferences(activity).getString("STUDENT_MOBILE", "");
//				intent.putExtra("phone", phone);
//				startActivity(intent);
			}
		});

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	
	
	
	private void getNewestVersionInfo() {

	}
	
	
	
	public void showUpdateDialog(Context context,final String title, final String text,final boolean forceUpdate,final String url) {
		CommonDialog commonDialog = new CommonDialog(context, R.layout.common_dialog, R.style.MyDialog) {
			public void initListener() {
				TextView titleView = (TextView) findViewById(R.id.title);
				titleView.setText(title);
				
				TextView comment = (TextView) findViewById(R.id.comment);
				comment.setText(text);
				
				Button okBtn = (Button) findViewById(R.id.ok);
				okBtn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						closeDialog();
						 Uri uri = Uri.parse(url); 
						Intent intent = new Intent(Intent.ACTION_VIEW,uri);    
				        startActivity(intent);  
					}
				});
				
				if(forceUpdate){
					Button cancelBtn = (Button) findViewById(R.id.cancel);
					cancelBtn.setVisibility(View.GONE);
				}else{
					Button cancelBtn = (Button) findViewById(R.id.cancel);
					cancelBtn.setText("取消");
					cancelBtn.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							closeDialog();
							Bundle bundle=new Bundle();
							bundle.putBoolean("result", false);
							Message message=new Message();
							message.setData(bundle);
						}
					});
				}
			}
		};
		commonDialog.show();
	}
}
