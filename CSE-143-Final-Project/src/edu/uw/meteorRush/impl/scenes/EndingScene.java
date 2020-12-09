package edu.uw.meteorRush.impl.scenes;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.InputManager;
import edu.uw.meteorRush.common.ResourceLoader;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Scanner;

public class EndingScene extends SceneWithKeys {

    private Image backgroundImage;
    private Clip backgroundMusic;
    private int currentOption;
    private int score;
    private boolean wasNewHighScore;
    private int highScore;
    private String os;

    private static final Font UI_FONT = ResourceLoader.loadFont("res/Font.ttf", 36);
    private static final Font ENDING_FONT = ResourceLoader.loadFont("res/Font.ttf", 100);

    private final String[] ENDING_OPTIONS = { "RESTART", "MAIN MENU", "QUIT" };

    public EndingScene (int score) {
        this.score = score;
    }

    public void initialize() {
        addObject(new FadeIn(1.5));
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

    public void render(Graphics g){
        super.render(g);
        InputManager inputManager = Game.getInstance().getInputManager();
        g.setFont(UI_FONT);
        g.setColor(Color.WHITE);
        g.setFont(ENDING_FONT);
        g.drawString("YOU DIED", 640, 145);
        g.drawString("YOUR SCORE: " + score, 640, 255);
        if (wasNewHighScore) {
            g.drawString("NEW HIGH SCORE!", 640, 365);
        } else {
            g.drawString("HIGH SCORE: " + highScore, 640, 365);
        }
        g.setFont(UI_FONT);
        currentOption = upDown(inputManager, ENDING_OPTIONS, currentOption);
        renderScrollingMenus(g, ENDING_OPTIONS, currentOption);
        endMenuEnter(inputManager);
    }

    /**
     * Decides what enter will do depending on the which option is highlighted, goes
     * to main menu, or quits the game
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

    public int getHighScore() throws FileNotFoundException {
        File highScoreFile;
        if(os.contains("Mac")) {
            highScoreFile = new File("/Library/Application Support/highScore.txt");
        } else {
            String userProfile = System.getProperty("user.home");
            highScoreFile = new File(userProfile + "/Local/highScore.txt");
        }
        if(highScoreFile.exists()) {
            Scanner scanner = new Scanner(highScoreFile);
            if(scanner.hasNext()) {
                scanner.close();
                return scanner.nextInt();
            }
            scanner.close();
        }
        return 0;
    }

    public void setHighScore(int highScore) throws IOException {
        File highScoreFile;
        if(os.contains("Mac")) {
            highScoreFile = new File("/Library/Application Support/highScore.txt");
        } else {
            String userProfile = System.getProperty("user.home");
            highScoreFile = new File(userProfile + "/Local/highScore.txt");
        }
        highScoreFile.delete();
        highScoreFile.createNewFile();
        FileWriter fw = new FileWriter(highScoreFile);
        String highScoreString = "" + highScore;
        fw.write(highScoreString);
        fw.close();
    }
}
