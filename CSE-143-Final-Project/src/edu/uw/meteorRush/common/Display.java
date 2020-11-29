package edu.uw.meteorRush.common;

import edu.uw.meteorRush.menus.GraphicsMenu;
import edu.uw.meteorRush.menus.GraphicsMenuItem;
import edu.uw.meteorRush.menus.GraphicsMenuListener;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * Represents the window in which the game is played in, providing a Canvas for
 * other classes to draw on.
 * 
 * @author Connor Reinholdtsen
 */
public class Display {

	/**
	 * How many times we want to buffer images before rendering them to the actual
	 * window.
	 */
	private static final int NUM_BUFFERS = 3;

	private JFrame window;
	private JFrame menu;
	private Canvas canvas;

	/**
	 * Creates and shows a new Display with the given title, width, and height.
	 * 
	 * @param title       the title of the window
	 * @param width       how many pixels wide the window is
	 * @param height      how many pixels tall the window is
	 * @param keyListener
	 */
	public Display(String title, int width, int height, KeyListener keyListener) {
		Dimension dimension = new Dimension(width, height);

		window = new JFrame(title);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(dimension);
		window.setResizable(false);
		window.setLocationRelativeTo(null);

		menu = new JFrame("Pause Menu");
		menu.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		menu.setSize(dimension);
		menu.setResizable(false);
		menu.setLocationRelativeTo(null);
		menu.setVisible(true);

		initializeMenu(menu);

		canvas = new Canvas();
		canvas.setPreferredSize(dimension);
		canvas.setMinimumSize(dimension);
		canvas.setMaximumSize(dimension);
		canvas.setFocusable(false);

		window.add(canvas);
		window.pack();
		window.addKeyListener(keyListener);

		canvas.createBufferStrategy(NUM_BUFFERS);
		window.setVisible(true);
	}

	private void initializeMenu(JFrame frame) {
		frame.setSize(900, 500);
		ArrayList<GraphicsMenuItem> menuItems = new ArrayList<GraphicsMenuItem>();
		GraphicsMenuItem item = new GraphicsMenuItem("Play", 280, 210, 30, 120);
		menuItems.add(item);

		item = new GraphicsMenuItem("Settings Menu", 400, 175, 30, 200);
		menuItems.add(item);

		GraphicsMenu menu = new GraphicsMenu(menuItems);
		menu.configure();
		frame.add(menu);

		menu.setMenuListener(new GraphicsMenuListener() {
			@Override
			public void menuSelected(String text) {
				System.out.println(text);
			}
		});



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
