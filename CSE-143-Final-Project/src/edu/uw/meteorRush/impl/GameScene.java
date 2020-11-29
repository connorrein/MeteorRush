package edu.uw.meteorRush.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.Clip;

import edu.uw.meteorRush.common.Collider;
import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.Entity.EntityCollider;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Scene;
import edu.uw.meteorRush.common.Vector2;

public class GameScene extends Scene {

	private static final double WAVE_REST_TIME = 5.0;
	private static final Font UI_FONT = new Font("Consolas", 0, 50);
	private static final Vector2 PLAYER_START = new Vector2(250, 350);

	private Collider bounds;
	private Image backgroundImage;
	private Clip backgroundMusic;
	private PlayerShip player;
	private int score;

	@Override
	public void initialize() {
		bounds = new Collider(0, 0, Main.WIDTH, Main.HEIGHT) {
			@Override
			public void onCollisionEnter(Collider other) {
			}

			@Override
			public void onCollisionExit(Collider other) {
				if (other instanceof EntityCollider) {
					Entity entity = ((EntityCollider) other).getEntity();
					Game.getInstance().getOpenScene().removeEntity(entity);
				}
			}
		};
		backgroundImage = ResourceLoader.loadImage("res/GameBackground.jpg");
		backgroundMusic = ResourceLoader.loadAudioClip("res/GameMusic.wav");
		backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
		player = new PlayerShip(PLAYER_START);
		addEntity(player);
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

	@Override
	public void dispose() {
		super.dispose();
		bounds.dispose();
	}

}
