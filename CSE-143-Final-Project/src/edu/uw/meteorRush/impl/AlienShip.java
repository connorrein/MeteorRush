package edu.uw.meteorRush.impl;

import java.awt.Graphics;

import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.Vector2;

public class AlienShip extends Entity implements DamagableEntity {

	private static final int WIDTH = 250;
	private static final int HEIGHT = 250;
	private static final int MAX_HEALTH = 3;
	private static final int SCORE_VALUE = 100;
	private static final double LASER_COOLDOWN = 3;

	private double health;
	private double rand;
	private double nextFireTime;

	public AlienShip(Vector2 position) {
		super(position, new Vector2(WIDTH, HEIGHT));
		health = MAX_HEALTH;
		rand = 6.28318530718 * Math.random();
	}

	@Override
	public void render(Graphics g) {
		Vector2 position = getPosition();
		g.drawImage(Assets.ALIEN, (int) (position.getX() - WIDTH / 2.0), (int) (position.getY() - HEIGHT / 2.0), null);
	}

	@Override
	public void tick() {
		Vector2 position = getPosition();
		position.setY(250 * Math.sin(rand + 2 * Game.getInstance().getTime()) + 425);
		position.add(-300 * Game.getInstance().getDeltaTime(), 0);
		setPosition(position);
		double currentTime = Game.getInstance().getTime();
		if (currentTime > nextFireTime) {
			fireLaser();
			nextFireTime = currentTime + LASER_COOLDOWN;
		}
	}

	private void fireLaser() {
		AlienLaser laser = new AlienLaser(getPosition());
		Game.getInstance().getOpenScene().addEntity(laser);
	}

	@Override
	public void onCollisionEnter(Entity other) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCollisionExit(Entity other) {
		// TODO Auto-generated method stub

	}

	@Override
	public void damage(double amount) {
		health -= amount;
		if (health <= 0) {
			destroy();
		}
	}

	private void destroy() {
		GameScene scene = (GameScene) Game.getInstance().getOpenScene();
		scene.removeEntity(this);
		scene.addScore(SCORE_VALUE);
	}

	private static class AlienLaser extends Projectile {

		private static final Vector2 SIZE = new Vector2(50, 50);
		private static final Vector2 VELOCITY = new Vector2(-1000, 0);

		public AlienLaser(Vector2 position) {
			super(position, SIZE, VELOCITY);
		}

		@Override
		public void render(Graphics g) {
			Vector2 position = getPosition();
			g.drawImage(Assets.PLAYER_LASER, (int) position.getX(), (int) position.getY(), null);
		}

		@Override
		public void onCollisionEnter(Entity other) {
			if (other instanceof PlayerShip) {
				System.out.println("HIT");
			} else if (other instanceof Projectile) {
				Game.getInstance().getOpenScene().removeEntity(this);
			}
		}

		@Override
		public void onCollisionExit(Entity other) {
		}

	}

}
