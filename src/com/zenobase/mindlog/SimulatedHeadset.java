package com.zenobase.mindlog;

import java.util.Random;

import javax.inject.Inject;

import roboguice.util.Ln;
import android.os.Handler;
import com.squareup.otto.Bus;

public class SimulatedHeadset implements Headset, Runnable {

	private final Handler handler = new Handler();
	private final Random rand = new Random();
	private final Bus bus;
	private final Scoreboard scoreboard;

	@Inject
	public SimulatedHeadset(Bus bus, Scoreboard scoreboard) {
		this.bus = bus;
		this.scoreboard = scoreboard;
	}

	@Override
	public void connect() {
		Ln.d("Connecting simulated headset...");
		handler.post(this);
	}

	@Override
	public void run() {
		bus.post(new Signal("Simulated", 100));
		scoreboard.add(rand.nextInt(101));
		handler.postDelayed(this, 1000);
	}

	@Override
	public void disconnect() {
		Ln.d("Disconnecting simulated headset...");
		handler.removeCallbacks(this);
	}
}
