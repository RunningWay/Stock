package com.buy.stock.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;

public class SlideListView extends ListView {
	private final static String TAG = "SlideListView";
	
	private static final int SNAP_VELOCITY = 3600;
	
	/**
	 * 当前滑动的Item position
	 */
	private int currentItemPosition;
	
	/**
	 * 当前滑动的Item view
	 */
	private View currentItemView;
	
	/**
	 * 手指按下x的坐标
	 */
	private int downX;
	
	/**
	 * 手指按下y的坐标
	 */
	private int downY;

	/**
	 * 屏幕宽度
	 */
	private int screenWidth;
	
	/**
	 * 滑动类
	 */
	private Scroller scroller;

	/**
	 * 速度追踪对象
	 */
	private VelocityTracker velocityTracker;
	
	/**
	 * 是否响应滑动，默认为不响应
	 */
	private boolean isSlide = false;
	
	/**
	 * 认为是用户滑动的最小距离
	 */
	private int mTouchSlop;
	
	private int LEFT_GONE_WIDTH = -1;
	private int RIGHT_GONE_WIDTH = -1;

	/**
	 * 滑动方向
	 */
	private SlideState slideDirection;

	public enum SlideState {
		NORMAL,RIGHT
	}

	public SlideListView(Context context) {
		this(context, null);
		screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		scroller = new Scroller(context);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	public SlideListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		scroller = new Scroller(context);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	public SlideListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		screenWidth = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		scroller = new Scroller(context);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	/**
	 * 分发事件，主要做的是判断点击的是那个item, 以及通过postDelayed来设置响应左右滑动事件
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: 
			addVelocityTracker(event);

			if (!scroller.isFinished()) {
				return super.dispatchTouchEvent(event);
			}
			
			downX = (int) event.getX();
			downY = (int) event.getY();

			currentItemPosition = pointToPosition(downX, downY);

			// 无效的position, 不做任何处理
			if (currentItemPosition == AdapterView.INVALID_POSITION) {
				return super.dispatchTouchEvent(event);
			}
			
			// 获取我们点击的item view
			currentItemView = getChildAt(currentItemPosition - getFirstVisiblePosition());
			break;
		case MotionEvent.ACTION_MOVE: 
			if (Math.abs(getScrollVelocity()) > SNAP_VELOCITY
					|| (Math.abs(event.getX() - downX) > mTouchSlop && Math.abs(event.getY() - downY) < mTouchSlop)) {
				isSlide = true;
			}
			break;
		case MotionEvent.ACTION_UP:
			recycleVelocityTracker();
			break;
		}
		return super.dispatchTouchEvent(event);
	}

	/**
	 * 处理我们拖动ListView item的逻辑
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (isSlide && currentItemPosition != AdapterView.INVALID_POSITION) {
			requestDisallowInterceptTouchEvent(true);
			addVelocityTracker(ev);
			final int action = ev.getAction();
			int x = (int) ev.getX();
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				MotionEvent cancelEvent = MotionEvent.obtain(ev);
	            cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
	                       (ev.getActionIndex()<< MotionEvent.ACTION_POINTER_INDEX_SHIFT));
	            onTouchEvent(cancelEvent);
	            
				int deltaX = downX - x;
				downX = x;

				// 手指拖动itemView滚动, deltaX大于0向左滚动，小于0向右滚
				int scrollX = currentItemView.getScrollX();
				if(scrollX < 100){
					slideDirection = SlideState.NORMAL;
				}else{
					slideDirection = SlideState.RIGHT;
				}
				currentItemView.scrollBy(deltaX, 0);
				return true;
			case MotionEvent.ACTION_UP:
				if(slideDirection ==SlideState.RIGHT){
					scroller.startScroll(0, 0, 400, 0, 200);
					//currentItemView.scrollTo(400, 0);
				}else{
					currentItemView.scrollTo(0, 0);
				}
				recycleVelocityTracker();
				isSlide = false;
				break;
			}
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			currentItemView.scrollTo(scroller.getCurrX(), scroller.getCurrY());
			postInvalidate();
			if (scroller.isFinished()) {
				//TODO
			}
		}
	}
	
	private void addVelocityTracker(MotionEvent event) {
		if (velocityTracker == null) {
			velocityTracker = VelocityTracker.obtain();
		}
		velocityTracker.addMovement(event);
	}
	
	private void recycleVelocityTracker() {
		if (velocityTracker != null) {
			velocityTracker.recycle();
			velocityTracker = null;
		}
	}

	/**
	 * 获取X方向的滑动速度,大于0向右滑动，反之向左
	 * 
	 * @return
	 */
	private int getScrollVelocity() {
		velocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) velocityTracker.getXVelocity();
		return velocity;
	}
}