package edu.uw.meteorRush.impl.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import edu.uw.meteorRush.common.Collider;
import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Entity.EntityCollider;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Scene;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.Main;
import edu.uw.meteorRush.impl.entities.PlayerShip;
import edu.uw.meteorRush.impl.waves.Wave1;

public class GameScene extends Scene {

	public static final double FIRST_WAVE_WAIT_TIME = 2.5;
	public static final double WAVE_REST_TIME = 5.0;
	private static final Font UI_FONT = new Font("Consolas", 0, 50);
	private static final Vector2 PLAYER_START = new Vector2(250, 350);

	private Collider bounds;
	private Image backgroundImage;
	private Clip backgroundMusic;
	private PlayerShip player;
	private int score;
	private double currentHealth;
	private double maxHealth;

	@Override
	public void initialize() {
		bounds = new Collider(-500, -500, Main.WIDTH + 500, Main.HEIGHT + 500) {
			@Override
			public void onCollisionEnter(Collider other) {
			}

			@Override
			public void onCollisionExit(Collider other) {
				if (other instanceof EntityCollider) {
					Entity entity = ((EntityCollider) other).getEntity();
					Game.getInstance().getOpenScene().removeObject(entity);
				}
			}
		};
		bounds.setActive(true);
		backgroundImage = ResourceLoader.loadImage("res/GameBackground.jpg");
		backgroundMusic = ResourceLoader.loadAudioClip("res/GameMusic.wav");
		backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
		player = new PlayerShip(PLAYER_START);
		addObject(player);
		addObject(new Wave1());
		maxHealth = player.getMaxHealth();
	}

	@Override
	public void tick() {
		super.tick();
		if (Game.getInstance().getInputManager().getKeyDown(KeyEvent.VK_ESCAPE)) {
			pause();
		}
		currentHealth = player.getCurrentHealth();
	}

	private void pause() {
		Game.getInstance().setTimeScale(0.0);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, null);
		super.render(g);
		g.setColor(Color.WHITE);
		g.setFont(UI_FONT);
		g.drawString("Score: " + score, 50, 50);
		g.drawString("Health", Main.WIDTH - 200, 50);
		g.drawRect(Main.WIDTH - 225, 60, 200, 15);
		g.setColor(Color.RED);
		g.fillRect(Main.WIDTH - 224, 61, (int) (currentHealth / maxHealth * 200.0), 13);

	}

	public PlayerShip getPlayer() {
		return player;
	}

	public void addScore(int score) {
		this.score += score;
	}

	@Override
	public void dispose() {
		super.dispose();
		bounds.setActive(false);
	}

}
