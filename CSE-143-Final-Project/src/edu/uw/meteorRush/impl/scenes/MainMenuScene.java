package edu.uw.meteorRush.impl.scenes;

import java.awt.Graphics;
import java.awt.Image;
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
		if (inputManager.keyDown(KeyEvent.VK_DOWN)) {
			// down option
		}
		if (inputManager.keyDown(KeyEvent.VK_UP)) {
			// up option
		}
		if (inputManager.keyDown(KeyEvent.VK_ENTER)) {
			// select option
			Game.getInstance().loadScene(new GameScene());
		}
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		// render background and text
		g.drawImage(backgroundImage, 0, 0, null);
		// the selected "button" should stand out
	}

	@Override
	public void dispose() {
		super.dispose();
		backgroundMusic.stop();
	}

}
