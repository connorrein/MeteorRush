package edu.uw.meteorRush.impl.entities;

import java.awt.Graphics;
import java.awt.Image;

import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.scenes.GameScene;

public class AsteroidSmall extends Entity implements DamagableEntity {

	private static final double DAMAGE_AMOUNT = 1.0;
	private static final double MAX_HEALTH = 3;
	private static final double SPEED = 600;
	private static final int WIDTH = 100;
	private static final int HEIGHT = 100;
	private static final int SCORE_VALUE = 20;
	public static final Image ASTEROID_SMALL = ResourceLoader.loadImage("res/Asteroid.png").getScaledInstance(WIDTH,
			HEIGHT, 0);

	private Vector2 velocity;
	private double currentHealth;

	public AsteroidSmall(Vector2 position, Vector2 direction) {
		super(position, new Vector2(WIDTH, HEIGHT));
		this.velocity = direction.clone().normalize().multiply(SPEED);
		currentHealth = MAX_HEALTH;
	}

	@Override
	public void tick() {
		Vector2 position = getPosition();
		position.add(velocity.clone().multiply(Game.getInstance().getDeltaTime()));
		setPosition(position);
	}

	@Override
	public void render(Graphics g) {
		Vector2 position = getPosition();
		g.drawImage(ASTEROID_SMALL, (int) position.getX() - WIDTH / 2, (int) position.getY() - HEIGHT / 2, null);
	}

	@Override
	public void onCollisionEnter(Entity other) {
		if (other instanceof PlayerShip) {
			((PlayerShip) other).damage(DAMAGE_AMOUNT);
			Game.getInstance().getOpenScene().removeObject(this);
		}
	}

	@Override
	public void onCollisionExit(Entity other) {
	}

	@Override
	public void damage(double amount) {
		currentHealth -= amount;
		if (currentHealth <= 0) {
			destroy();
		} else {
			Explosion explosion = new Explosion(getPosition(), new Vector2(50, 50), 0.1);
			Game.getInstance().getOpenScene().addObject(explosion);
		}
	}

	private void destroy() {
		GameScene scene = (GameScene) Game.getInstance().getOpenScene();
		scene.removeObject(this);
		scene.addScore(SCORE_VALUE);
		Explosion explosion = new Explosion(getPosition(), new Vector2(100, 100), 0.2);
		scene.addObject(explosion);
	}

}
