package edu.uw.meteorRush.impl.waves;

import java.awt.Graphics;

import edu.uw.meteorRush.gameEngine.Game;
import edu.uw.meteorRush.gameEngine.SceneObject;
import edu.uw.meteorRush.gameEngine.Vector2;
import edu.uw.meteorRush.impl.Main;
import edu.uw.meteorRush.impl.entities.AsteroidLarge;
import edu.uw.meteorRush.impl.entities.Hornet;
import edu.uw.meteorRush.impl.entities.Javelin;
import edu.uw.meteorRush.impl.entities.Marauder;
import edu.uw.meteorRush.impl.scenes.GameScene;

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
