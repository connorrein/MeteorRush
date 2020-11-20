package edu.uw.project.common;

import java.awt.Graphics;

/**
 * Represents a scene, or stage of the game.
 */
public interface Scene {

	/**
	 * Updates variables and performs non-graphical tasks.
	 */
	public void tick();

	/**
	 * Performs graphical tasks using the given Graphics.
	 */
	public void render(Graphics g);

}
