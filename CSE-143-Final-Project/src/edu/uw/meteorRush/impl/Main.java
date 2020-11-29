package edu.uw.meteorRush.impl;

import edu.uw.meteorRush.common.Game;

public class Main {

	public static final int WIDTH = 1800;
	public static final int HEIGHT = 800;

	public static void main(String[] args) {
		Game game = new Game("Meteor Rush", WIDTH, HEIGHT);
		game.start();
		game.loadScene(new GameScene());
	}

}
