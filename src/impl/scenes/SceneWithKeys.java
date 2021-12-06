package impl.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import gameEngine.InputManager;
import gameEngine.ResourceLoader;
import gameEngine.Scene;

/**
 *
 * @author Marko Milovanovic
 */
public class SceneWithKeys extends Scene {
    private static final Font UI_FONT = ResourceLoader.loadFont("res/Font.ttf", 36);
    public final Image buttonSelected = ResourceLoader.loadImage("res/images/ui/ButtonHover.png").getScaledInstance(336,
	    56, 0);
    public final Image button = ResourceLoader.loadImage("res/images/ui/Button.png").getScaledInstance(336, 56, 0);

    public void initialize() {
    }

    /**
     * Renders all the options Increases the size of option currently being
     * considered
     *
     * @param g:       graphics from above
     * @param options: the options that can be selected
     */
    public void renderScrollingMenus(Graphics g, String[] options, int currentOption) {
	Image buttonState;
	g.setFont(UI_FONT);
	for (int i = 0; i < options.length; i++) {
	    if (currentOption == i) {
		g.setColor(Color.WHITE);
		buttonState = buttonSelected;
	    } else {
		g.setColor(Color.WHITE);
		buttonState = button;
	    }
	    g.drawImage(buttonState, 730, 420 + 67 * i, null);
	    String option = options[i];
	    int width = g.getFontMetrics().stringWidth(option);
	    g.drawString(option, 900 - width / 2, 462 + 67 * i);
	}
    }

    /**
     * based on whether the up or down keys are clicked, the highlighted option is
     * changed
     *
     * @param inputManager: to process user input
     * @param options:      the possible options in list form
     */
    public int upDown(InputManager inputManager, String[] options, int currentOption) {

	// the if it passes the bottom, wraps around to the top
	if (inputManager.getKeyDown(KeyEvent.VK_DOWN)) {
	    onSound();
	    currentOption++;
	    if (currentOption >= options.length) {
		return 0;
	    }
	}

	// the if it passes the bottom, wraps around to the top
	if (inputManager.getKeyDown(KeyEvent.VK_UP)) {
	    onSound();
	    currentOption--;
	    if (currentOption < 0) {
		return options.length - 1;
	    }
	}
	return currentOption;
    }

    public void onSound() {
	ResourceLoader.loadAudioClip("res/audio/Button.wav").start();
    }
}
