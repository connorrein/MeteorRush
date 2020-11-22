package edu.uw.meteorRush.impl;

import edu.uw.meteorRush.common.Game;

public class Main {

	public static void main(String[] args) {
		Game game = new Game("Title", 1700, 800);
		game.start();
		game.loadScene(new GameScene());
	}

}
