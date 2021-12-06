package impl;

public enum Difficulty {
    EASY(0.75), MEDIUM(1), HARD(1.25);

    private double modifier;

    Difficulty(double modifier) {
	this.modifier = modifier;
    }

    public double getModifier() {
	return modifier;
    }
}
