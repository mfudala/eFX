package com.example.efx.model;

public enum Commission {
	STANDARD(0.001);

	private final double percentage;

	Commission(final double percentage) {
		this.percentage = percentage;
	}

	public double getValue() {
		return this.percentage;
	}
}
