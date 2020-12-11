package edu.uw.meteorRush.gameEngine;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a scene that can be opened by the Game. Scenes are made up of
 * SceneObjects such as Entities.
 * 
 * @author Connor Reinholdtsen
 */
public abstract class Scene {

	private List<SceneObject> objects;

	/**
	 * Constructs a new empty scene
	 */
	public Scene() {
		objects = new ArrayList<>();
	}

	/**
	 * Adds the given SceneObject to this Scene, invoking intialize() on it. Once
	 * added, this Scene will invoke tick() and render(Graphics) on it according to
	 * the game loop.
	 * 
	 * @param object the SceneObject to be added
	 */
	public final void addObject(SceneObject object) {
		objects.add(object);
		object.initialize();
	}

	/**
	 * Removes the given SceneObject from this Scene, invoking dispose() on it. Once
	 * removed, this scene will no longer invoke tick() and render(Graphics) on it
	 * according to the game loop.
	 * 
	 * @param object the SceneObject to be removed
	 */
	public final void removeObject(SceneObject object) {
		objects.remove(object);
		object.dispose();
	}

	/**
	 * Returns all of the SceneObjects in this Scene.
	 * 
	 * @return a List of SceneObjects
	 */
	public final List<SceneObject> getObjects() {
		return new ArrayList<>(objects);
	}

	/**
	 * Invoked by the Game when it loads this Scene.
	 */
	public abstract void initialize();

	/**
	 * Invoked by the Game according to the game loop. Updates variables and
	 * performs non-graphical tasks. Can be overridden in subclasses of Scene, but
	 * overriding methods must invoke super.
	 */
	public void tick() {
		for (int i = 0; i < objects.size(); i++) {
			SceneObject object = objects.get(i);
			object.tick();
		}
	}

	/**
	 * Performs graphical tasks using the given Graphics. Can be overridden in
	 * subclasses of Scene, but overriding methods must invoke super.
	 * 
	 * @param g the Graphics used to render this scene
	 */
	public void render(Graphics g) {
		for (int i = 0; i < objects.size(); i++) {
			SceneObject object = objects.get(i);
			object.render(g);
		}
	}

	/**
	 * Invoked when the Game unloads this Scene. Can be overridden in subclasses of
	 * Scene, but overriding methods must invoke super.
	 */
	public void dispose() {
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).dispose();
		}
	}

}
