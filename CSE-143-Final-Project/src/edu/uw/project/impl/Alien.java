package edu.uw.project.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import edu.uw.project.common.Entity;
import edu.uw.project.common.Game;
import edu.uw.project.common.ResourceLoader;
import edu.uw.project.common.Vector2;

public class Alien extends Entity {

	private BufferedImage sprite;
	private Vector2 position;

	public Alien() {
		sprite = ResourceLoader.loadImage("res/Alien.png");
		position = new Vector2(400, 0);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(sprite, (int) position.getX(), (int) position.getY(), null);
	}

	@Override
	public void tick() {
		position.setX(500 * Math.sin(Game.getInstance().getTime()) + 500);
		position.add(0, 25 * Game.getInstance().getDeltaTime());
	}

}
