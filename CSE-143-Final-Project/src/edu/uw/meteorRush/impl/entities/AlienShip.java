package edu.uw.meteorRush.impl.entities;

import java.awt.Graphics;
import java.awt.Image;

import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.scenes.GameScene;

public class AlienShip extends Entity implements DamagableEntity {

	private static final int WIDTH = 150;
	private static final int HEIGHT = 150;
	private static final int MAX_HEALTH = 3;
	private static final int SCORE_VALUE = 100;
	private static final double LASER_COOLDOWN = 2;
	private static final int LASER_WIDTH = 100;
	private static final int LASER_HEIGHT = 20;
	private static final Image SPRITE = ResourceLoader.loadImage("res/AlienShip.png").getScaledInstance(WIDTH, HEIGHT,
			0);
	private static final Image LASER = ResourceLoader.loadImage("res/AlienLaser.png").getScaledInstance(LASER_WIDTH,
			LASER_HEIGHT, 0);

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
		g.drawImage(SPRITE, (int) (position.getX() - WIDTH / 2.0), (int) (position.getY() - HEIGHT / 2.0), null);
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
		AlienLaser laser = new AlienLaser(getPosition().subtract(120, 10));
		Game.getInstance().getOpenScene().addObject(laser);
	}

	@Override
	public void onCollisionEnter(Entity other) {
		if (other instanceof PlayerShip) {
			((PlayerShip) other).damage(2);
			destroy();
		}
	}

	@Override
	public void onCollisionExit(Entity other) {
	}

	@Override
	public void damage(double amount) {
		health -= amount;
		if (health <= 0) {
			destroy();
		} else {
			Explosion explosion = new Explosion(getPosition(), new Vector2(100, 100), 0.2);
			Game.getInstance().getOpenScene().addObject(explosion);
		}
	}

	private void destroy() {
		GameScene scene = (GameScene) Game.getInstance().getOpenScene();
		scene.removeObject(this);
		scene.addScore(SCORE_VALUE);
		Explosion explosion = new Explosion(getPosition(), new Vector2(250, 250), 0.2);
		Game.getInstance().getOpenScene().addObject(explosion);
	}

	private static class AlienLaser extends Projectile {
		private static final Vector2 VELOCITY = new Vector2(-1000, 0);
		private static final double DAMAGE_AMOUNT = 1;

		public AlienLaser(Vector2 position) {
			super(position, new Vector2(LASER_WIDTH, LASER_HEIGHT), VELOCITY);
		}

		@Override
		public void initialize() {
		}

		@Override
		public void render(Graphics g) {
			Vector2 position = getPosition();
			g.drawImage(LASER, (int) position.getX(), (int) position.getY(), null);
		}

		@Override
		public void onCollisionEnter(Entity other) {
			if (other instanceof PlayerShip) {
				((PlayerShip) other).damage(DAMAGE_AMOUNT);
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
