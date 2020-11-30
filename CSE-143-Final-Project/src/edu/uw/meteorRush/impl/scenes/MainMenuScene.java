package edu.uw.meteorRush.impl.scenes;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.InputManager;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Scene;

public class MainMenuScene extends Scene {

	/**
	 * Stores the current "button" the user has selected.
	 */
	private int currentSelection;

	private Image backgroundImage;
	private Clip backgroundMusic;
	private static final Font UI_FONT = new Font("Consolas", 0, 50);
	// Bigger size for when the option is selected
	private static Font SELECT_FONT = new Font("Consolas", 0, 70);
	// list of options
	private String[] options = {"Intro", "START", "CREDITS", "SETTINGS", "QUIT"};

	@Override
	public void initialize() {
		backgroundImage = ResourceLoader.loadImage("res/MainMenuBackground.jpg");
		backgroundMusic = ResourceLoader.loadAudioClip("res/MainMenuMusic.wav");
		backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
	}

	@Override
	public void tick() {
		InputManager inputManager = Game.getInstance().getInputManager();
		// use InputManager to process user input

		 // the if it passes the bottom, wraps around to the top
		if (inputManager.getKeyDown(KeyEvent.VK_DOWN)) {
			currentSelection++;
			if(currentSelection > 4) {
				currentSelection = 0;
			}
		}

		 // the if it passes the top, wraps around to the bottom
		if (inputManager.getKeyDown(KeyEvent.VK_UP)) {
			currentSelection--;
			if(currentSelection < 0) {
				currentSelection = 4;
			}
		}

		// depending on the option selected, enter will do something else
		if (inputManager.getKey(KeyEvent.VK_ENTER)) {
			if(currentSelection == 0) {
				Game.getInstance().loadScene(new IntroScene());
			} else if(currentSelection == 1) {
				Game.getInstance().loadScene(new GameScene());
			} else if(currentSelection == 2) {
				Game.getInstance().loadScene(new CreditsScene());
			} else if(currentSelection == 3){
				Game.getInstance().loadScene(new SettingsScene());
			} else if(currentSelection == 4){
				// TODO

			}
		}
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		// render background and text
		//g.drawImage(backgroundImage, 0, 0, null);
		makeBig(g, 4);
		makeBig(g, 3);
		makeBig(g, 2);
		makeBig(g, 1);
		makeBig(g, 0);
	}

	/**
	 *
	 * @param g: graphics from above
	 * @param i: the option in number form
	 * Increases the size of option currently being considered
	 * Also sets its color to red
	 */
	private void makeBig(Graphics g, int i) {
		if (currentSelection == i) {
			g.setColor(Color.RED);
			g.setFont(SELECT_FONT);
		} else {
			g.setColor(Color.WHITE);
			g.setFont(UI_FONT);
		}
		g.drawString(options[i], 475, 155 + 70 * i);
	}

	@Override
	public void dispose() {
		super.dispose();
		backgroundMusic.stop();
	}

}
