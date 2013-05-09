package com.zenobase.mindlog;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import ca.scotthyndman.sparklines.LineSparkView;
import com.google.common.primitives.Floats;

public class ScoreChartView extends LineSparkView {

	public ScoreChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setChartRange(0, 100);
		setShowLatestSpot(true);
		setShowMaxPoint(false);
		setShowMinSpot(false);
		setLineWidth(5.0f);
		setSpotRadius(10.0f);
		setLineColor(Color.WHITE);
		setBackgroundColor(Color.TRANSPARENT);
	}

	public void setScores(List<Integer> values) {
		setValues(toFloats(values));
		invalidate();
	}

	private static List<Float> toFloats(List<Integer> values) {
		return Floats.asList(Floats.toArray(values));
	}
}
