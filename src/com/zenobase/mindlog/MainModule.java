package com.zenobase.mindlog;

import com.google.inject.AbstractModule;
import com.squareup.otto.Bus;

public class MainModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Bus.class).asEagerSingleton();
		bind(Scoreboard.class).asEagerSingleton();
		bind(Headset.class).toProvider(HeadsetProvider.class);
	}
}
