package com.zenobase.mindlog.tests;

import junit.framework.TestCase;
import com.google.common.testing.EqualsTester;

import com.zenobase.mindlog.Signal;

public class SignalTest extends TestCase {

	public void test() {
		new EqualsTester()
			.addEqualityGroup(new Signal("foo", 1), new Signal("foo", 1))
			.addEqualityGroup(new Signal("foo", 2))
			.addEqualityGroup(new Signal("bar", 1))
			.testEquals();
	}
}
