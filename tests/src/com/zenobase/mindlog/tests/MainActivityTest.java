package com.zenobase.mindlog.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import com.jayway.android.robotium.solo.Solo;

import com.zenobase.mindlog.MainActivity;
import com.zenobase.mindlog.R;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo;

	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@LargeTest
	public void test() throws Exception {
		solo.clickOnActionBarItem(R.id.menu_start);
		solo.sleep(10000);
		solo.clickOnActionBarItem(R.id.menu_stop);
		solo.clickOnButton("Discard");
	}

	@Override
	protected void tearDown() {
		solo.finishOpenedActivities();
	}
}
