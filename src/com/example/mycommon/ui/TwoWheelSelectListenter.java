package com.example.mycommon.ui;

import com.example.mycommon.R;
import com.example.mycommon.widget.wheel.ArrayWheelAdapter;
import com.example.mycommon.widget.wheel.Callback;
import com.example.mycommon.widget.wheel.WheelView;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TwoWheelSelectListenter implements OnClickListener{
	
	private PopupWindow window;
	private WheelView hourWheelView;
	private WheelView miniWheelView;
	private Context context;
	private View parentView;
	private TextView setValueTxt;
	private String[] hours;
	private String[] minites;
	private String cancelBtn;
	private String sureBtn;
	private String titleText;
	private int index ;
	/**点击右边btn的回调**/
	private Callback rightBtnCallBack;
	public TwoWheelSelectListenter( Context context , View parentView , TextView setValueTxt , 
			String titleText , String cancelBtn , String sureBtn, String[] hours, String[] minites,Callback rightBtnCallBack){
		this.context = context;
		this.parentView = parentView;
		this.setValueTxt = setValueTxt;
		this.hours = hours;
		this.minites = minites;
		this.cancelBtn = cancelBtn;
		this.sureBtn = sureBtn;
		this.titleText = titleText;
		this.rightBtnCallBack = rightBtnCallBack;
	}
	
	public TwoWheelSelectListenter( Context context , View parentView , TextView setValueTxt , String[] hours,String[] minites,Callback rightBtnCallBack ){
		this.context = context;
		this.parentView = parentView;
		this.setValueTxt = setValueTxt;
		this.hours = hours;
		this.minites = minites;
		this.rightBtnCallBack = rightBtnCallBack;
	}
	
	public TwoWheelSelectListenter( Context context , View parentView , TextView setValueTxt , String titleText, String[] hours,String[] minites,Callback rightBtnCallBack){
		this.context = context;
		this.parentView = parentView;
		this.setValueTxt = setValueTxt;
		this.hours = hours;
		this.minites = minites;
		this.titleText = titleText;
		this.rightBtnCallBack = rightBtnCallBack;
	}
	
	 

	public View getParentView() {
		return parentView;
	}

	public void setParentView(View parentView) {
		this.parentView = parentView;
	}

	public TextView getSetValueTxt() {
		return setValueTxt;
	}

	public void setSetValueTxt(TextView setValueTxt) {
		this.setValueTxt = setValueTxt;
	}
 

	public String getSureBtn() {
		return sureBtn;
	}

	public void setSureBtn(String sureBtn) {
		this.sureBtn = sureBtn;
	}

	public String getTitleText() {
		return titleText;
	}

	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}

	@Override
	public void onClick(View v) {
		show();
	}
	
	public void show(){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(parentView.getWindowToken(), 0);
		
		LayoutInflater inflater = LayoutInflater.from(context);
		
		View view = inflater.inflate(R.layout.wheel_pupop_two_demo, null);
		
		hourWheelView = (WheelView) view.findViewById( R.id.two_wheel1 );
		miniWheelView = (WheelView) view.findViewById( R.id.two_wheel2 );
		TextView text = (TextView) view.findViewById( R.id.title );
		Button sure = (Button) view.findViewById( R.id.ok );
		Button cancel = (Button) view.findViewById( R.id.cancel );
		
		sure.setText( sureBtn );
		cancel.setText( cancelBtn );
		text.setText(titleText);
		
		cancel.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				window.dismiss();
				window = null;
			}
		} );
		
		sure.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(null != rightBtnCallBack){
					rightBtnCallBack.handle(hourWheelView.getAdapter().getItem( hourWheelView.getCurrentItem())+miniWheelView.getAdapter().getItem( miniWheelView.getCurrentItem()));
				}
				//
				window.dismiss();
				window = null;
			}
		} );
		
		hourWheelView.setAdapter(new ArrayWheelAdapter<String>( hours ) );
		hourWheelView.TEXT_SIZE = dipTopx(context, 20);
//		hourWheelView.setLabel("点");
		hourWheelView.setLabel("");
		hourWheelView.setCurrentItem(index);
		
		miniWheelView.setAdapter(new ArrayWheelAdapter<String>( minites) );
		miniWheelView.TEXT_SIZE = dipTopx(context, 20);
//		miniWheelView.setLabel("分");
		miniWheelView.setLabel("");
		miniWheelView.setCurrentItem(index); 
		
		if (window == null){
			window = new PopupWindow(view, LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT, true);
			window.setBackgroundDrawable(new BitmapDrawable());
		}
		window.showAtLocation( parentView , Gravity.CENTER, 0, 0);
		
	}

	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param context
	 * @param dpValue
	 *            dp值
	 * @return 返回像素值
	 */
	public static int dipTopx(Context context, float dpValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
