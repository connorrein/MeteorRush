package gameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for getting user-input for the game.
 * 
 * @author Connor Reinholdtsen
 */
public class InputManager {
    private static boolean[] keys;
    private Set<Integer> keysDownNow;
    private Set<Integer> keysDownNextUpdate;

    InputManager() {
	keys = new boolean[256];
	keysDownNow = new HashSet<>();
	keysDownNextUpdate = new HashSet<>();
    }

    KeyListener getKeyListener() {
	return new KeyListener() {
	    @Override
	    public void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if (!getKey(keyCode)) {
		    keysDownNextUpdate.add(keyCode);
		}
		keys[keyCode] = true;
	    }

	    @Override
	    public void keyReleased(KeyEvent event) {
		int keyCode = event.getKeyCode();
		keys[keyCode] = false;
	    }

	    @Override
	    public void keyTyped(KeyEvent event) {
	    }
	};
    }

    /**
     * Called at the end of a game loop update to update keys down.
     */
    void tick() {
	keysDownNow.clear();
	keysDownNow.addAll(keysDownNextUpdate);
	keysDownNextUpdate.clear();
    }

    /**
     * Returns whether the key with the given key code is pressed by the user. Key
     * codes for keys can be accessed using the KeyEvent class. For instance, to see
     * if the ESC key is pressed down, use getKey(KeyEvent.VK_ESCAPE).
     * 
     * @param keyCode the key code of the key
     * @return whether they key is pressed
     */
    public boolean getKey(int keyCode) {
	return keys[keyCode];
    }

    /**
     * Returns whether the key with the given key code was pressed by the user in
     * the last iteration of the game loop, but not immediately before that. Key
     * codes for keys can be accessed using the KeyEvent class. For instance, to see
     * if the ESC key was just pressed down the last update, use
     * getKeyDown(KeyEvent.VK_ESCAPE).
     * 
     * @param keyCode the key code of the key
     * @return whether the key was pressed last update
     */
    public boolean getKeyDown(int keyCode) {
	return keysDownNow.contains(keyCode);
    }

    /**
     * Returns a double ranging from [-1.0, 1.0] representing user-input on the
     * horizontal axis. A value of -1.0 would represent the player wanting to move
     * left. A value of 1.0 would represent the player wanting to move right. A
     * value of 0.0 would represent no horizontal movement.
     * 
     * @return a double ranging from [-1.0, 1.0]
     */
    public double getHorizontalAxis() {
	double horizontal = 0.0;
	if (getKey(KeyEvent.VK_A) || getKey(KeyEvent.VK_LEFT)) {
	    horizontal--;
	}
	if (getKey(KeyEvent.VK_D) || getKey(KeyEvent.VK_RIGHT)) {
	    horizontal++;
	}
	return horizontal;
    }

    /**
     * Returns a double ranging from [-1.0, 1.0] representing user-input on the
     * vertical axis. A value of -1.0 would represent the player wanting to move
     * down. A value of 1.0 would represent the player wanting to move up. A value
     * of 0.0 would represent no vertical movement.
     * 
     * @return a double ranging from [-1.0, 1.0]
     */
    public double getVerticalAxis() {
	double vertical = 0.0;
	if (getKey(KeyEvent.VK_S) || getKey(KeyEvent.VK_DOWN)) {
	    vertical--;
	}
	if (getKey(KeyEvent.VK_W) || getKey(KeyEvent.VK_UP)) {
	    vertical++;
	}
	return vertical;
    }
}
