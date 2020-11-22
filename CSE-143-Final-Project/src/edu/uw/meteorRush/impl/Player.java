package edu.uw.meteorRush.impl;

import java.awt.Graphics;
import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.InputManager;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Scene;
import edu.uw.meteorRush.common.Vector2;

public class Player extends Entity {

	private static final double LASER_COOLDOWN = 0.22;
	private static final double SPEED = 600;

	private double nextFireTime;

	public Player() {
		super(new Vector2(250, 250), new Vector2(50, 50));
	}

	@Override
	public void tick() {
		InputManager input = Game.getInstance().getInputManager();
		Vector2 move = new Vector2(input.getHorizontalAxis(), input.getVerticalAxis())
				.multiply(Game.getInstance().getDeltaTime() * -SPEED);
		Vector2 position = getPosition();

		position.add(move);
		position.setX(clamp(position.getX(), 25, 400));
		position.setY(clamp(position.getY(), 25, 700));
		setPosition(position);
		if (input.spaceDown()) {
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
		Game.getInstance().getOpenScene().addEntity(laser);
		ResourceLoader.loadAudioClip("res/laser.wav").start();
	}

	@Override
	public void render(Graphics g) {
		Vector2 position = getPosition();
		g.drawImage(Assets.PLAYER, (int) position.getX(), (int) position.getY(), null);
	}

	@Override
	public void onCollisionEnter(Entity other) {

	}

	@Override
	public void onCollisionExit(Entity other) {

	}

	private static class Laser extends Entity {

		private static final double SPEED = 1500;
		private static final Vector2 SIZE = new Vector2(50, 50);

		Laser(Vector2 position) {
			super(position, SIZE);
		}

		@Override
		public void tick() {
			Vector2 position = getPosition();
			position.add(SPEED * Game.getInstance().getDeltaTime(), 0.0);
			setPosition(position);
		}

		@Override
		public void render(Graphics g) {
			Vector2 position = getPosition();
			g.drawImage(Assets.PLAYER_LASER, (int) position.getX(), (int) position.getY(), null);
		}

		@Override
		public void onCollisionEnter(Entity other) {
			if (!(other instanceof Player)) {
				Scene scene = Game.getInstance().getOpenScene();
				scene.removeEntity(other);
				scene.removeEntity(this);
			}
		}

		@Override
		public void onCollisionExit(Entity other) {

		}

	}

}
