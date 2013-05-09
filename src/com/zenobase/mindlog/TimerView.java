package com.zenobase.mindlog;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TimerView extends TextView {

	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setValue(0);
	}

    public void setValue(int seconds) {
		setText(String.format("%02d:%02d", seconds / 60, seconds % 60));
	}
}
