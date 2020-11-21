package edu.uw.project.common;

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
		if (keys[KeyEvent.VK_A]) {
			horizontal++;
		}
		if (keys[KeyEvent.VK_D]) {
			horizontal--;
		}
		return horizontal;
	}

	public double getVerticalAxis() {
		double vertical = 0;
		if (keys[KeyEvent.VK_W]) {
			vertical++;
		}
		if (keys[KeyEvent.VK_S]) {
			vertical--;
		}
		return vertical;
	}

}
