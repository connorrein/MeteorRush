package edu.uw.meteorRush.common;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;


/**
 * Represents the window in which the game is played in, providing a Canvas for
 * other classes to draw on.
 * 
 * @author Connor Reinholdtsen
 */
public class GameDisplay extends Display {

	/**
	 * How many times we want to buffer images before rendering them to the actual
	 * window.
	 */
	private static final int NUM_BUFFERS = 3;

	protected Canvas canvas;

	/**
	 * Creates and shows a new Display with the given title, width, and height.
	 * 
	 * @param title       the title of the window
	 * @param width       how many pixels wide the window is
	 * @param height      how many pixels tall the window is
	 * @param keyListener keyListener
	 */
	public GameDisplay(String title, int width, int height, KeyListener keyListener) {
		super(title, width, height);
		Dimension dimension = new Dimension(width, height);

		canvas = new Canvas();
		canvas.setPreferredSize(dimension);
		canvas.setMinimumSize(dimension);
		canvas.setMaximumSize(dimension);
		canvas.setFocusable(false);

		window.add(canvas);
		window.pack();
		window.addKeyListener(keyListener);

		canvas.createBufferStrategy(NUM_BUFFERS);
	}

	/**
	 * Returns the BufferStrategy of this Display's Canvas, which can be used to
	 * render graphics onto the window.
	 * 
	 * @return the BufferStrategy of this Display's Canvas
	 */
	public BufferStrategy getBufferStrategy() {
		return canvas.getBufferStrategy();
	}

	/**
	 * Close the window.
	 */
	public void close() {
		window.dispose();
	}

}
