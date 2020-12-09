package edu.uw.meteorRush.impl.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.FloatControl.Type;

import edu.uw.meteorRush.common.Collider;
import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Entity.EntityCollider;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.InputManager;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.Main;
import edu.uw.meteorRush.impl.entities.PlayerShip;
import edu.uw.meteorRush.impl.waves.Wave1;

/**
 * 
 * @author Jacob Barnhart
 */
public class GameScene extends SceneWithKeys {

	public static final double FIRST_WAVE_WAIT_TIME = 2.5;
	public static final double WAVE_REST_TIME = 5.0;
	private static final Font UI_FONT = ResourceLoader.loadFont("res/Font.ttf", 50);
	private static final Vector2 PLAYER_START = new Vector2(250, Main.HEIGHT / 2);
	private final String[] PAUSE_MENU_OPTIONS = { "Continue", "Main Menu" };

	private static final Image GREEN_HP_FRAME = ResourceLoader.loadImage("res/images/ui/HealthOutlineGreen.png")
			.getScaledInstance((int) (142 * 2.5), (int) (20 * 2.5), 0);
	private static final Image YELLOW_HP_FRAME = ResourceLoader.loadImage("res/images/ui/HealthOutlineYellow.png")
			.getScaledInstance((int) (142 * 2.5), (int) (20 * 2.5), 0);
	private static final Image RED_HP_FRAME = ResourceLoader.loadImage("res/images/ui/HealthOutlineRed.png")
			.getScaledInstance((int) (142 * 2.5), (int) (20 * 2.5), 0);
	private static final Image GREEN_HP_BAR = ResourceLoader.loadImage("res/images/ui/HealthBitGreen.png")
			.getScaledInstance((int) (10 * 2.5), (int) (14 * 2.5), 0);
	private static final Image YELLOW_HP_BAR = ResourceLoader.loadImage("res/images/ui/HealthBitYellow.png")
			.getScaledInstance((int) (10 * 2.5), (int) (14 * 2.5), 0);
	private static final Image RED_HP_BAR = ResourceLoader.loadImage("res/images/ui/HealthBitRed.png")
			.getScaledInstance((int) (10 * 2.5), (int) (14 * 2.5), 0);

	private Collider bounds;
	private BufferedImage backgroundImage;
	private Clip backgroundMusic;
	private PlayerShip player;
	private int score;
	private int currentPauseOption;
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
		backgroundMusic = ResourceLoader.loadAudioClip("res/audio/GameMusic.wav");
		backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
		currentPauseOption = 0;
		inputManager = Game.getInstance().getInputManager();
		player = new PlayerShip(PLAYER_START);
		addObject(player);
		addObject(new FadeIn(1.5));
		addObject(new Wave1());
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
	}

	private void pauseTick() {
		currentPauseOption = upDown(inputManager, PAUSE_MENU_OPTIONS, currentPauseOption);
		if (inputManager.getKeyDown(KeyEvent.VK_ENTER)) {
			if (currentPauseOption == 0) {
				unPause();
			} else if (currentPauseOption == 1) {
				unPause();
				backgroundMusic.stop();
				Game.getInstance().loadScene(new MainMenuScene("Main"));
			}
		}
	}

	public boolean isPaused() {
		return paused;
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
		double time = Game.getInstance().getTime();
		int x = (int) (time * 150 % 22195);
		Image backgroundSubImage = backgroundImage.getSubimage(x, 0, Main.WIDTH, Main.HEIGHT);
		g.drawImage(backgroundSubImage, 0, 0, null);
		super.render(g);
		g.setColor(Color.WHITE);
		g.setFont(UI_FONT);
		g.drawString("Score: " + score, 50, 70);
		drawHealthBar(g);
		if (paused) {
			Color filter = new Color(0, 0, 0, 100);
			g.setColor(filter);
			g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
			g.setColor(Color.WHITE);
			g.drawString("PAUSED", 800, 350);
			renderScrollingMenus(g, PAUSE_MENU_OPTIONS, currentPauseOption);
		}
	}

	private void drawHealthBar(Graphics g) {
		double healthProportion = player.getCurrentHealth() / player.getMaxHealth();
		Image frame;
		Image bar;
		if (healthProportion < 0.33) {
			frame = RED_HP_FRAME;
			bar = RED_HP_BAR;
		} else if (healthProportion < 0.66) {
			frame = YELLOW_HP_FRAME;
			bar = YELLOW_HP_BAR;
		} else {
			frame = GREEN_HP_FRAME;
			bar = GREEN_HP_BAR;
		}
		int numBars = (int) Math.ceil(healthProportion * 15);
		for (int i = 0; i < numBars; i++) {
			g.drawImage(bar, Main.WIDTH - 391 + i * 23, 43, null);
		}
		g.drawImage(frame, Main.WIDTH - 400, 35, null);
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

	public void endGame() {
		backgroundMusic.stop();
		Game.getInstance().loadScene(new EndingScene(score));
	}

	public int getScore() {
		return score;
	}

}
