package edu.uw.meteorRush.common;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class for getting user-input for the game.
 * 
 * @author Connor Reinholdtsen
 */
public class InputManager {

	private static boolean[] keys;

	InputManager() {
		keys = new boolean[256];
	}

	KeyListener getKeyListener() {
		return new KeyListener() {
			@Override
			public void keyPressed(KeyEvent event) {
				keys[event.getKeyCode()] = true;
			}

			@Override
			public void keyReleased(KeyEvent event) {
				keys[event.getKeyCode()] = false;
			}

			@Override
			public void keyTyped(KeyEvent event) {
			}
		};
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
		if (keyDown(KeyEvent.VK_A) || keyDown(KeyEvent.VK_LEFT)) {
			horizontal--;
		}
		if (keyDown(KeyEvent.VK_D) || keyDown(KeyEvent.VK_RIGHT)) {
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
		if (keyDown(KeyEvent.VK_S) || keyDown(KeyEvent.VK_DOWN)) {
			vertical--;
		}
		if (keyDown(KeyEvent.VK_W) || keyDown(KeyEvent.VK_UP)) {
			vertical++;
		}
		return vertical;
	}

	/**
	 * Returns whether the key with the given key code is pressed by the user. Key
	 * codes for keys can be accessed using the KeyEvent class. For instance, to see
	 * if the ESC key is pressed down, use keyDown(KeyEvent.VK_ESCAPE).
	 * 
	 * @param keyCode the key code of the key
	 * @return
	 */
	public boolean keyDown(int keyCode) {
		return keys[keyCode];
	}

}
