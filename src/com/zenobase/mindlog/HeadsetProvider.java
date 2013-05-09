package com.zenobase.mindlog;

import javax.inject.Provider;

import android.bluetooth.BluetoothAdapter;
import com.google.inject.Inject;
import com.squareup.otto.Bus;


public class HeadsetProvider implements Provider<Headset> {

	private final Bus bus;
	private final Scoreboard scoreboard;

	@Inject
	public HeadsetProvider(Bus bus, Scoreboard scoreboard) {
		this.bus = bus;
		this.scoreboard = scoreboard;
	}

	@Override
	public Headset get() {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		return adapter != null && adapter.isEnabled()
			? new BluetoothHeadset(bus, scoreboard)
			: new SimulatedHeadset(bus, scoreboard);
	}
}
