package com.example.major.help;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ScrollViewAndListViewConfilct extends ListView {
	public ScrollViewAndListViewConfilct(Context context) {
		super(context);
	}

	public ScrollViewAndListViewConfilct(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollViewAndListViewConfilct(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}