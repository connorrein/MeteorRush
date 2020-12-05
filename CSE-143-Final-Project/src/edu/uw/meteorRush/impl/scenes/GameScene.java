package edu.uw.meteorRush.impl.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

import edu.uw.meteorRush.common.Collider;
import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Entity.EntityCollider;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.InputManager;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Scene;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.Main;
import edu.uw.meteorRush.impl.entities.PlayerShip;
import edu.uw.meteorRush.impl.waves.Wave1;

/**
 * 
 * @author Connor Reinholdtsen
 * @author Jacob Barnhart
 */
public class GameScene extends Scene {

	public static final double FIRST_WAVE_WAIT_TIME = 2.5;
	public static final double WAVE_REST_TIME = 5.0;
	private static final Font UI_FONT = new Font("Consolas", 0, 50);
	private static final Font SELECT_FONT = new Font("Consolas", 0, 70);
	private static final Vector2 PLAYER_START = new Vector2(250, Main.HEIGHT / 2);
	private final String[] PAUSE_MENU_OPTIONS = { "Continue", "Main Menu" };

	private Collider bounds;
	private BufferedImage backgroundImage;
	private Image pauseBackgroundImage;
	private Clip backgroundMusic;
	private PlayerShip player;
	private int score;
	private int currentPauseOption;
	private double currentHealth;
	private double maxHealth;
	private boolean paused;
	private InputManager inputManager;

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
		backgroundImage = ResourceLoader.toBufferedImage(
				ResourceLoader.loadImage("res/images/backgrounds/GameBackground.png").getScaledInstance(24750, 825, 0));
		pauseBackgroundImage = ResourceLoader.loadImage("res/images/backgrounds/PauseBackground.jpg");
		backgroundMusic = ResourceLoader.loadAudioClip("res/audio/GameMusic.wav");
		backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
		currentPauseOption = 0;
		inputManager = Game.getInstance().getInputManager();
		player = new PlayerShip(PLAYER_START);
		addObject(player);
		addObject(new Wave1());
		maxHealth = player.getMaxHealth();
		addObject(new FadeIn(1.5));
	}

	@Override
	public void tick() {
		super.tick();
		if (paused) {
			if (Game.getInstance().getInputManager().getKeyDown(KeyEvent.VK_ESCAPE)) {
				unPause();
			}
			pauseTick();
		} else {
			if (Game.getInstance().getInputManager().getKeyDown(KeyEvent.VK_ESCAPE)) {
				pause();
			}
		}
		currentHealth = player.getCurrentHealth();
	}

	private void pauseTick() {
		scroll(inputManager);
		if (inputManager.getKeyDown(KeyEvent.VK_ENTER)) {
			if (currentPauseOption == 0) {
				unPause();
			} else if (currentPauseOption == 1) {
				unPause();
				backgroundMusic.stop();
				Game.getInstance().loadScene(new MainMenuScene());
			}
		}
	}

	private void scroll(InputManager inputManager) {
		if (inputManager.getKeyDown(KeyEvent.VK_DOWN)) {
			currentPauseOption++;
			if (currentPauseOption >= PAUSE_MENU_OPTIONS.length) {
				currentPauseOption = 0;
			}
		} else if (inputManager.getKeyDown(KeyEvent.VK_UP)) {
			currentPauseOption--;
			if (currentPauseOption < 0) {
				currentPauseOption = PAUSE_MENU_OPTIONS.length - 1;
			}
		}
	}

	private void pause() {
		Game.getInstance().setTimeScale(0.0);
		paused = true;
	}

	private void unPause() {
		Game.getInstance().setTimeScale(1.0);
		paused = false;
	}

	@Override
	public void render(Graphics g) {
		if (paused) {
			renderPause(g);
		} else {
			double time = Game.getInstance().getTime();
			int x = (int) (time * 150 % 22195);
			Image backgroundSubImage = backgroundImage.getSubimage(x, 0, Main.WIDTH, Main.HEIGHT);
			g.drawImage(backgroundSubImage, 0, 0, null);
			super.render(g);
			g.setColor(Color.WHITE);
			g.setFont(UI_FONT);
			g.drawString("Score: " + score, 50, 50);
			g.drawString("Health", Main.WIDTH - 200, 50);
			g.drawRect(Main.WIDTH - 225, 60, 200, 15);
			g.setColor(Color.RED);
			g.fillRect(Main.WIDTH - 224, 61, (int) (currentHealth / maxHealth * 200.0), 13);
		}
	}

	public void renderPause(Graphics g) {
		g.drawImage(pauseBackgroundImage, 0, 0, null);
		for (int i = 0; i < PAUSE_MENU_OPTIONS.length; i++) {
			if (currentPauseOption == i) {
				g.setFont(SELECT_FONT);
				g.setColor(Color.RED);
			} else {
				g.setFont(UI_FONT);
				g.setColor(Color.WHITE);
			}
			g.drawString(PAUSE_MENU_OPTIONS[i], 400, 300 + i * 70);
		}
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
