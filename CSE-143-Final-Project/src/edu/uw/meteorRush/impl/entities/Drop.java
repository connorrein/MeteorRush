package edu.uw.meteorRush.impl.entities;

import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.Vector2;

public abstract class Drop extends Entity {

	public Drop(Vector2 position, Vector2 size) {
		super(position, size);
	}

	@Override
	public void onCollisionEnter(Entity other) {
		if (other instanceof PlayerShip) {
			onPickup();
			Game.getInstance().getOpenScene().removeObject(this);
		}
	}

	@Override
	public void onCollisionExit(Entity other) {
	}

	@Override
	public void tick() {
	}

	public abstract void onPickup();

}
