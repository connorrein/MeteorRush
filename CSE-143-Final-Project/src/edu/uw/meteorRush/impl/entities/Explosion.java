package edu.uw.meteorRush.impl.entities;

import java.awt.Graphics;
import java.awt.Image;
import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.Assets;

public class Explosion extends Entity {

	private double endTime;
	private Image sprite;

	public Explosion(Vector2 position, Vector2 size, double lifetime) {
		super(position, size);
		endTime = Game.getInstance().getTime() + lifetime;
		sprite = Assets.EXPLOSION.getScaledInstance((int) size.getX(), (int) size.getY(), 0);
	}

	@Override
	public void tick() {
		if (Game.getInstance().getTime() > endTime) {
			Game.getInstance().getOpenScene().removeObject(this);
		}
	}

	@Override
	public void render(Graphics g) {
		Vector2 position = getPosition();
		Vector2 size = getSize();
		g.drawImage(sprite, (int) (position.getX() - size.getX() / 2.0), (int) (position.getY() - size.getY() / 2.0),
				null);
	}

	@Override
	public void onCollisionEnter(Entity other) {
	}

	@Override
	public void onCollisionExit(Entity other) {
	}

}
