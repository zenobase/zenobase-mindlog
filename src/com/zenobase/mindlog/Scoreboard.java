package com.zenobase.mindlog;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.squareup.otto.Bus;

public class Scoreboard {

	public static final int HISTORY = 60;

	private final Bus bus;
	private final List<Integer> scores = Lists.newLinkedList();
	private double mean;
	private int count;
	private boolean enabled;

	@Inject
	public Scoreboard(Bus bus) {
		this.bus = bus;
		reset();
	}

	public void add(int score) {
		if (enabled && score > 0) {
			mean += (score - mean) / ++count;
			scores.add(score);
			scores.remove(0);
        	bus.post(new Score(score));
		}
	}

	public List<Integer> getScores() {
		return scores;
	}

	public int getLast() {
		return !scores.isEmpty() ? Iterables.getLast(scores) : 0;
	}

	public int getMean() {
		return Ints.saturatedCast(Math.round(mean));
	}

	public int getCount() {
		return count;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void reset() {
		mean = 0.0;
		count = 0;
		scores.clear();
		fill(scores, 0, HISTORY);
    	bus.post(new Score(0));
	}

	private static <T> void fill(List<T> items, T item, int count) {
		for (int i = 0; i < count; ++i) {
			items.add(item);
		}
	}
}
