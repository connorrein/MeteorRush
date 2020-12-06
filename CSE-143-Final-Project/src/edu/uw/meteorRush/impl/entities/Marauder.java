package edu.uw.meteorRush.impl.entities;

import java.awt.Graphics;
import java.awt.Image;

import javax.sound.sampled.Clip;

import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.Main;
import edu.uw.meteorRush.impl.scenes.GameScene;

public class Marauder extends Entity implements DamagableEntity {

	private static final int WIDTH = 250;
	private static final int HEIGHT = 250;
	private static final int MAX_HEALTH = 8;
	private static final double SPEED = 200.0;
	private static final int SCORE_VALUE = 300;
	private static final double HEALTH_DROP_CHANCE = 0.2;
	private static final double BASE_CONTACT_DAMAGE = 2.0;
	private static final double LASER_LIFETIME = 2.9;
	private static final double LASER_COOLDOWN = 6.0;
	private static final double LASER_DAMAGE_AMOUNT = 4.0;
	private static final int LASER_WIDTH = 2000;
	private static final int LASER_HEIGHT = 30;

	private static final Image SPRITE_1 = ResourceLoader.loadImage("res/images/entities/marauder/Marauder1.png")
			.getScaledInstance(WIDTH, HEIGHT, 0);
	private static final Image SPRITE_2 = ResourceLoader.loadImage("res/images/entities/marauder/Marauder2.png")
			.getScaledInstance(WIDTH, HEIGHT, 0);
	private static final Image LASER = ResourceLoader.loadImage("res/images/entities/marauder/MarauderLaser.png")
			.getScaledInstance(LASER_WIDTH, LASER_HEIGHT, 0);

	private double health;
	private double nextFireTime;
	private Laser currrentLaser;

	public Marauder(Vector2 position) {
		super(position, new Vector2(WIDTH, HEIGHT));
		health = MAX_HEALTH;
		nextFireTime = Game.getInstance().getTime() + 2;
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
		currrentLaser = new Laser(getPosition().add(-LASER_WIDTH / 2.0, -10));
		Game.getInstance().getOpenScene().addObject(currrentLaser);
	}

	@Override
	public void render(Graphics g) {
		Vector2 position = getPosition();
		g.drawImage(SPRITE_1, (int) (position.getX() - WIDTH / 2.0), (int) (position.getY() - HEIGHT / 2.0), null);
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
		if (scene.getObjects().contains(currrentLaser)) {
			scene.removeObject(currrentLaser);
		}
		scene.addScore(SCORE_VALUE);
		Explosion explosion = new Explosion(getPosition(), new Vector2(250, 250), 0.2);
		scene.addObject(explosion);
		if (Math.random() < HEALTH_DROP_CHANCE) {
			scene.addObject(new HealthDrop(getPosition()));
		}
	}

	@Override
	public void onCollisionEnter(Entity other) {
		if (other instanceof PlayerShip) {
			((PlayerShip) other).damage(Main.difficulty.getModifier() * BASE_CONTACT_DAMAGE);
			destroy();
		}
	}

	@Override
	public void onCollisionExit(Entity other) {
	}

	private static class Laser extends Entity {

		private boolean collidingWithPlayer;
		private double deathTime;
		private Clip sound;

		public Laser(Vector2 position) {
			super(position, new Vector2(LASER_WIDTH, LASER_HEIGHT));
			collidingWithPlayer = false;
			deathTime = Game.getInstance().getTime() + LASER_LIFETIME;
			sound = ResourceLoader.loadAudioClip("res/audio/MarauderLaser.wav");
			sound.start();
		}

		@Override
		public void onCollisionEnter(Entity other) {
			if (other instanceof PlayerShip) {
				collidingWithPlayer = true;
			}
		}

		@Override
		public void onCollisionExit(Entity other) {
			collidingWithPlayer = false;
		}

		@Override
		public void tick() {
			GameScene scene = (GameScene) Game.getInstance().getOpenScene();
			if (Game.getInstance().getTime() > deathTime) {
				scene.removeObject(this);
				return;
			}
			Vector2 position = getPosition();
			position.add(-SPEED * Game.getInstance().getDeltaTime(), 0);
			setPosition(position);
			if (collidingWithPlayer) {
				PlayerShip player = scene.getPlayer();
				player.damage(LASER_DAMAGE_AMOUNT * Game.getInstance().getDeltaTime());
			}
		}

		@Override
		public void render(Graphics g) {
			Vector2 position = getPosition();
			g.drawImage(LASER, (int) (position.getX() - LASER_WIDTH / 2.0),
					(int) (position.getY() - LASER_HEIGHT / 2.0), null);
		}

		@Override
		public void dispose() {
			super.dispose();
			sound.stop();
		}
	}
}
