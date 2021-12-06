package impl.waves;

import java.awt.Graphics;

import gameEngine.Game;
import gameEngine.SceneObject;
import gameEngine.Vector2;
import impl.Main;
import impl.entities.AsteroidLarge;
import impl.entities.Hornet;
import impl.entities.Javelin;
import impl.entities.Marauder;
import impl.scenes.GameScene;

/**
 * A wave of enemies.
 */
public abstract class Wave extends SceneObject {
    protected void spawnAsteroid() {
	GameScene scene = (GameScene) Game.getInstance().getOpenScene();
	Vector2 position = new Vector2(Main.WIDTH + 100, Main.HEIGHT * Math.random());
	Vector2 direction = new Vector2(-1, Math.random() - 0.5);
	AsteroidLarge enemy = new AsteroidLarge(position, direction);
	scene.addObject(enemy);
    }

    protected void spawnJavelin() {
	GameScene scene = (GameScene) Game.getInstance().getOpenScene();
	Vector2 position = new Vector2(Main.WIDTH + 50, Math.random() * Main.HEIGHT * 0.85 + 0.075 * Main.HEIGHT);
	Javelin enemy = new Javelin(position);
	scene.addObject(enemy);
    }

    protected void spawnHornet() {
	GameScene scene = (GameScene) Game.getInstance().getOpenScene();
	Vector2 position = new Vector2(Main.WIDTH + 50, Math.random() * Main.HEIGHT);
	Hornet enemy = new Hornet(position);
	scene.addObject(enemy);
    }

    protected void spawnMarauder() {
	GameScene scene = (GameScene) Game.getInstance().getOpenScene();
	Vector2 position = new Vector2(Main.WIDTH + 100, Math.random() * Main.HEIGHT * 0.85 + 0.075 * Main.HEIGHT);
	Marauder enemy = new Marauder(position);
	scene.addObject(enemy);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void render(Graphics g) {
    }

    @Override
    public void dispose() {
    }
}
