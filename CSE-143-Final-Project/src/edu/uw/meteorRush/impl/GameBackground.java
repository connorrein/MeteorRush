package edu.uw.meteorRush.impl;

import java.awt.Graphics;
import java.awt.Image;

import edu.uw.meteorRush.common.Entity;
import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Vector2;

public class GameBackground extends Entity {

	public static final Image BACKGROUND_IMAGE = ResourceLoader.loadImage("res/GameBackground.jpg");

	public GameBackground(Vector2 position, Vector2 size) {
		super(position, size);
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(BACKGROUND_IMAGE, 0, 0, null);
	}

	@Override
	public void onCollisionEnter(Entity other) {
	}

	@Override
	public void onCollisionExit(Entity other) {
		//Game.getInstance().getOpenScene().removeEntity(other);
	}

}
