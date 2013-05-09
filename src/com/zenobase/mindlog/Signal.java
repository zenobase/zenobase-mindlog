package com.zenobase.mindlog;

import com.google.common.base.Objects;

public class Signal {

	private final String source;
	private final int strength;

	public Signal(String source, int strength) {
		this.source = source;
		this.strength = strength;
	}

	public String getSource() {
		return source;
	}

	public int getStrength() {
		return strength;
	}

	@Override
	public boolean equals(Object that) {
		return that instanceof Signal &&
			equals((Signal) that);
	}

	public boolean equals(Signal that) {
		return strength == that.getStrength() &&
			Objects.equal(source, that.getSource());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(source, strength);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
			.add("source", source)
			.add("strength", strength)
			.toString();
	}
}
