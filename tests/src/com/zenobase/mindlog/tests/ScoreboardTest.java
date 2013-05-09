package com.zenobase.mindlog.tests;

import static com.zenobase.mindlog.tests.ScoreboardAssert.assertThat;

import static org.mockito.Mockito.*;

import junit.framework.TestCase;
import com.squareup.otto.Bus;

import com.zenobase.mindlog.Score;
import com.zenobase.mindlog.Scoreboard;

public class ScoreboardTest extends TestCase {

	public void test() {

		Bus bus = mock(Bus.class);
		Scoreboard scoreboard = new Scoreboard(bus);
		assertThat(scoreboard).isEmpty();

		scoreboard.add(50);
		assertThat(scoreboard).isEmpty();

		scoreboard.setEnabled(true);
		assertThat(scoreboard).isEmpty();

		scoreboard.add(50);
		assertThat(scoreboard).hasCount(1).hasMean(50).hasScores(0, 50);

		scoreboard.add(70);
		assertThat(scoreboard).hasCount(2).hasMean(60).hasScores(0, 50, 70);

		scoreboard.reset();
		assertThat(scoreboard).isEmpty();

		verify(bus, times(2)).post(new Score(0));
		verify(bus).post(new Score(50));
		verify(bus).post(new Score(70));
	}
}
