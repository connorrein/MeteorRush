package impl.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.sound.sampled.Clip;

import gameEngine.Game;
import gameEngine.InputManager;
import gameEngine.ResourceLoader;
import impl.Main;

/**
 *
 * @author Marko Milovanovic
 */
public class EndingScene extends SceneWithKeys {
    private Image backgroundImage;
    private Clip backgroundMusic;
    private int currentOption;
    private int score;
    private boolean wasNewHighScore;
    private int highScore;
    private String os;

    private static final Font DEATH_FONT = ResourceLoader.loadFont("res/Font.ttf", 100);
    private static final Font SCORE_FONT = ResourceLoader.loadFont("res/Font.ttf", 50);
    private static final Font UI_FONT = ResourceLoader.loadFont("res/Font.ttf", 36);

    private final String[] ENDING_OPTIONS = { "RESTART", "MAIN MENU", "QUIT" };

    public EndingScene(int score) {
	this.score = score;
	backgroundImage = ResourceLoader.loadImage("res/images/backgrounds/DeathScene.png")
		.getScaledInstance(Main.WIDTH, Main.HEIGHT, 0);
    }

    public void initialize() {
	backgroundMusic = ResourceLoader.loadAudioClip("res/audio/DeathScene.wav");
	backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
	addObject(new FadeIn(5.0));
	os = System.getProperty("os.name");

	try {
	    highScore = getHighScore();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	if (score > highScore) {
	    wasNewHighScore = true;
	    try {
		setHighScore(score);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    highScore = score;
	} else {
	    wasNewHighScore = false;
	}
    }

    public void render(Graphics g) {
	g.drawImage(backgroundImage, 0, 0, null);
	super.render(g);
	InputManager inputManager = Game.getInstance().getInputManager();
	g.setColor(Color.WHITE);
	g.setFont(DEATH_FONT);
	g.drawString("YOU DIED", 655, 180);
	g.setFont(SCORE_FONT);
	String scoreText = "YOUR SCORE: " + score;
	g.drawString(scoreText, 900 - g.getFontMetrics().stringWidth(scoreText) / 2, 275);
	if (wasNewHighScore) {
	    String highScoreText = "NEW HIGH SCORE!";
	    g.drawString("NEW HIGH SCORE!", 900 - g.getFontMetrics().stringWidth(highScoreText) / 2, 360);
	} else {
	    String highScoreText = "NEW HIGH SCORE!";
	    g.drawString("HIGH SCORE: " + highScore, 900 - g.getFontMetrics().stringWidth(highScoreText) / 2, 360);
	}
	g.setFont(UI_FONT);
	currentOption = upDown(inputManager, ENDING_OPTIONS, currentOption);
	renderScrollingMenus(g, ENDING_OPTIONS, currentOption);
	endMenuEnter(inputManager);
    }

    /**
     * Decides what enter will do depending on the which option is highlighted,
     * restarts, goes to main menu, or quits the game
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

    /**
     *
     * returns the high score found in the file, if this is the first time the game
     * was played, creates a folder to house the high score file
     * 
     * @throws FileNotFoundException
     */

    public int getHighScore() throws FileNotFoundException {
	File highScoreFile;
	String userProfile = System.getProperty("user.home");
	int highScore = 0;
	if (os.contains("Mac")) {
	    if (!Files.exists(Paths.get(userProfile + "/Library/Application Support/MeteorRush/"))) {
		createFolderMac();
	    }
	    highScoreFile = new File(userProfile + "/Library/Application Support/MeteorRush/highScore.txt");
	} else if (os.contains("Windows")) {
	    if (!Files.exists(Paths.get(userProfile + "/AppData/Local/MeteorRush/"))) {
		createFolderWindows();
	    }
	    highScoreFile = new File(userProfile + "/AppData/Local/MeteorRush/highScore.txt");
	} else {
	    // e.g. linux
	    return 0;
	}
	if (highScoreFile.exists()) {
	    Scanner scanner = new Scanner(highScoreFile);
	    if (scanner.hasNext()) {
		highScore = scanner.nextInt();
	    }
	    scanner.close();
	}
	return highScore;
    }

    /**
     * Creates a folder called MetoerRush on a Mac OS
     */
    public void createFolderMac() {
	String userProfile = System.getProperty("user.home");
	File highScoreDirectory = new File(userProfile + "/Library/Application Support/MeteorRush/");
	highScoreDirectory.mkdir();
    }

    /**
     * Creates a folder called MetoerRush on a Windows OS
     */
    public void createFolderWindows() {
	String userProfile = System.getProperty("user.home");
	File highScoreDirectory = new File(userProfile + "/AppData/Local/MeteorRush/");
	highScoreDirectory.mkdir();
    }

    /**
     * Saves the high score in a file that can be read again even when the game is
     * closed
     * 
     * @param highScore: the new high score
     * @throws IOException
     */
    public void setHighScore(int highScore) throws IOException {
	File highScoreFile;
	String userProfile = System.getProperty("user.home");
	if (os.contains("Mac")) {
	    highScoreFile = new File(userProfile + "/Library/Application Support/MeteorRush/highScore.txt");
	} else if (os.contains("Windows")) {
	    highScoreFile = new File(userProfile + "/AppData/Local/MeteorRush/highScore.txt");
	} else {
	    // e.g. linux
	    return;
	}
	highScoreFile.delete();
	highScoreFile.createNewFile();
	FileWriter fw = new FileWriter(highScoreFile);
	String highScoreString = "" + highScore;
	fw.write(highScoreString);
	fw.close();
    }

    @Override
    public void dispose() {
	super.dispose();
	backgroundMusic.stop();
    }
}
