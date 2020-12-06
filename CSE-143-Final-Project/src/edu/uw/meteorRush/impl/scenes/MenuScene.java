package edu.uw.meteorRush.impl.scenes;

import edu.uw.meteorRush.common.InputManager;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Scene;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuScene extends Scene {

    public final Image buttonSelected = ResourceLoader.loadImage("res/images/ui/ButtonHover.png").getScaledInstance(336, 56, 0);
    public final Image button = ResourceLoader.loadImage("res/images/ui/Button.png").getScaledInstance(336, 56, 0);

    public void initialize(){
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
            g.drawString(option, 905 - option.length() * 12, 462 + 67 * i);
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
