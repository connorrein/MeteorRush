package edu.uw.meteorRush.common;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager {

	private static boolean[] keys;

	public InputManager() {
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

	public boolean keyDown(int keyCode) {
		return keys[keyCode];
	}

	public boolean spaceDown() {
		return keyDown(KeyEvent.VK_SPACE);
	}

	public boolean escDown() {
		return keyDown(KeyEvent.VK_ESCAPE);
	}

}
