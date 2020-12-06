package edu.uw.meteorRush.impl;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.impl.scenes.MainMenuScene;

public class Main {

	public static final int WIDTH = 1800;
	public static final int HEIGHT = 800;

	public static Difficulty difficulty = Difficulty.MEDIUM;

	public static void main(String[] args) {
		Game game = new Game("Meteor Rush", WIDTH, HEIGHT);
		game.start();
		game.loadScene(new MainMenuScene());
	}

}
