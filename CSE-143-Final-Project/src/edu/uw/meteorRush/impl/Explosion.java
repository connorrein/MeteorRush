package edu.uw.meteorRush.impl;

import java.awt.Graphics;
import java.util.TimerTask;

import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.Vector2;

public class Explosion extends Entity {

	private static final long LIFETIME = 1000;

	public Explosion(Vector2 position, Vector2 size) {
		super(position, size);
		Game.getInstance().getTimer().schedule(destroy(), LIFETIME);
	}

	private TimerTask destroy() {
		return new TimerTask() {
			@Override
			public void run() {
				Game.getInstance().getOpenScene().removeEntity(Explosion.this);
			}
		};
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {

	}

	@Override
	public void onCollisionEnter(Entity other) {

	}

	@Override
	public void onCollisionExit(Entity other) {

	}

}
