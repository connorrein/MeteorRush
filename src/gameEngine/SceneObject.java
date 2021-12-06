package gameEngine;

import java.awt.Graphics;

/**
 * Represents an object that can be added to a Scene using
 * Scene.add(SceneObject). Once added to a Scene, initialize will be invoked on
 * SceneObjects. Then, tick() and render(Graphics) will be repeatedly invoked by
 * the game loop. Once a SceneObject is removed from a Scene, dispose() will be
 * invoked.
 * 
 * @author Connor Reinholdtsen
 */
public abstract class SceneObject {
    /**
     * Invoked when this SceneObject is added to a Scene.
     */
    public abstract void initialize();

    /**
     * Invoked when the parent Scene ticks.
     */
    public abstract void tick();

    /**
     * Invoked when the parent Scene renders.
     * 
     * @param g the Graphics used to render this SceneObject to the game's display
     */
    public abstract void render(Graphics g);

    /**
     * Invoked when this SceneObject is removed from a Scene.
     */
    public abstract void dispose();
}
