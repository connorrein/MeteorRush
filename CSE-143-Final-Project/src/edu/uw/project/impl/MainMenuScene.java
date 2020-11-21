package edu.uw.project.impl;

import java.awt.Graphics;
import javax.sound.sampled.Clip;

import edu.uw.project.common.ResourceLoader;
import edu.uw.project.common.Scene;

public class MainMenuScene extends Scene {

	private boolean initialized;
	private Clip backgroundMusic;
	private Alien alien;

	@Override
	public void tick() {
		if (!initialized) {
			initialized = true;
			alien = new Alien();
			backgroundMusic = ResourceLoader.loadAudioClip("res/MainMenuMusic.wav");
			backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
		}
		alien.tick();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.gameBackground, 0, 0, null);
		alien.render(g);
	}

}
