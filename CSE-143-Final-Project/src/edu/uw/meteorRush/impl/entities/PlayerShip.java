package edu.uw.meteorRush.impl.entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.InputManager;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.Main;
import edu.uw.meteorRush.impl.scenes.GameScene;

public class PlayerShip extends Entity implements DamagableEntity {

	private static final int WIDTH = 147;
	private static final int HEIGHT = 70;
	private static final double LASER_COOLDOWN = 0.22;
	private static final double SPEED = 600;
	private static final double MAX_HEALTH = 20;

	private static final double LASER_DAMAGE_AMOUNT = 1;
	private static final double LASER_SPEED = 1500;
	private static final double LASER_WIDTH = 50;
	private static final double LASER_HEIGHT = 10;

	public static final Image PLAYER_1 = ResourceLoader.loadImage("res/images/entities/player/Player1.png")
			.getScaledInstance(WIDTH, HEIGHT, 0);
	public static final Image PLAYER_2 = ResourceLoader.loadImage("res/images/entities/player/Player2.png")
			.getScaledInstance(WIDTH, HEIGHT, 0);
	public static final Image PLAYER_BACKARD_1 = ResourceLoader
			.loadImage("res/images/entities/player/PlayerBackward1.png").getScaledInstance(WIDTH, HEIGHT, 0);
	public static final Image PLAYER_BACKARD_2 = ResourceLoader
			.loadImage("res/images/entities/player/PlayerBackward2.png").getScaledInstance(WIDTH, HEIGHT, 0);
	public static final Image PLAYER_DOWN_1 = ResourceLoader.loadImage("res/images/entities/player/PlayerDown1.png")
			.getScaledInstance(WIDTH, HEIGHT, 0);
	public static final Image PLAYER_DOWN_2 = ResourceLoader.loadImage("res/images/entities/player/PlayerDown2.png")
			.getScaledInstance(WIDTH, HEIGHT, 0);
	public static final Image PLAYER_FORWARD_1 = ResourceLoader
			.loadImage("res/images/entities/player/PlayerForward1.png").getScaledInstance(WIDTH, HEIGHT, 0);
	public static final Image PLAYER_FORWARD_2 = ResourceLoader
			.loadImage("res/images/entities/player/PlayerForward2.png").getScaledInstance(WIDTH, HEIGHT, 0);
	public static final Image PLAYER_UP_1 = ResourceLoader.loadImage("res/images/entities/player/PlayerUp1.png")
			.getScaledInstance(WIDTH, HEIGHT, 0);
	public static final Image PLAYER_UP_2 = ResourceLoader.loadImage("res/images/entities/player/PlayerUp2.png")
			.getScaledInstance(WIDTH, HEIGHT, 0);
	public static final Image PLAYER_LASER = ResourceLoader.loadImage("res/images/entities/player/PlayerLaser.png")
			.getScaledInstance(50, 10, 0);

	private Image sprite1;
	private Image sprite2;
	private double currentHealth;
	private double nextFireTime;

	public PlayerShip(Vector2 position) {
		super(position, new Vector2(WIDTH, HEIGHT));
		sprite1 = PLAYER_1;
		sprite2 = PLAYER_2;
		currentHealth = MAX_HEALTH;
	}

	@Override
	public void tick() {
		if (((GameScene) Game.getInstance().getOpenScene()).isPaused()) {
			return;
		}
		InputManager input = Game.getInstance().getInputManager();
		double horizontal = input.getHorizontalAxis();
		double vertical = input.getVerticalAxis();
		Vector2 move = new Vector2(horizontal, -vertical).multiply(Game.getInstance().getDeltaTime() * SPEED);

		if (vertical < 0) {
			sprite1 = PLAYER_DOWN_1;
			sprite2 = PLAYER_DOWN_2;
		} else if (vertical > 0) {
			sprite1 = PLAYER_UP_1;
			sprite2 = PLAYER_UP_2;
		} else {
			if (horizontal < 0) {
				sprite1 = PLAYER_BACKARD_1;
				sprite2 = PLAYER_BACKARD_2;
			} else if (horizontal > 0) {
				sprite1 = PLAYER_FORWARD_1;
				sprite2 = PLAYER_FORWARD_2;
			} else {
				sprite1 = PLAYER_1;
				sprite2 = PLAYER_2;
			}
		}

		Vector2 position = getPosition();

		position.add(move);
		position.setX(clamp(position.getX(), 70, Main.WIDTH - 100));
		position.setY(clamp(position.getY(), 50, Main.HEIGHT - 40));
		setPosition(position);
		if (input.getKey(KeyEvent.VK_SPACE)) {
			double time = Game.getInstance().getTime();
			if (time > nextFireTime) {
				nextFireTime = time + LASER_COOLDOWN;
				fireLaser();
			}
		}
	}

	private double clamp(double n, double min, double max) {
		if (n < min) {
			return min;
		} else if (n > max) {
			return max;
		} else {
			return n;
		}
	}

	private void fireLaser() {
		Vector2 position = getPosition();
		Laser laser = new Laser(position.add(WIDTH / 2.0, 0.0));
		Game.getInstance().getOpenScene().addObject(laser);
		ResourceLoader.loadAudioClip("res/audio/PlayerLaser.wav").start();
	}

	@Override
	public void render(Graphics g) {
		Vector2 position = getPosition();
		Image sprite;
		double time = Game.getInstance().getTime();
		if (time % 0.2 < 0.1) {
			sprite = sprite1;
		} else {
			sprite = sprite2;
		}
		g.drawImage(sprite, (int) (position.getX() - WIDTH / 2.0), (int) (position.getY() - HEIGHT / 2.0), null);
	}

	public double getCurrentHealth() {
		return currentHealth;
	}

	public double getMaxHealth() {
		return MAX_HEALTH;
	}

	@Override
	public void damage(double amount) {
		currentHealth -= amount;
		GameScene scene = (GameScene) Game.getInstance().getOpenScene();
		if (currentHealth <= 0) {
			destroy();
			scene.endGame();
		}
		Explosion explosion = new Explosion(getPosition(), new Vector2(100, 100), 0.1);
		scene.addObject(explosion);
	}

	public void heal(double healAmount) {
		currentHealth += healAmount;
		if (currentHealth > MAX_HEALTH) {
			currentHealth = MAX_HEALTH;
		}
	}

	private void destroy() {
		Game.getInstance().getOpenScene().removeObject(this);
	}

	@Override
	public void onCollisionEnter(Entity other) {
	}

	@Override
	public void onCollisionExit(Entity other) {
	}

	private static class Laser extends Projectile {
		Laser(Vector2 position) {
			super(position, new Vector2(LASER_WIDTH, LASER_HEIGHT), new Vector2(LASER_SPEED, 0));
		}

		@Override
		public void render(Graphics g) {
			Vector2 position = getPosition();
			g.drawImage(PLAYER_LASER, (int) position.getX(), (int) position.getY(), null);
		}

		@Override
		public void onCollisionEnter(Entity other) {
			if (!(other instanceof PlayerShip) && other instanceof DamagableEntity) {
				((DamagableEntity) other).damage(LASER_DAMAGE_AMOUNT);
				Game.getInstance().getOpenScene().removeObject(this);
			} else if (other instanceof Projectile) {
				Game.getInstance().getOpenScene().removeObject(this);
			}
		}

		@Override
		public void onCollisionExit(Entity other) {
		}
	}

}
