package edu.uw.meteorRush.impl.scenes;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.InputManager;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Scene;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;

public class EndingScene extends MenuScene {

    private Image backgroundImage;
    private Clip backgroundMusic;
    private int currentOption;

    private static final Font UI_FONT = ResourceLoader.loadFont("res/Font.ttf", 36);
    private static final Font ENDING_FONT = ResourceLoader.loadFont("res/Font.ttf", 100);

    private final String[] ENDING_OPTIONS = { "RESTART", "MAIN MENU", "QUIT" };

    public void render(Graphics g) {
        super.render(g);
        InputManager inputManager = Game.getInstance().getInputManager();
        g.setFont(UI_FONT);
        g.setColor(Color.WHITE);
        g.setFont(ENDING_FONT);
        g.drawString("YOU DIED", 640, 145);
        g.setFont(UI_FONT);
        currentOption = upDown(inputManager, ENDING_OPTIONS, currentOption);
        renderScrollingMenus(g, ENDING_OPTIONS, currentOption);
        endMenuEnter(inputManager);
    }

    /**
     * Decides what enter will do depending on the which option is highlighted, goes
     * to main menu, or quits the game
     *
     * @param inputManager
     */
    public void endMenuEnter(InputManager inputManager) {

        // depending on the option selected, enter will do something else
        if (inputManager.getKeyDown(KeyEvent.VK_ENTER)) {
            if (currentOption == 0) {
                Game.getInstance().loadScene(new GameScene());
            } else if (currentOption == 1) {
                Game.getInstance().loadScene(new MainMenuScene("Main"));
            } else if (currentOption == 2) {
                Game.getInstance().stop();
            }
        }
    }
}
