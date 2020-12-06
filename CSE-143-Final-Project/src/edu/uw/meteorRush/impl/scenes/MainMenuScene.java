package edu.uw.meteorRush.impl.scenes;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.InputManager;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.impl.Difficulty;
import edu.uw.meteorRush.impl.Main;

/**
 * @author Marko Milovanovic
 */
public class MainMenuScene extends MenuScene {

	private static final Font UI_FONT = ResourceLoader.loadFont("res/Font.ttf", 36);
	// list of options
	private final String[] MAIN_MENU_OPTIONS = { "START", "DIFFICULTY", "CREDITS", "INTRO", "QUIT" };
	private final String[] SETTINGS_OPTIONS = { "EASY", "MEDIUM", "HARD", "BACK" };

	//Stores the current option the user has highlighted
	private int currentOption;
	//Stores the current Scene
	private String sceneOption;

	private Image backgroundImage;
	private Image title;
	private Clip backgroundMusic;

	public MainMenuScene(String sceneOption) {
		this.sceneOption = sceneOption;
		initialize();
	}

	public MainMenuScene() {
		this.sceneOption = "Intro";
	}

	@Override
	public void initialize() {
		backgroundImage = ResourceLoader.loadImage("res/images/backgrounds/GameBackground.png").getScaledInstance(24750,
				825, 0);
		title = ResourceLoader.loadImage("res/images/ui/Title.png").getScaledInstance(1089, 322, 0);

		backgroundMusic = ResourceLoader.loadAudioClip("res/audio/MainMenuMusic.wav");
		backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
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
			g.drawImage(title, 355, 45, null);
			currentOption= upDown(inputManager, MAIN_MENU_OPTIONS, currentOption);
			renderScrollingMenus(g, MAIN_MENU_OPTIONS, currentOption);
			mainMenuEnter(inputManager);
		} else if (sceneOption.equals("Difficulty")) {
			g.drawImage(title, 355, 45, null);
			currentOption= upDown(inputManager, SETTINGS_OPTIONS, currentOption);
			renderScrollingMenus(g, SETTINGS_OPTIONS, currentOption);
			settingsMenuEnter(inputManager);
		} else if (sceneOption.equals("Credits")) {
			creditsScene(g, inputManager);
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
			onSound();
			if (currentOption == 0) {
				backgroundMusic.stop();
				Game.getInstance().loadScene(new GameScene());
			} else if (currentOption == 1) {
				sceneOption = "Difficulty";
				currentOption = Main.difficulty.ordinal();
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
			onSound();
			if (currentOption == 0) {
				Main.difficulty = Difficulty.EASY;
			} else if (currentOption == 1) {
				Main.difficulty = Difficulty.MEDIUM;
			} else if (currentOption == 2) {
				Main.difficulty = Difficulty.HARD;
			}
			currentOption = 1;
			sceneOption = "Main";
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
		g.drawString("MAIN MENU", 500, 770);
		if (inputManager.getKeyDown(KeyEvent.VK_ENTER)) {
			onSound();
			sceneOption = "Main";
		}
	}
}
