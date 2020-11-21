package edu.uw.project.impl;

import java.awt.Graphics;

import javax.sound.sampled.Clip;

import edu.uw.project.common.ResourceLoader;
import edu.uw.project.common.Scene;

public class GameScene extends Scene {

	private Clip backgroundMusic;
	private Player player;
	private boolean initialized;

	@Override
	public void tick() {
		if (!initialized) {
			initialized = true;
			backgroundMusic = ResourceLoader.loadAudioClip("res/GameMusic.wav");
			backgroundMusic.start();
			player = new Player();
		}
		player.tick();
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.gameBackground, 0, 0, null);
		player.render(g);
	}

}
