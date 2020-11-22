package edu.uw.meteorRush.impl;

import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.Clip;

import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Scene;
import edu.uw.meteorRush.common.Vector2;

public class GameScene extends Scene {

	private Clip backgroundMusic;
	private Player player;
	private boolean initialized;

	@Override
	public void tick() {
		if (!initialized) {
			initialized = true;
			backgroundMusic = ResourceLoader.loadAudioClip("res/GameMusic.wav");
			backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
			player = new Player();
			addEntity(player);
			Alien alien = new Alien(new Vector2(1920, 250));
			addEntity(alien);
			Timer timer = new Timer();
			TimerTask spawnTask = new TimerTask() {
				@Override
				public void run() {
					addEntity(new Alien(new Vector2(1920, 250)));
				}
			};
			timer.scheduleAtFixedRate(spawnTask, 0, 1000);
		}
		super.tick();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.GAME_BACKGROUND, 0, 0, null);
		super.render(g);
	}

}
