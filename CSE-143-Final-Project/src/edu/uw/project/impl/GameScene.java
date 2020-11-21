package edu.uw.project.impl;

import java.awt.Graphics;

import edu.uw.project.common.Scene;

public class GameScene extends Scene {

	private Player player;
	private boolean initialized;

	@Override
	public void tick() {
		if (!initialized) {
			initialized = true;
			player = new Player();
		}
		player.tick();
	}

	@Override
	public void render(Graphics g) {
		player.render(g);
	}

}
