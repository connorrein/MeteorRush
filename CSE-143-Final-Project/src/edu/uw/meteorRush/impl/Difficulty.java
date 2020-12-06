package edu.uw.meteorRush.impl;

public enum Difficulty {

	EASY(0.5), MEDIUM(1), HARD(1.5);

	private double modifier;

	Difficulty(double modifier) {
		this.modifier = modifier;
	}

	public double getModifier() {
		return modifier;
	}

}
