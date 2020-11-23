package edu.uw.meteorRush.common;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a scene, or stage of the game.
 */
public abstract class Scene {

	private List<Entity> entities;

	public Scene() {
		entities = new ArrayList<>();
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public void removeEntity(Entity entity) {
		entities.remove(entity);
		entity.dispose();
	}

	public List<Entity> getEntities() {
		return new ArrayList<>(entities);
	}

	public abstract void initialize();

	/**
	 * Updates variables and performs non-graphical tasks.
	 */
	public void tick() {
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			entity.tick();
		}
	}

	/**
	 * Performs graphical tasks using the given Graphics.
	 */
	public void render(Graphics g) {
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			entity.render(g);
		}
	}

	void dispose() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).dispose();
		}
	}

}
