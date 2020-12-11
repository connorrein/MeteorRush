package edu.uw.meteorRush.impl;

import java.awt.Image;

import edu.uw.meteorRush.gameEngine.Game;
import edu.uw.meteorRush.gameEngine.ResourceLoader;
import edu.uw.meteorRush.impl.scenes.MainMenuScene;

public class Main {

	public static final int WIDTH = 1800;
	public static final int HEIGHT = 800;

	public static Difficulty difficulty = Difficulty.MEDIUM;

	public static void main(String[] args) {
		Image icon = ResourceLoader.loadImage("res/images/Icon.png");
		Game game = new Game("Meteor Rush", WIDTH, HEIGHT, icon);
		game.start();
		game.loadScene(new MainMenuScene());
	}

}
