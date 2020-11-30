package edu.uw.meteorRush.impl.entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.InputManager;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.Assets;
import edu.uw.meteorRush.impl.Main;

public class PlayerShip extends Entity implements DamagableEntity {

	private static final int PLAYER_WIDTH = 100;
	private static final int PLAYER_HEIGHT = 100;
	private static final double LASER_COOLDOWN = 0.22;
	private static final double SPEED = 600;

	private Image sprite;
	private Image sprite1;
	private Image sprite2;
	private double nextFireTime;

	public PlayerShip(Vector2 position) {
		super(position, new Vector2(PLAYER_WIDTH, PLAYER_HEIGHT));
		sprite1 = Assets.PLAYER_1;
		sprite2 = Assets.PLAYER_2;
		TimerTask animationChange = new TimerTask() {
			@Override
			public void run() {
				if (sprite == sprite1) {
					sprite = sprite2;
				} else {
					sprite = sprite1;
				}
			}
		};
		new Timer().schedule(animationChange, 0, 100);
	}

	@Override
	public void tick() {
		InputManager input = Game.getInstance().getInputManager();
		double horizontal = input.getHorizontalAxis();
		double vertical = input.getVerticalAxis();
		Vector2 move = new Vector2(horizontal, -vertical).multiply(Game.getInstance().getDeltaTime() * SPEED);

		if (vertical < 0) {
			sprite1 = Assets.PLAYER_DOWN_1;
			sprite2 = Assets.PLAYER_DOWN_2;
		} else if (vertical > 0) {
			sprite1 = Assets.PLAYER_UP_1;
			sprite2 = Assets.PLAYER_UP_2;
		} else {
			if (horizontal < 0) {
				sprite1 = Assets.PLAYER_BACKARD_1;
				sprite2 = Assets.PLAYER_BACKARD_2;
			} else if (horizontal > 0) {
				sprite1 = Assets.PLAYER_FORWARD_1;
				sprite2 = Assets.PLAYER_FORWARD_2;
			} else {
				sprite1 = Assets.PLAYER_1;
				sprite2 = Assets.PLAYER_2;
			}
		}

		Vector2 position = getPosition();

		position.add(move);
		position.setX(clamp(position.getX(), 40, Main.WIDTH - 150));
		position.setY(clamp(position.getY(), 60, Main.HEIGHT - 60));
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
		Laser laser = new Laser(position);
		Game.getInstance().getOpenScene().addObject(laser);
		ResourceLoader.loadAudioClip("res/laser.wav").start();
	}

	@Override
	public void render(Graphics g) {
		Vector2 position = getPosition();
		g.drawImage(sprite, (int) (position.getX() - PLAYER_WIDTH / 2.0), (int) (position.getY() - PLAYER_HEIGHT / 2.0),
				null);
	}

	@Override
	public void onCollisionEnter(Entity other) {
	}

	@Override
	public void onCollisionExit(Entity other) {
	}

	private static class Laser extends Projectile {

		private static final double DAMAGE_AMOUNT = 1;
		private static final double SPEED = 1500;
		private static final Vector2 SIZE = new Vector2(50, 50);

		Laser(Vector2 position) {
			super(position, SIZE, new Vector2(SPEED, 0));
		}

		@Override
		public void initialize() {
		}

		@Override
		public void render(Graphics g) {
			Vector2 position = getPosition();
			g.drawImage(Assets.PLAYER_LASER, (int) position.getX(), (int) position.getY(), null);
		}

		@Override
		public void onCollisionEnter(Entity other) {
			if (!(other instanceof PlayerShip) && other instanceof DamagableEntity) {
				((DamagableEntity) other).damage(DAMAGE_AMOUNT);
				Game.getInstance().getOpenScene().removeObject(this);
			} else if (other instanceof Projectile) {
				Game.getInstance().getOpenScene().removeObject(this);
			}
		}

		@Override
		public void onCollisionExit(Entity other) {

		}

	}

	@Override
	public void damage(double amount) {
		Explosion explosion = new Explosion(getPosition(), new Vector2(100, 100), 0.1);
		Game.getInstance().getOpenScene().addObject(explosion);
	}

}
