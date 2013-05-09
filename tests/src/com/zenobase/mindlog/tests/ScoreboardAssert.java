package com.zenobase.mindlog.tests;

import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.api.Assertions;

import com.zenobase.mindlog.Scoreboard;

public class ScoreboardAssert extends AbstractAssert<ScoreboardAssert, Scoreboard> {

	public static ScoreboardAssert assertThat(Scoreboard actual) {
		return new ScoreboardAssert(actual);
	}

	private ScoreboardAssert(Scoreboard actual) {
		super(actual, ScoreboardAssert.class);
	}

	public ScoreboardAssert hasCount(int count) {
		Assertions.assertThat(actual.getCount()).as("count").isEqualTo(count);
		return this;
	}

	public ScoreboardAssert hasMean(int mean) {
		Assertions.assertThat(actual.getMean()).as("mean").isEqualTo(mean);
		return this;
	}

	public ScoreboardAssert hasScores(Integer... scores) {
		Assertions.assertThat(actual.getScores()).as("number of scores").hasSize(Scoreboard.HISTORY);
		Assertions.assertThat(actual.getScores()).as("unique scores").containsOnly(scores);
		return this;
	}

	public ScoreboardAssert isEmpty() {
		return hasCount(0).hasMean(0).hasScores(0);
	}
}
