package edu.uw.meteorRush.impl;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.menus.GraphicsMenuListener;
import edu.uw.meteorRush.menus.MenuDisplay;

public class Main {

	public static void main(String[] args) {
		//creates a main menu which closes after pressing the Play button
		// the play button starts the game
		MenuDisplay menuDisplay = new MenuDisplay("Main Menu", 900, 500);
		menuDisplay.SetMenuListener(
				new GraphicsMenuListener() {
					@Override
					public void menuSelected(String option) {
						if(option.equals("Play")) {
							Game game = new Game("Title", 1800, 800);
							game.start();
							game.loadScene(new GameScene());
							menuDisplay.close();
						}
					}
				}
		);
	}

}
