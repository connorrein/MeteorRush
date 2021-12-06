package gameEngine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;

/**
 * Singleton class that controls the game loop, display, and execution of scene
 * behavior.
 * 
 * @author Connor Reinholdtsen
 */
public class Game {
    private static Game instance;

    private String title;
    private int width;
    private int height;
    private InputManager inputManager;
    private Display display;
    private Scene currentScene;
    private Scene nextScene;
    private boolean running;
    private double timeScale;
    private double elapsedTimeSeconds;
    private double deltaTimeSeconds;

    /**
     * Creates a new game with the given title, window width, and window height.
     * 
     * @param title  the title of the game
     * @param width  the width of the game's window
     * @param height the height of the game's window
     */
    public Game(String title, int width, int height, Image icon) {
	if (instance != null) {
	    throw new IllegalStateException("There can be only one!");
	}
	this.title = title;
	this.width = width;
	this.height = height;
	inputManager = new InputManager();
	display = new Display(title, width, height, icon, inputManager.getKeyListener());
	currentScene = null;
	nextScene = null;
	running = false;
	timeScale = 1.0;
	elapsedTimeSeconds = 0.0;
	deltaTimeSeconds = 0.0;
	instance = this;
    }

    /**
     * Returns the instance of the game being played.
     * 
     * @return the instance
     */
    public static Game getInstance() {
	return instance;
    }

    /**
     * Returns this Game's title.
     * 
     * @return
     */
    public String getTitle() {
	return title;
    }

    /**
     * Returns the width of this Game's window.
     * 
     * @return the window's width
     */
    public int getWidth() {
	return width;
    }

    /**
     * Returns the height of this Game's window.
     * 
     * @return the window's height
     */
    public int getHeight() {
	return height;
    }

    /**
     * Returns the Game's InputManager to access user input.
     * 
     * @return the InputManager
     */
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
	display.open();
	running = true;
	new Thread() {
	    @Override
	    public void run() {
		long lastTimeMilis = System.currentTimeMillis();
		while (running) {
		    long currentTimeMilis = System.currentTimeMillis();
		    long absoluteDeltaTimeMilis = currentTimeMilis - lastTimeMilis;
		    lastTimeMilis = currentTimeMilis;
		    deltaTimeSeconds = absoluteDeltaTimeMilis * 0.001 * timeScale;
		    elapsedTimeSeconds += deltaTimeSeconds;
		    if (nextScene != null) {
			if (currentScene != null) {
			    currentScene.dispose();
			}
			currentScene = nextScene;
			nextScene = null;
			currentScene.initialize();
		    }
		    if (currentScene != null) {
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
	// The game loop will dispose of the open scene and close the Display once it
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
     * Loads the given scene once the game loop finishes its current iteration,
     * closing the previously loaded scene if there was one.
     * 
     * @param scene the scene to be loaded
     */
    public void loadScene(Scene scene) {
	// We must wait to set the current scene to the given scene until the game loop
	// finishes its current iteration. Not doing so could result in errors if tick()
	// or render() is invoked on the given scene in the game loop before
	// initialize().
	nextScene = scene;
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
