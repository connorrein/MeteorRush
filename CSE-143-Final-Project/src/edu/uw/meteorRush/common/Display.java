package edu.uw.meteorRush.common;

import javax.swing.JFrame;

/**
 * Represents the base Display used by any window that is drawn
 *
 * @author marko.p.milovanovic
 */
public class Display {

    protected JFrame window;

    /**
     * Creates and shows a new Display with the given title, width, and height.
     *
     * @param title       the title of the window
     * @param width       how many pixels wide the window is
     * @param height      how many pixels tall the window is
     */
    public Display(String title, int width, int height) {

        window = new JFrame(title);
        window.setSize(width, height);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setVisible(true);
    }


    /**
     * Close the window.
     */
    public void close() {
        window.dispose();
    }

}
