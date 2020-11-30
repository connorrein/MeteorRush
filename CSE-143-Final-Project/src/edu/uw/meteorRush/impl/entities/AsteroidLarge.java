package edu.uw.meteorRush.impl.entities;

import java.awt.Graphics;
import java.awt.Image;

import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Vector2;

public class AsteroidLarge extends Entity implements DamagableEntity {

	private static final double DAMAGE_AMOUNT = 3.0;
	private static final double MAX_HEALTH = 5;
	private static final double SPEED = 300;
	private static final int WIDTH = 200;
	private static final int HEIGHT = 200;
	public static final Image ASTEROID_LARGE = ResourceLoader.loadImage("res/Asteroid.png").getScaledInstance(WIDTH,
			HEIGHT, 0);

	private Vector2 velocity;
	private double currentHealth;

	public AsteroidLarge(Vector2 position, Vector2 direction) {
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
		g.drawImage(ASTEROID_LARGE, (int) position.getX() - WIDTH / 2, (int) position.getY() - HEIGHT / 2, null);
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
		}
	}

	private void destroy() {
		Game.getInstance().getOpenScene().removeObject(this);
		Vector2 position = getPosition();
		Game.getInstance().getOpenScene().addObject(new AsteroidSmall(position, velocity));
		velocity.rotate(-Math.toRadians(30));
		Game.getInstance().getOpenScene().addObject(new AsteroidSmall(position, velocity));
		velocity.rotate(Math.toRadians(60));
		Game.getInstance().getOpenScene().addObject(new AsteroidSmall(position, velocity));
	}

}
