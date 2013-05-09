package com.zenobase.mindlog.tests;

import junit.framework.TestCase;
import com.google.common.testing.EqualsTester;

import com.zenobase.mindlog.Score;

public class ScoreTest extends TestCase {

	public void test() {
		new EqualsTester()
			.addEqualityGroup(new Score(0), new Score(0))
			.addEqualityGroup(new Score(1))
			.testEquals();
	}
}
