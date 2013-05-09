package com.zenobase.mindlog;

import com.google.common.base.Objects;

public class Score {

	private final int value;

	public Score(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public boolean equals(Object that) {
		return that instanceof Score &&
			equals((Score) that);
	}

	public boolean equals(Score that) {
		return value == that.value;
	}

	@Override
	public int hashCode() {
		return value;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this)
			.add("value", value)
			.toString();
	}
}
