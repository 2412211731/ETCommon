package com.example.mycommon.widget;

import java.io.Serializable;

import com.example.mycommon.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
/**
 * {@link email:1940125001@qq.com}
 * @author Fay
 * @since 2014/5/23
 */
public class LockView extends View {
	private String TAG = "SudokoView";
	
	//width for this SudokoView
	private int width ;
	
	//height for this SudokoView,width = height
	private int height ;
	
	//spacing between two points
	private int spacing = 0;
	
	private int startX = 0;
	private int startY = 0;
	
	//current x-Location when move
	private int moveX = 0;
	
	//current y-Location when move
	private int moveY = 0;
	
	//check whether the any point is selected
	private boolean hasSelected = false;
	
	//outer paint for the line
	private Paint outerPaint = null;
	
	//inner paint for the line
	private Paint innerPaint = null;
	
	//the String of lock
	private StringBuilder lockString = new StringBuilder();
	
	//default bitmap for the point
	private Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lock_default);
	
	//selected bitmap for the point
	private Bitmap selectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lock_selected);
    
	//the radius for the default bitmap
	private int defaultRadius = defaultBitmap.getWidth() / 2;
	
	//the radius for the selected bitmap
	private int selectedRadius = selectedBitmap.getWidth() / 2;
	
	//start point for touching;
	//this will only appears when the ACTION_DOWN in the area of nine points.
	private PointInfo startPoint = null;
	
	//nine point for this SudokoView
	private PointInfo ninePoints[] = new PointInfo[9];
	
	private OnLockFinishListener mOnLockFinishListener = null;
	
	public LockView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public LockView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LockView(Context context) {
		super(context);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		width = getWidth();
		height = getWidth();
		spacing = (width - selectedRadius * 2 * 3) / 4;
		initPoints();
		initPaint();
		super.onLayout(changed, left, top, right, bottom);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (startX > 0 && startY > 0 && moveX > 0 && moveY > 0) {
			canvas.drawLine(startX, startY, moveX, moveY, outerPaint);
		}
		drawNinePoint(canvas);
		super.onDraw(canvas);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (hasSelected) {//if the points has been selected previously, clear all
				initPoints();
				startPoint = null;
				hasSelected = false;
				lockString.delete(0, lockString.length());
				invalidate();
				return false;//must return false to end touch
			}
			int x = (int) event.getX();
			int y = (int) event.getY();
			for (PointInfo pointInfo : ninePoints) {
				if (pointInfo.isInPoint(x, y)) {
					pointInfo.setSelected(true);
					startPoint = pointInfo;
					startX = pointInfo.getCenterX();
					startY = pointInfo.getCenterY();
					lockString.append(pointInfo.getNumber());
					hasSelected = true;
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			Log.v(TAG, "ACTION_MOVE");
			moveX = (int) event.getX();
			moveY = (int) event.getY();
			for (PointInfo pointInfo : ninePoints) {
				if (!pointInfo.isSelected() && pointInfo.isInPoint(moveX, moveY)) {
					startX = pointInfo.getCenterX();
					startY = pointInfo.getCenterY();
					int length = lockString.length();
					if (length > 0) {
						int previousNumber = lockString.charAt(length - 1) - 48; 
						ninePoints[pointInfo.getNumber()].setSelected(true);
						ninePoints[previousNumber].setNextNumber(pointInfo.getNumber());
					}
					hasSelected = true;
					lockString.append(pointInfo.getNumber());
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if (lockString.length() > 0) {
				mOnLockFinishListener.finish(lockString);
			}
			startX = 0;
			startY = 0;
			moveX = 0;
			moveY = 0;
			break;
		}
		invalidate();
		return true;
	}

	/**
	 * draw the nine points for this SudokoView including default bitmap and selected bitmap
	 * @param Canvas canvas
	 */
	private void drawNinePoint(Canvas canvas) {
		if (null != startPoint) {
			drawLine(canvas, startPoint);
		}
		for (PointInfo mPointInfo : ninePoints) {
			//Firstly, if the point is selected, draw the selected bitmap on this point
			if (mPointInfo.isSelected()) {
				canvas.drawBitmap(selectedBitmap, mPointInfo.getSelectedX(), mPointInfo.getSelectedY(), null);
			}
			//Then, draw the default bitmap on this point
			canvas.drawBitmap(defaultBitmap, mPointInfo.getDefaultX(), mPointInfo.getDefaultY(), null);
		}
	}
	
	/**
	 * draw a line between two points
	 * @param Canvas canvas
	 * @param PointInfo mPointInfo
	 */
	private void drawLine(Canvas canvas, PointInfo mPointInfo) {
		while (mPointInfo.isHasNextPoint()) {
			int index = mPointInfo.getNextNumber();
			canvas.drawLine(mPointInfo.getCenterX(), mPointInfo.getCenterY(), ninePoints[index].getCenterX(), ninePoints[index].getCenterY(), outerPaint);
			canvas.drawLine(mPointInfo.getCenterX(), mPointInfo.getCenterY(), ninePoints[index].getCenterX(), ninePoints[index].getCenterY(), innerPaint);
			mPointInfo = ninePoints[index];
		}
	}
	
	/**
	 * initialize the paint 
	 */
	private void initPaint() {
		outerPaint = new Paint();
		outerPaint.setColor(Color.GRAY);//绘制的线的颜色
		outerPaint.setStrokeWidth(defaultBitmap.getWidth());
		outerPaint.setAntiAlias(true);
		outerPaint.setStrokeCap(Cap.ROUND);
		
		
		innerPaint = new Paint();
		innerPaint.setColor(Color.GRAY);//绘制的线的颜色
		innerPaint.setStrokeWidth(defaultBitmap.getWidth() - 5);
		innerPaint.setAntiAlias(true);
		innerPaint.setStrokeCap(Cap.ROUND);
	}
	
	
	/**
	 * initialize basic nine points
	 */
	private void initPoints() {
		int selectedX = spacing;
		int selectedY = spacing;
		int defaultX = spacing + selectedRadius - defaultRadius;
		int defaultY = spacing + selectedRadius - defaultRadius;
		PointInfo mPointInfo = null;
		for (int index = 0; index < 9; index ++) {
			if  (index == 3 || index == 6) {
				selectedX = spacing;
				selectedY += selectedRadius * 2 + spacing;
				defaultX = spacing + selectedRadius - defaultRadius;
				defaultY += selectedRadius * 2 + spacing;
			} else {
				if (index != 0) {
					selectedX += selectedRadius * 2 + spacing;
					//selectedY = selectedY;
					defaultX += selectedRadius * 2 + spacing;
					//defaultY = defaultY;
				}
			}
			mPointInfo = new PointInfo(defaultX, defaultY, selectedX, selectedY, defaultRadius, selectedRadius, index, index);
			mPointInfo.setSelected(false);
			mPointInfo.setNumber(index);
			mPointInfo.setNextNumber(index);
		    ninePoints[index] = mPointInfo;
		}
	}
	
	/**
	 * Listener when the locking is finishing
	 */
	public interface OnLockFinishListener {
		void finish(StringBuilder lockString) ;
	}
	
	/**
	 * set the Listener for the callback
	 */
	public void setOnLockFinishListener (OnLockFinishListener mOnLockFinishListener) {
		this.mOnLockFinishListener = mOnLockFinishListener;
	}
	
	
	
	class PointInfo implements Serializable{
	    private static final long serialVersionUID = 1L;
	    //default x-Location
		private int defaultX;
		
		//default y-Location
		private int defaultY;
		
		//selected x-Location
		private int selectedX;
		
		//selected y-Location
		private int selectedY;
		
		//the radius for the default bitmap
		private int defaultRadius;
		
		//the radius for the selected bitmap
		private int selectedRadius;
		
		//the number about this point
		private int number;
		
		//the next point connection with this point
		private int nextNumber;
		
		//whether the point is selected
		private boolean isSelected;
		
		public PointInfo(int defaultX, int defaultY, int selectedX, int selectedY,
				int defaultRadius, int selectedRadius, int number, int nextNumber) {
			super();
			this.defaultX = defaultX;
			this.defaultY = defaultY;
			this.selectedX = selectedX;
			this.selectedY = selectedY;
			this.defaultRadius = defaultRadius;
			this.selectedRadius = selectedRadius;
			this.number = number;
			this.nextNumber = number;//
		}
		public int getDefaultX() {
			return defaultX;
		}
		public void setDefaultX(int defaultX) {
			this.defaultX = defaultX;
		}
		public int getDefaultY() {
			return defaultY;
		}
		public void setDefaultY(int defaultY) {
			this.defaultY = defaultY;
		}
		public int getSelectedX() {
			return selectedX;
		}
		public void setSelectedX(int selectedX) {
			this.selectedX = selectedX;
		}
		public int getSelectedY() {
			return selectedY;
		}
		public void setSelectedY(int selectedY) {
			this.selectedY = selectedY;
		}
		public int getDefaultRadius() {
			return defaultRadius;
		}
		public void setDefaultRadius(int defaultRadius) {
			this.defaultRadius = defaultRadius;
		}
		public int getSelectedRadius() {
			return selectedRadius;
		}
		public void setSelectedRadius(int selectedRadius) {
			this.selectedRadius = selectedRadius;
		}
		public int getNumber() {
			return number;
		}
		public void setNumber(int number) {
			this.number = number;
		}
		public int getNextNumber() {
			return nextNumber;
		}
		public void setNextNumber(int nextNumber) {
			this.nextNumber = nextNumber;
		}
		public boolean isSelected() {
			return isSelected;
		}
		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		}
		
		/*
		 * whether current point which touch is in the area of this point
		 * @return boolean
		 */
		public boolean isInPoint(int x, int y) {
			return  (selectedX <= x && x <= selectedX + 2 * selectedRadius 
					&& selectedY <= y && y <= selectedY + 2 * selectedRadius);
		}
		
		/**
		 * whether current point the next connecting point
		 * @return boolean
		 */
		public boolean isHasNextPoint() {
			//if equals, return false, else return true
			return (number == nextNumber) ? false : true; 
		}
		
		/**
		 * get the center x-Location of this point
		 * @return Integer
		 */
		public int getCenterX() {
			return selectedX + selectedRadius;
		}
		
		/**
		 * get the center y-Location of this point
		 * @return
		 */
		public int getCenterY() {
			return selectedY + selectedRadius;
		}
		
		@Override
		public String toString() {
			return "PointInfo [defaultX=" + defaultX + ", defaultY=" + defaultY
					+ ", selectedX=" + selectedX + ", selectedY=" + selectedY
					+ ", defaultRadius=" + defaultRadius + ", selectedRadius="
					+ selectedRadius + ", number=" + number + ", nextNumber="
					+ nextNumber + ", isSelected=" + isSelected + "]";
		}
		
	}

	
}
