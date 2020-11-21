package edu.uw.project.impl;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import edu.uw.project.common.Entity;
import edu.uw.project.common.Game;
import edu.uw.project.common.InputManager;
import edu.uw.project.common.ResourceLoader;
import edu.uw.project.common.Vector2;

public class Player extends Entity {

	private static final double LASER_COOLDOWN = 0.22;
	private static final double SPEED = 750;

	private Vector2 position;
	private double nextFireTime;
	private List<Laser> lasers;

	public Player() {
		position = new Vector2(250, 750);
		lasers = new ArrayList<>();
	}

	@Override
	public void tick() {
		InputManager input = Game.getInstance().getInputManager();
		Vector2 move = new Vector2(input.getHorizontalAxis(), input.getVerticalAxis())
				.multiply(Game.getInstance().getDeltaTime() * -SPEED);
		position.add(move);
		if (input.spaceDown()) {
			double time = Game.getInstance().getTime();
			if (time > nextFireTime) {
				nextFireTime = time + LASER_COOLDOWN;
				fireLaser();
			}
		}
		for (Laser laser : lasers) {
			laser.tick();
		}
	}

	private void fireLaser() {
		ResourceLoader.loadAudioClip("res/laser.wav").start();
		lasers.add(new Laser(position));
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.player, (int) position.getX(), (int) position.getY(), null);
		for (Laser laser : lasers) {
			laser.render(g);
		}
	}

	private class Laser extends Entity {

		private static final double SPEED = 1500;

		private Vector2 position;

		Laser(Vector2 position) {
			this.position = position.clone();
		}

		@Override
		public void tick() {
			position.add(SPEED * Game.getInstance().getDeltaTime(), 0);
		}

		@Override
		public void render(Graphics g) {
			g.drawImage(Assets.alien, (int) position.getX(), (int) position.getY(), null);
		}

	}

}
