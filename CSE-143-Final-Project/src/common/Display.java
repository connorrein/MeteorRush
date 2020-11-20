package common;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Display {

	private JFrame window;

	public Display() {
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setUndecorated(true);
		window.setResizable(false);
		window.setVisible(true);
		KeyListener keyListener = new KeyListener() {
			Sound sound = new Sound("laser.wav");

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				sound.loop();
			}
		};
		window.addKeyListener(keyListener);
	}

}
