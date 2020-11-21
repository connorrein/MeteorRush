package edu.uw.project.impl;

import java.awt.Graphics;

import edu.uw.project.common.Entity;
import edu.uw.project.common.Game;
import edu.uw.project.common.InputManager;
import edu.uw.project.common.Vector2;

public class Player extends Entity {

	private Vector2 position;

	public Player() {
		position = new Vector2(500, 500);
	}

	@Override
	public void tick() {
		InputManager input = Game.getInstance().getInputManager();
		Vector2 move = new Vector2(input.getHorizontalAxis(), input.getVerticalAxis())
				.multiply(Game.getInstance().getDeltaTime() * 500);
		position.add(move);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.alien, (int) position.getX(), (int) position.getY(), null);
	}

}
