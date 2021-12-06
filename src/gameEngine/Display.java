package gameEngine;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * Represents the window in which the game is played in, providing a Canvas for
 * other classes to draw on.
 * 
 * @author Connor Reinholdtsen
 */
class Display {
    /**
     * How many times we want to buffer images before rendering them to the actual
     * window.
     */
    private static final int NUM_BUFFERS = 3;

    private JFrame window;
    private Canvas canvas;

    /**
     * Creates and shows a new Display with the given title, width, and height.
     * 
     * @param title       the title of the window
     * @param width       how many pixels wide the window is
     * @param height      how many pixels tall the window is
     * @param keyListener
     */
    Display(String title, int width, int height, Image icon, KeyListener keyListener) {
	Dimension dimension = new Dimension(width, height);

	window = new JFrame(title);
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.setSize(dimension);
	window.setResizable(false);
	window.setLocationRelativeTo(null);
	window.setIconImage(icon);
	BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "Blank");
	window.getContentPane().setCursor(blankCursor);

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
    BufferStrategy getBufferStrategy() {
	return canvas.getBufferStrategy();
    }

    /**
     * Opens the window.
     */
    void open() {
	window.setVisible(true);
    }

    /**
     * Close the window.
     */
    void close() {
	window.dispose();
    }
}
