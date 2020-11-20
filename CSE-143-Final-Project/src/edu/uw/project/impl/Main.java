package edu.uw.project.impl;

import edu.uw.project.common.Game;

public class Main {

	public static void main(String[] args) {
		Game game = new Game("Title", 1500, 1000);
		game.start();
		game.setScene(new MainMenu());
//		State mainMenu = new MainMenu();
//		game.setState(mainMenu);
	}

}
