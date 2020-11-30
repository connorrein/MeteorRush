package edu.uw.meteorRush.impl.scenes;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.InputManager;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Scene;

public class IntroScene extends Scene {

    /**
     * Stores the current "button" the user has selected.
     */

    private Image backgroundImage;
    private Clip backgroundMusic;
    private static Font UI_FONT = new Font("Consolas", 0, 50);
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
        g.drawString("You are an astronaut who went off ", 475, 105);
        g.drawString("course due to a malfunction.", 475, 150);
        g.drawString("Now aliens are trying to kill you", 475, 195);
        g.drawString("in the midst of a hectic asteroid belt", 475, 240);

        g.setColor(Color.RED);
        g.setFont(SELECT_FONT);
        g.drawString("RETURN TO MAIN MENU", 475, 785);

    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundMusic.stop();
    }

}
