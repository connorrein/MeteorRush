package edu.uw.meteorRush.common;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Timer;

/**
 * Controls the game loop, display, and execution of scene behavior.
 * 
 * @author Connor Reinholdtsen
 */
public class Game {

	private static Game instance;

	private String title;
	private int width;
	private int height;
	private boolean running;
	private long startTimeMilis;
	private GameDisplay gameDisplay;
	private InputManager inputManager;
	private Scene currentScene;
	private Timer timer;
	private long deltaTimeMilis;
	private boolean sceneInitialized;

	/**
	 * Creates a new game with the given title, window width, and window height.
	 * 
	 * @param title  the title of the game
	 * @param width  the width of the game's window
	 * @param height the height of the game's window
	 */
	public Game(String title, int width, int height) {
		if (instance != null) {
			throw new IllegalStateException("There can be only one!");
		}
		this.title = title;
		this.width = width;
		this.height = height;
		timer = new Timer();
		instance = this;
	}

	/**
	 * Returns the instance of the game being played.
	 */
	public static Game getInstance() {
		return instance;
	}

	public InputManager getInputManager() {
		return inputManager;
	}

	/**
	 * Begin the game by opening the game window and entering the game loop.
	 */
	public void start() {
		if (running) {
			throw new IllegalStateException("Game already started!");
		}
		running = true;
		startTimeMilis = System.currentTimeMillis();
		inputManager = new InputManager();
		gameDisplay = new GameDisplay(title, width, height, inputManager.getKeyListener());
		new Thread() {
			@Override
			public void run() {
				long lastTimeMilis = System.currentTimeMillis();
				while (running) {
					long currentTimeMilis = System.currentTimeMillis();
					deltaTimeMilis = currentTimeMilis - lastTimeMilis;
					lastTimeMilis = currentTimeMilis;
					if (currentScene != null) {
						if (!sceneInitialized) {
							currentScene.initialize();
							sceneInitialized = true;
						}
						tick();
						render();
					}
				}
			}
		}.start();
	}

	/**
	 * Stop the game by exiting the game window and exiting the game loop.
	 */
	public void stop() {
		if (!running) {
			throw new IllegalStateException("Game already stopped!");
		}
		running = false;
		gameDisplay.close();
	}

	/**
	 * Returns the current scene open.
	 * 
	 * @return the current Scene open
	 */
	public Scene getOpenScene() {
		return currentScene;
	}

	/**
	 * Loads the given scene, closing the previously loaded scene if there was one.
	 * 
	 * @param scene the scene to be loaded.
	 */
	public void loadScene(Scene scene) {
		if (currentScene != null) {
			currentScene.dispose();
		}
		sceneInitialized = false;
		this.currentScene = scene;
	}

	public Timer getTimer() {
		return timer;
	}

	/**
	 * Updates variables and performs non-graphical tasks.
	 */
	private void tick() {
		currentScene.tick();
	}

	/**
	 * Performs graphical tasks.
	 */
	private void render() {
		BufferStrategy bufferStrategy = gameDisplay.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		graphics.clearRect(0, 0, width, height);
		currentScene.render(graphics);
		graphics.dispose();
		bufferStrategy.show();
	}

	/**
	 * Returns how much time has passed since the start of the game.
	 * 
	 * @return the time in seconds.
	 */
	public double getTime() {
		return (System.currentTimeMillis() - startTimeMilis) / 1000.0;
	}

	/**
	 * Returns how much time has passes since the previous update of the game loop.
	 * 
	 * @return the delta time in seconds.
	 */
	public double getDeltaTime() {
		return deltaTimeMilis / 1000.0;
	}

}
