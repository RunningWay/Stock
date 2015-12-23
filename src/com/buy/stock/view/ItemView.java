package com.buy.stock.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.widget.Scroller;

public class ItemView extends ViewGroup{
	private static final String TAG = "ItemView";
	
	private static final int TOUCH_STATE_REST = 0;
	private static final int TOUCH_STATE_SCROLLING = 1;
	private static final int SNAP_VELOCITY = 600;
	
	private Scroller mScroller;
	private VelocityTracker mVelocity;
	
	public ItemView(Context context) {
		super(context);
	}

	public ItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int width  = MeasureSpec.getSize(widthMeasureSpec);
		final int height = MeasureSpec.getSize(heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		
	}
}
