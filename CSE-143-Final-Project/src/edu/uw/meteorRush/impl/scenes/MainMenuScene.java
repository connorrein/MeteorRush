package edu.uw.meteorRush.impl.scenes;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.InputManager;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Scene;

public class MainMenuScene extends Scene {

	/**
	 * Stores the current "button" the user has selected.
	 */
	private int currentSelection;

	private Image backgroundImage;
	private Clip backgroundMusic;
	private static final Font UI_FONT = new Font("Consolas", 0, 50);
	// Bigger size for when the option is selected
	private static Font SELECT_FONT = new Font("Consolas", 0, 70);
	// list of options
	private final String[]  MAIN_MENU_OPTIONS = {"Intro", "START", "CREDITS", "SETTINGS", "QUIT"};
	private final String[] SETTINGS_OPTIONS = {"Hard", "Medium", "Easy", "Quit"};

	private String sceneOption;


	@Override
	public void initialize() {
		backgroundImage = ResourceLoader.loadImage("res/MainMenuBackground.jpg");
		backgroundMusic = ResourceLoader.loadAudioClip("res/MainMenuMusic.wav");
		backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
		sceneOption = "Intro";
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		InputManager inputManager = Game.getInstance().getInputManager();
		g.setFont(UI_FONT);
		g.setColor(Color.BLACK);
		// render background and text
		if(sceneOption.equals("Intro")) {
			introSceneRender(g);
			mainMenuOnEnter(inputManager);
		} else if(sceneOption.equals("Main")){
			renderScrolling(g, MAIN_MENU_OPTIONS);
			mainMenuScrolling(inputManager);
		} else if(sceneOption.equals("Settings")){
			renderScrolling(g, SETTINGS_OPTIONS);
			settingsMenuScrolling(inputManager);
		} else if(sceneOption.equals("Credits")){
			creditsSceneRender(g);
			mainMenuOnEnter(inputManager);
		}
	}


	@Override
	public void dispose() {
		super.dispose();
		backgroundMusic.stop();
	}

	public void mainMenuOnEnter(InputManager inputManager) {
		if (inputManager.getKeyDown(KeyEvent.VK_ENTER)) {
			sceneOption = "Main";
		}
	}

	public void upDown(InputManager inputManager, String[] options) {
		// use InputManager to process user input

		// the if it passes the bottom, wraps around to the top
		if (inputManager.getKeyDown(KeyEvent.VK_DOWN)) {
			currentSelection++;
			if(currentSelection >= options.length) {
				currentSelection = 0;
			}
		}

		// the if it passes the bottom, wraps around to the top
		if (inputManager.getKeyDown(KeyEvent.VK_UP)) {
			currentSelection--;
			if (currentSelection < 0) {
				currentSelection = options.length - 1;
			}
		}
	}

	public void mainMenuScrolling(InputManager inputManager) {

		upDown(inputManager, MAIN_MENU_OPTIONS);

		// depending on the option selected, enter will do something else
		if (inputManager.getKeyDown(KeyEvent.VK_ENTER)) {
			if(currentSelection == 0) {
				sceneOption = "Intro";
			} else if(currentSelection == 1) {
				Game.getInstance().loadScene(new GameScene());
			} else if(currentSelection == 2) {
				sceneOption = "Credits";
			} else if(currentSelection == 3){
				sceneOption = "Settings";
			} else if(currentSelection == 4){
				// TODO
			}
		}
	}

	public void settingsMenuScrolling(InputManager inputManager) {
		upDown(inputManager, SETTINGS_OPTIONS);

		// depending on the option selected, enter will do something else
		if (inputManager.getKeyDown(KeyEvent.VK_ENTER)) {
			if(currentSelection == 0) {
				//TODO
				//hard
			} else if(currentSelection == 1){
				//TODO
				//medium
			} else if(currentSelection == 2){
				//TODO
				//easy
			} else if(currentSelection == 3) {
				sceneOption = "Main";
			}
		}

	}

	/**
	 *
	 * @param g: graphics from above
	 * @param options: the options that can be selected
	 * Increases the size of option currently being considered
	 * Also sets its color to red
	 */
	public void renderScrolling(Graphics g, String[] options) {
		//g.drawImage(backgroundImage, 0, 0, null);
		for(int i = 0; i < options.length; i++) {
			if (currentSelection == i) {
				g.setColor(Color.RED);
				g.setFont(SELECT_FONT);
			} else {
				g.setColor(Color.WHITE);
				g.setFont(UI_FONT);
			}
			g.drawString(options[i], 475, 155 + 70 * i);
		}
	}

	public void introSceneRender(Graphics g) {
		//g.drawImage(backgroundImage, 0, 0, null);
		g.drawString("You are an astronaut who went off ", 475, 105);
		g.drawString("course due to a malfunction.", 475, 150);
		g.drawString("Now aliens are trying to kill you", 475, 195);
		g.drawString("in the midst of a hectic asteroid belt", 475, 240);

		g.setColor(Color.RED);
		g.setFont(SELECT_FONT);
		g.drawString("RETURN TO MAIN MENU", 475, 785);
	}

	public void creditsSceneRender(Graphics g) {
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



}
