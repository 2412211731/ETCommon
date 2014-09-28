package com.example.mycommon.widget;

import java.util.Map;
import java.util.Map.Entry;
import com.example.mycommon.R;
import com.example.mycommon.utils.UiTool;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class RadioGroupDialog extends CommonDialog {

	private RadioGroup radioGroup;
	private TextView titleTextView;

	public RadioGroupDialog(Context context, String title, Map<Long,String> map,Long defaultValue,final Handler handler) {
		super(context, R.layout.widget_radio_dialog, R.style.MyDialog);
		
		setCanceledOnTouchOutside(true);
		setCancelable(true);

		radioGroup = (RadioGroup) findViewById(R.id.dialog_radiogroup);
		titleTextView = (TextView) findViewById(R.id.dialog_title);
		titleTextView.setText(title);

		for (Entry<Long,String> entry:map.entrySet()) {
				RadioButton radioButton = new RadioButton(context);
				
				if(defaultValue!=null){
					if(entry.getKey().equals(defaultValue)){
						radioButton.setChecked(true);
					}
				}
				
				radioButton.setTextSize(18);
				radioButton.setTextColor(0xFF666666);
				radioButton.setText("  "+entry.getValue());
				radioButton.setTag(entry);

				radioButton.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.switch_tip), null);
				radioButton.setButtonDrawable(R.drawable.widget_block);
				radioButton.setBackgroundResource(R.drawable.line);
				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, UiTool.dip2px(context, 50));
				radioGroup.addView(radioButton, lp);
		}
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				View view=group.findViewById(checkedId);
				Message message=new Message();
				message.obj=view.getTag();
				handler.sendMessage(message);
				RadioGroupDialog.this.closeDialog();
			}
		});
		
	}

	@Override
	public void initListener() {
	}

}
