package com.buy.stock.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class TagView extends View {
	private CharSequence text;

	public TagView(Context context) {
		super(context);
	}

	public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public TagView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setText(int resId) {
		text = getResources().getText(resId);
	}

	public void setText(CharSequence text) {
		this.text = text;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (canvas == null) {
			return;
		}
	}

	private Path getPath() {
		Path path = new Path();
		path.moveTo(0, 0);
		path.lineTo(getWidth(), 0);
		return path;
	}
}
