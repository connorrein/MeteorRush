package edu.uw.meteorRush.impl.scenes;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.InputManager;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.Scene;
import edu.uw.meteorRush.impl.Main;

/**
 * @author Marko Milovanovic
 */
public class MainMenuScene extends Scene {

	/**
	 * Stores the current option the user has highlighted.
	 */
	private int currentOption;

	private Image backgroundImage;
	private Image buttonSelected;
	private Image button;
	private Image title;
	private Clip backgroundMusic;
	private static final Font UI_FONT = ResourceLoader.loadFont("res/Font.ttf", 36);

	// list of options
	private final String[] MAIN_MENU_OPTIONS = { "START", "SETTINGS", "CREDITS", "INTRO", "QUIT" };
	private final String[] SETTINGS_OPTIONS = { "HARD", "MEDIUM", "EASY", "BACK" };

	private String sceneOption;

	@Override
	public void initialize() {
		backgroundImage = ResourceLoader.loadImage("res/images/backgrounds/GameBackground.png").getScaledInstance(24750,
				825, 0);
		buttonSelected = ResourceLoader.loadImage("res/images/ui/ButtonHover.png").getScaledInstance(336, 56, 0);
		button = ResourceLoader.loadImage("res/images/ui/Button.png").getScaledInstance(336, 56, 0);
		title = ResourceLoader.loadImage("res/images/ui/Title.png").getScaledInstance(1089, 322, 0);

		backgroundMusic = ResourceLoader.loadAudioClip("res/audio/MainMenuMusic.wav");
		backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
		sceneOption = "Intro";
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		InputManager inputManager = Game.getInstance().getInputManager();
		g.setFont(UI_FONT);
		g.setColor(Color.WHITE);
		double time = Game.getInstance().getTime();
		g.drawImage(backgroundImage, (int) (time * -50 % 22195), 0, null);

		// render background and text
		if (sceneOption.equals("Intro")) {
			introScene(g, inputManager);
		} else if (sceneOption.equals("Main")) {
			upDown(inputManager, MAIN_MENU_OPTIONS);
			renderScrollingMenus(g, MAIN_MENU_OPTIONS);
			mainMenuEnter(inputManager);
		} else if (sceneOption.equals("Settings")) {
			upDown(inputManager, SETTINGS_OPTIONS);
			renderScrollingMenus(g, SETTINGS_OPTIONS);
			settingsMenuEnter(inputManager);
		} else if (sceneOption.equals("Credits")) {
			creditsScene(g, inputManager);
		}
	}

	/**
	 * based on whether the up or down keys are clicked, the highlighted option is
	 * changed
	 * 
	 * @param inputManager: to process user input
	 * @param options:      the possible options in list form
	 */
	public void upDown(InputManager inputManager, String[] options) {

		// the if it passes the bottom, wraps around to the top
		if (inputManager.getKeyDown(KeyEvent.VK_DOWN)) {
			currentOption++;
			if (currentOption >= options.length) {
				currentOption = 0;
			}
		}

		// the if it passes the bottom, wraps around to the top
		if (inputManager.getKeyDown(KeyEvent.VK_UP)) {
			currentOption--;
			if (currentOption < 0) {
				currentOption = options.length - 1;
			}
		}
	}

	/**
	 * Decides what enter will do depending on the which option is highlighted,
	 * picks the option selected
	 * 
	 * @param inputManager
	 */
	public void mainMenuEnter(InputManager inputManager) {

		// depending on the option selected, enter will do something else
		if (inputManager.getKeyDown(KeyEvent.VK_ENTER)) {
			if (currentOption == 0) {
				backgroundMusic.stop();
				Game.getInstance().loadScene(new GameScene());
			} else if (currentOption == 1) {
				sceneOption = "Settings";
			} else if (currentOption == 2) {
				sceneOption = "Credits";
			} else if (currentOption == 3) {
				sceneOption = "Intro";
			} else if (currentOption == 4) {
				Game.getInstance().stop();
			}
		}
	}

	/**
	 * Decides what enter will do depending on the which option is highlighted, sets
	 * a difficulty or returns the user to the main menu
	 * 
	 * @param inputManager
	 */
	public void settingsMenuEnter(InputManager inputManager) {

		// depending on the option selected, enter will do something else
		if (inputManager.getKeyDown(KeyEvent.VK_ENTER)) {
			if (currentOption == 0) {
				// TODO
				// hard
				sceneOption = "Main";
			} else if (currentOption == 1) {
				// TODO
				// medium
				sceneOption = "Main";
			} else if (currentOption == 2) {
				// TODO
				// easy
				sceneOption = "Main";
			} else if (currentOption == 3) {
				sceneOption = "Main";
			}
		}

	}

	/**
	 * Renders all the options Increases the size of option currently being
	 * considered
	 * 
	 * @param g:       graphics from above
	 * @param options: the options that can be selected
	 */
	public void renderScrollingMenus(Graphics g, String[] options) {
		g.drawImage(title, 355, 45, null);
		Image buttonState = button;
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
			g.drawString(option, 905 - option.length() * 13, 462 + 67 * i);
		}
	}

	/**
	 * The intro scene: tells us the backstory for the game as well and gives a
	 * button to return us to the main menu
	 * 
	 * @param g
	 * @param inputManager
	 */
	public void introScene(Graphics g, InputManager inputManager) {
		// g.drawImage(backgroundImage, 0, 0, null);
		g.drawString("You are an astronaut who went off ", 475, 105);
		g.drawString("course due to a malfunction.", 475, 150);
		g.drawString("Now aliens are trying to kill you", 475, 195);
		g.drawString("in the midst of a hectic asteroid belt", 475, 240);
		returnToMenuOption(g, inputManager);
	}

	/**
	 * The credits scene: shows us who made the game as well as a button to return
	 * us to the main menu
	 * 
	 * @param g
	 * @param inputManager
	 */
	public void creditsScene(Graphics g, InputManager inputManager) {
		// g.drawImage(backgroundImage, 0, 0, null);
		g.drawString("CREATORS:", 475, 105);
		g.drawString("Jacob Barnhart", 480, 195);
		g.drawString("Connor Reinholdtsen", 475, 245);
		g.drawString("Marko Milovanovic", 475, 295);
		g.drawString("OTHER CREDITS:", 475, 405);
		g.drawString("Main Menu Music: Stellardrone – Eternity", 475, 495);
		g.drawString("In-game Music: F-777 - Ludicrous Speed", 480, 545);
		g.drawString("Game art: Amy George", 475, 595);
		returnToMenuOption(g, inputManager);
	}

	/**
	 * Creates a big red RETURN TO MENU button that returns the user to the main
	 * menu
	 * 
	 * @param g
	 * @param inputManager
	 */
	public void returnToMenuOption(Graphics g, InputManager inputManager) {
		g.setColor(Color.BLACK);
		// g.setFont(SELECT_FONT);
		g.drawImage(buttonSelected, 475, 725, null);
		g.drawString("MAIN MENU", 485, 785);
		if (inputManager.getKeyDown(KeyEvent.VK_ENTER)) {
			sceneOption = "Main";
		}
	}

}
