package edu.uw.meteorRush.common;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * Central class that controls the game loop, display, and execution of scene
 * behavior.
 * 
 * @author Connor Reinholdtsen
 */
public class Game {

	private static final long MILIS_PER_FRAME = 5;

	private static Game instance;

	private String title;
	private int width;
	private int height;
	private Display display;
	private InputManager inputManager;
	private Scene currentScene;
	private boolean running;
	private double timeScale;
	private double elapsedTimeSeconds;
	private double deltaTimeSeconds;
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
		this.timeScale = 1.0;
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
		inputManager = new InputManager();
		display = new Display(title, width, height, inputManager.getKeyListener());
		running = true;
		new Thread() {
			@Override
			public void run() {
				long lastTimeMilis = System.currentTimeMillis();
				while (running) {
					long currentTimeMilis = System.currentTimeMillis();
					long absoluteDeltaTimeMilis = currentTimeMilis - lastTimeMilis;
					if (absoluteDeltaTimeMilis < MILIS_PER_FRAME) {
						continue;
					}
					lastTimeMilis = currentTimeMilis;
					deltaTimeSeconds = absoluteDeltaTimeMilis * 0.001 * timeScale;
					elapsedTimeSeconds += deltaTimeSeconds;
					if (currentScene != null) {
						if (!sceneInitialized) {
							currentScene.initialize();
							sceneInitialized = true;
						}
						tick();
						render();
					}
					inputManager.tick();
				}
				if (currentScene != null) {
					currentScene.dispose();
				}
				display.close();
			}
		}.start();
	}

	/**
	 * Stop the game by disposing of the open Scene if there is one, exiting the
	 * game loop, and closing the game window.
	 */
	public void stop() {
		if (!running) {
			throw new IllegalStateException("Game already stopped!");
		}
		running = false;
		// The game loop will dipose of the open scene and close the Display once it
		// notices that running == false.
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
	 * @param scene the scene to be loaded
	 */
	public void loadScene(Scene scene) {
		if (currentScene != null) {
			currentScene.dispose();
		}
		sceneInitialized = false;
		this.currentScene = scene;
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
		BufferStrategy bufferStrategy = display.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		graphics.clearRect(0, 0, width, height);
		currentScene.render(graphics);
		graphics.dispose();
		bufferStrategy.show();
	}

	/**
	 * Returns the rate at which time in the game progresses with respect to system
	 * time.
	 * 
	 * @return a double representing the rate of time progression
	 */
	public double getTimeScale() {
		return timeScale;
	}

	/**
	 * Sets the rate at which time in the game progresses with respect to system
	 * time. This will affect results from getTime() and getDeltaTime(). For
	 * example, setTimeScale(0.0) will cause the game to "pause". The default time
	 * scale is 1.0. That is, time in the game passes at the same rate as time in
	 * the system by default.
	 * 
	 * @param timeScale the rate at which time progresses
	 */
	public void setTimeScale(double timeScale) {
		this.timeScale = timeScale;
	}

	/**
	 * Returns how much scaled time has passed since the start of the game.
	 * 
	 * @return the time in seconds.
	 */
	public double getTime() {
		return elapsedTimeSeconds;
	}

	/**
	 * Returns how much scaled time has passes since the previous update of the game
	 * loop.
	 * 
	 * @return the delta time in seconds.
	 */
	public double getDeltaTime() {
		return deltaTimeSeconds;
	}

}
