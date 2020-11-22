package edu.uw.meteorRush.impl;

import java.awt.Graphics;
import javax.sound.sampled.Clip;

import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Scene;

public class MainMenuScene extends Scene {

	private boolean initialized;
	private Clip backgroundMusic;

	@Override
	public void tick() {
		if (!initialized) {
			initialized = true;
			backgroundMusic = ResourceLoader.loadAudioClip("res/MainMenuMusic.wav");
			backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
		}
		super.tick();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.GAME_BACKGROUND, 0, 0, null);
		super.render(g);
	}

}
