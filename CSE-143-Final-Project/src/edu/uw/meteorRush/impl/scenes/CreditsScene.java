package edu.uw.meteorRush.impl.scenes;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.InputManager;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Scene;

public class CreditsScene extends Scene {

    /**
     * Stores the current "button" the user has selected.
     */

    private Image backgroundImage;
    private Clip backgroundMusic;
    private static final Font UI_FONT = new Font("Consolas", 0, 50);
    private static Font SELECT_FONT = new Font("Consolas", 0, 70);

    @Override
    public void initialize() {
        backgroundImage = ResourceLoader.loadImage("res/MainMenuBackground.jpg");
        backgroundMusic = ResourceLoader.loadAudioClip("res/MainMenuMusic.wav");
        backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void tick() {
        InputManager inputManager = Game.getInstance().getInputManager();
        if (inputManager.getKey(KeyEvent.VK_ENTER)) {
            Game.getInstance().loadScene(new MainMenuScene());
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        // render background and text
        //g.drawImage(backgroundImage, 0, 0, null);
        g.setFont(UI_FONT);
        g.setColor(Color.BLACK);
        g.drawString("CREATORS:", 475, 105);
        g.drawString("Jacob Barnhart", 480, 195);
        g.drawString("Connor Reinholdtsen", 475, 245);
        g.drawString("Marko Milovanovic", 475, 295);
        g.drawString("OTHER CREDITS:", 475, 405);
        g.drawString("Main Menu Music: Stellardrone – Eternity", 475, 495);
        g.drawString("In-game Music: F-777 - Ludicrous Speed", 480, 545);
        g.drawString("Game art: Amy George", 475, 595);

        g.setColor(Color.RED);
        g.setFont(SELECT_FONT);
        g.drawString("RETURN TO MENU", 475, 785);

    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundMusic.stop();
    }

}
