package edu.uw.meteorRush.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.Clip;

import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Scene;
import edu.uw.meteorRush.common.Vector2;

public class GameScene extends Scene {

	private static final Font UI_FONT = new Font("Consolas", 0, 50);
	private static final Vector2 PLAYER_START = new Vector2(250, 350);

	private Image backgroundImage;
	private Clip backgroundMusic;
	private PlayerShip player;
	private int score;

	@Override
	public void initialize() {
		backgroundImage = ResourceLoader.loadImage("res/GameBackground.jpg");
		backgroundMusic = ResourceLoader.loadAudioClip("res/GameMusic.wav");
		backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
		player = new PlayerShip(PLAYER_START);
		addEntity(player);
		Timer timer = new Timer();
		TimerTask spawnTask = new TimerTask() {
			@Override
			public void run() {
				addEntity(new AlienShip(new Vector2(1920, 250)));
			}
		};
		timer.scheduleAtFixedRate(spawnTask, 0, 1000);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, null);
		super.render(g);
		g.setColor(Color.WHITE);
		g.setFont(UI_FONT);
		g.drawString("Score: " + score, 50, 50);
	}

	public void addScore(int score) {
		this.score += score;
	}

}
