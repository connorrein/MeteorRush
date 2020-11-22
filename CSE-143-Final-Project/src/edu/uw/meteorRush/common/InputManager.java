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
		double horizontal = 0;
		if (keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) {
			horizontal++;
		}
		if (keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) {
			horizontal--;
		}
		return horizontal;
	}

	public double getVerticalAxis() {
		double vertical = 0;
		if (keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) {
			vertical++;
		}
		if (keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]) {
			vertical--;
		}
		return vertical;
	}
	
	public boolean spaceDown() {
		return keys[KeyEvent.VK_SPACE];
	}

}
