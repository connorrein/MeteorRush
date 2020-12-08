package edu.uw.meteorRush.impl.scenes;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.InputManager;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.impl.Difficulty;
import edu.uw.meteorRush.impl.Main;

/**
 * @author Marko Milovanovic
 */
public class MainMenuScene extends SceneWithKeys {

	private static final Font UI_FONT = ResourceLoader.loadFont("res/Font.ttf", 36);
	// list of options
	private final String[] MAIN_MENU_OPTIONS = { "START", "DIFFICULTY", "CREDITS", "INTRO", "QUIT" };
	private final String[] SETTINGS_OPTIONS = { "EASY", "MEDIUM", "HARD", "BACK" };

	private BufferedImage backgroundImage;
	private Image IntroScene1;
	private Image title;
	private Clip backgroundMusic;

	// Stores the current option the user has highlighted.
	private int currentOption;
	// The current scene
	private String sceneOption;

	public MainMenuScene(String sceneOption) {
		this.sceneOption = sceneOption;
	}

	public MainMenuScene() {
		this.sceneOption = "Intro";
	}

	@Override
	public void initialize() {
		backgroundImage = ResourceLoader.toBufferedImage(
				ResourceLoader.loadImage("res/images/backgrounds/GameBackground.png").getScaledInstance(24750, 825, 0));
		IntroScene1 = ResourceLoader.loadImage("res/images/backgrounds/IntroScene1.png").getScaledInstance(1800, 800,
				0);
		title = ResourceLoader.loadImage("res/images/ui/Title.png").getScaledInstance(1089, 322, 0);

		backgroundMusic = ResourceLoader.loadAudioClip("res/audio/MainMenuMusic.wav");
		backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
		addObject(new FadeIn(1.0));
	}

	@Override
	public void render(Graphics g) {
		InputManager inputManager = Game.getInstance().getInputManager();
		g.setFont(UI_FONT);
		g.setColor(Color.WHITE);

		double time = Game.getInstance().getTime();
		int x = (int) (time * 50 % 22195);
		Image backgroundSubImage = backgroundImage.getSubimage(x, 0, Main.WIDTH, Main.HEIGHT);
		g.drawImage(backgroundSubImage, 0, 0, null);

		// render background and text
		if (sceneOption.equals("Intro")) {
			introScene(g, inputManager);
		} else if (sceneOption.equals("Main")) {
			g.drawImage(title, 355, 45, null);
			currentOption = upDown(inputManager, MAIN_MENU_OPTIONS, currentOption);
			renderScrollingMenus(g, MAIN_MENU_OPTIONS, currentOption);
			mainMenuEnter(inputManager);
		} else if (sceneOption.equals("Difficulty")) {
			g.drawImage(title, 355, 45, null);
			currentOption = upDown(inputManager, SETTINGS_OPTIONS, currentOption);
			renderScrollingMenus(g, SETTINGS_OPTIONS, currentOption);
			settingsMenuEnter(inputManager);
		} else if (sceneOption.equals("Credits")) {
			creditsScene(g, inputManager);
		}
		super.render(g);
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
				addObject(new FadeIn(1.0));
			} else if (currentOption == 3) {
				sceneOption = "Intro";
				addObject(new FadeIn(1.0));
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
		g.drawImage(IntroScene1, 0, 0, null);
		g.drawString("You are an astronaut who got caught in a wormhole...", 10, 35);
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
		g.drawString("CREDITS", 825, 105);
		String[] lines = { "Jacob Barnhart - Programmer", "Connor Reinholdtsen - Programmer",
				"Marko Milovanovic - Programmer", "Amy George - Artist", "", "Main Menu Music: Stellardrone - Eternity",
				"Game Music: F-777 - Ludicrous Speed", "Font: Minecraft" };
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			int width = g.getFontMetrics().stringWidth(line);
			g.drawString(lines[i], 900 - width / 2, 230 + i * 50);
		}
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
		if (Game.getInstance().getTime() % 1.5 < 0.9) {
			g.drawString("PRESS ENTER", 775, 735);
		}
		if (inputManager.getKeyDown(KeyEvent.VK_ENTER)) {
			onSound();
			addObject(new FadeIn(1.0));
			sceneOption = "Main";
		}
	}

}
