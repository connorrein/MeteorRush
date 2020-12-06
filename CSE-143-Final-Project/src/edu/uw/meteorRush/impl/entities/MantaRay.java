package edu.uw.meteorRush.impl.entities;

import java.awt.Graphics;
import java.awt.Image;

import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.Main;
import edu.uw.meteorRush.impl.scenes.GameScene;

public class MantaRay extends Entity implements DamagableEntity {

	private static final int WIDTH = (int) (44 * 2.8);
	private static final int HEIGHT = (int) (21 * 2.8);
	private static final int MAX_HEALTH = 1;
	private static final double SPEED = 700.0;
	private static final int SCORE_VALUE = 75;
	private static final double HEALTH_DROP_CHANCE = 1;
	private static final double CONTACT_DAMAGE = 1.0;
	private static final double LASER_COOLDOWN = 0.5;
	private static final int LASER_WIDTH = 30;
	private static final int LASER_HEIGHT = 6;
	private static final double LASER_SPEED = 1000.0;
	private static final double LASER_DAMAGE_AMOUNT = 0.5;
	private static final Image SPRITE_1 = ResourceLoader.loadImage("res/images/entities/mantaRay/MantaRay1.png")
			.getScaledInstance(WIDTH, HEIGHT, 0);
	private static final Image SPRITE_2 = ResourceLoader.loadImage("res/images/entities/mantaRay/MantaRay2.png")
			.getScaledInstance(WIDTH, HEIGHT, 0);
	private static final Image LASER = ResourceLoader.loadImage("res/images/entities/mantaRay/MantaRayLaser.png")
			.getScaledInstance(LASER_WIDTH, LASER_HEIGHT, 0);

	private double health;
	private double rand;
	private double nextFireTime;

	public MantaRay(Vector2 position) {
		super(position, new Vector2(WIDTH, HEIGHT));
		health = MAX_HEALTH;
		rand = 6.28318530718 * Math.random();
	}

	@Override
	public void tick() {
		Vector2 position = getPosition();
		position.setY(Main.HEIGHT * 0.42 * Math.sin(rand + 2.0 * Game.getInstance().getTime()) + Main.HEIGHT / 2.0);
		position.add(-SPEED * Game.getInstance().getDeltaTime(), 0);
		setPosition(position);
		double currentTime = Game.getInstance().getTime();
		if (currentTime > nextFireTime) {
			fireLaser();
			nextFireTime = currentTime + LASER_COOLDOWN;
		}
	}

	@Override
	public void render(Graphics g) {
		Vector2 position = getPosition();
		double time = Game.getInstance().getTime();
		Image sprite;
		if (time % 0.3 < 0.15) {
			sprite = SPRITE_1;
		} else {
			sprite = SPRITE_2;
		}
		g.drawImage(sprite, (int) (position.getX() - WIDTH / 2.0), (int) (position.getY() - HEIGHT / 2.0), null);
	}

	private void fireLaser() {
		Laser laser = new Laser(getPosition().add(-80, -10));
		Game.getInstance().getOpenScene().addObject(laser);
	}

	@Override
	public void onCollisionEnter(Entity other) {
		if (other instanceof PlayerShip) {
			((PlayerShip) other).damage(CONTACT_DAMAGE);
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
		scene.addObject(explosion);
		if (Math.random() < HEALTH_DROP_CHANCE) {
			scene.addObject(new HealthDrop(getPosition()));
		}
	}

	private static class Laser extends Projectile {

		public Laser(Vector2 position) {
			super(position, new Vector2(LASER_WIDTH, LASER_HEIGHT), new Vector2(-LASER_SPEED, 0));
		}

		@Override
		public void initialize() {
		}

		@Override
		public void render(Graphics g) {
			Vector2 position = getPosition();
			g.drawImage(LASER, (int) (position.getX() - LASER_WIDTH / 2.0),
					(int) (position.getY() - LASER_HEIGHT / 2.0), null);
		}

		@Override
		public void onCollisionEnter(Entity other) {
			if (other instanceof PlayerShip) {
				((PlayerShip) other).damage(LASER_DAMAGE_AMOUNT);
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