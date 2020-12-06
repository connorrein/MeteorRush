package edu.uw.meteorRush.impl.entities;

import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.Vector2;

public abstract class Drop extends Entity {

	private double startY;
	private double rand;

	public Drop(Vector2 position, Vector2 size) {
		super(position, size);
		startY = position.getY();
		rand = Math.random();
	}

	@Override
	public void tick() {
		double time = Game.getInstance().getTime();
		Vector2 position = getPosition();
		position.setX(position.getX() - 150.0 * Game.getInstance().getDeltaTime());
		position.setY(startY + 15.0 * Math.sin(rand + 2.0 * time));
		setPosition(position);
	}

	@Override
	public void onCollisionEnter(Entity other) {
		if (other instanceof PlayerShip) {
			onPickup((PlayerShip) other);
			Game.getInstance().getOpenScene().removeObject(this);
		}
	}

	@Override
	public void onCollisionExit(Entity other) {
	}

	public abstract void onPickup(PlayerShip player);

}
