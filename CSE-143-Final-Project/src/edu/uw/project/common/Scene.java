package edu.uw.project.common;

import java.awt.Graphics;

/**
 * Represents a scene, or stage of the game.
 */
public abstract class Scene {

	/**
	 * Updates variables and performs non-graphical tasks.
	 */
	public abstract void tick();

	/**
	 * Performs graphical tasks using the given Graphics.
	 */
	public abstract void render(Graphics g);

}
