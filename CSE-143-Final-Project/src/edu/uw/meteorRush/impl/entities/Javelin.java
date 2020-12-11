package edu.uw.meteorRush.impl.entities;

import java.awt.Graphics;
import java.awt.Image;

import edu.uw.meteorRush.gameEngine.Entity;
import edu.uw.meteorRush.gameEngine.Game;
import edu.uw.meteorRush.gameEngine.ResourceLoader;
import edu.uw.meteorRush.gameEngine.Scene;
import edu.uw.meteorRush.gameEngine.SceneObject;
import edu.uw.meteorRush.gameEngine.Vector2;
import edu.uw.meteorRush.impl.Main;
import edu.uw.meteorRush.impl.scenes.GameScene;

public class Javelin extends Entity implements DamagableEntity {

	private static final int WIDTH = (int) (92 * 3.5);
	private static final int HEIGHT = (int) (22 * 3.5);
	private static final int MAX_HEALTH = 3;
	private static final double SPEED = 400.0;
	private static final int SCORE_VALUE = 100;
	private static final double HEALTH_DROP_CHANCE = 0.2;
	private static final double BASE_CONTACT_DAMAGE = 2.0;
	private static final double BASE_LASER_DAMAGE = 2;
	private static final double LASER_COOLDOWN = 2;
	private static final int LASER_WIDTH = 90;
	private static final int LASER_HEIGHT = 15;
	private static final double LASER_SPEED = 1000.0;

	private static final Image SPRITE_1 = ResourceLoader.loadImage("res/images/entities/javelin/Javelin1.png")
			.getScaledInstance(WIDTH, HEIGHT, 0);
	private static final Image SPRITE_2 = ResourceLoader.loadImage("res/images/entities/javelin/Javelin2.png")
			.getScaledInstance(WIDTH, HEIGHT, 0);
	private static final Image LASER = ResourceLoader.loadImage("res/images/entities/javelin/JavelinLaser.png")
			.getScaledInstance(LASER_WIDTH, LASER_HEIGHT, 0);

	private double health;
	private double nextFireTime;

	public Javelin(Vector2 position) {
		super(position, new Vector2(WIDTH, HEIGHT));
		health = MAX_HEALTH;
	}

	@Override
	public void tick() {
		Vector2 position = getPosition();
		position.add(-SPEED * Game.getInstance().getDeltaTime(), 0);
		setPosition(position);
		double currentTime = Game.getInstance().getTime();
		if (currentTime > nextFireTime) {
			fireLaser();
			nextFireTime = currentTime + LASER_COOLDOWN;
		}
	}

	private void fireLaser() {
		Laser laser = new Laser(getPosition().add(-90, 20));
		Game.getInstance().getOpenScene().addObject(laser);
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

	@Override
	public void onCollisionEnter(Entity other) {
		if (other instanceof PlayerShip) {
			((PlayerShip) other).damage(BASE_CONTACT_DAMAGE * Main.difficulty.getModifier());
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
		}
	}

	private void destroy() {
		GameScene scene = (GameScene) Game.getInstance().getOpenScene();
		scene.removeObject(this);
		scene.addScore(SCORE_VALUE);
		Explosion explosion = new Explosion(getPosition(), 250, 0.35);
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
			ResourceLoader.loadAudioClip("res/audio/JavelinLaser.wav").start();
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
				((PlayerShip) other).damage(BASE_LASER_DAMAGE * Main.difficulty.getModifier());
				Scene scene = Game.getInstance().getOpenScene();
				scene.removeObject(this);
				scene.addObject(new LaserSpark(getPosition().add(LASER_WIDTH / 2.0, 0)));
			} else if (other instanceof Projectile) {
				Scene scene = Game.getInstance().getOpenScene();
				scene.removeObject(this);
				scene.addObject(new LaserSpark(getPosition().add(LASER_WIDTH / 2.0, 0)));
			}
		}

		@Override
		public void onCollisionExit(Entity other) {
		}
	}

	private static class LaserSpark extends SceneObject {
		private static final double DURATION = 0.1;
		private static final int LASER_SPARK_WIDTH = 50;
		private static final int LASER_SPARK_HEIGHT = 50;
		private static final Image LASER_SPARK = ResourceLoader
				.loadImage("res/images/entities/javelin/JavelinLaserSpark.png")
				.getScaledInstance(LASER_SPARK_WIDTH, LASER_SPARK_HEIGHT, 0);

		private Vector2 position;
		private double deathTime;

		private LaserSpark(Vector2 position) {
			this.position = position;
			deathTime = Game.getInstance().getTime() + DURATION;
		}

		@Override
		public void initialize() {
		}

		@Override
		public void tick() {
			if (Game.getInstance().getTime() > deathTime) {
				Game.getInstance().getOpenScene().removeObject(this);
			}
		}

		@Override
		public void render(Graphics g) {
			int x = (int) (position.getX() - LASER_SPARK_WIDTH / 2.0);
			int y = (int) (position.getY() - LASER_SPARK_HEIGHT / 2.0);
			g.drawImage(LASER_SPARK, x, y, null);
		}

		@Override
		public void dispose() {
		}
	}

}
