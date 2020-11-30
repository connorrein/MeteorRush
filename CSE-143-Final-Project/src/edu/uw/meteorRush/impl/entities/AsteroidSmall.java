package edu.uw.meteorRush.impl.entities;

import java.awt.Graphics;

import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.Assets;

public class AsteroidSmall extends Entity implements DamagableEntity {

	private static final double DAMAGE_AMOUNT = 1.0;
	private static final double MAX_HEALTH = 2;
	public static final double SPEED = 600;
	private static final Vector2 SIZE = new Vector2(100, 100);

	private Vector2 velocity;
	private double currentHealth;

	public AsteroidSmall(Vector2 position, Vector2 direction) {
		super(position, SIZE);
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
		g.drawImage(Assets.ALIEN, (int) position.getX(), (int) position.getY(), null);
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
		if (currentHealth < 0) {
			destroy();
		}
	}

	private void destroy() {
		Game.getInstance().getOpenScene().removeObject(this);
	}

}
