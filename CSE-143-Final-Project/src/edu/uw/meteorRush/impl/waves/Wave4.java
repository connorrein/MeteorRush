package edu.uw.meteorRush.impl.waves;

import java.awt.Graphics;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.Main;
import edu.uw.meteorRush.impl.entities.AsteroidLarge;
import edu.uw.meteorRush.impl.entities.Javelin;
import edu.uw.meteorRush.impl.entities.MantaRay;
import edu.uw.meteorRush.impl.scenes.GameScene;

public class Wave4 extends Wave {

	private static final double ENEMY_SPAWN_PERIOD = 1.5;
	private static final int MAX_ENEMY_COUNT = 15;

	private int enemyCount;
	private double startTime;
	private double nextSpawnTime;

	public Wave4() {
		enemyCount = 0;
		startTime = Game.getInstance().getTime();
		nextSpawnTime = startTime + GameScene.WAVE_REST_TIME;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void tick() {
		double currentTime = Game.getInstance().getTime();
		if (currentTime >= nextSpawnTime) {
			nextSpawnTime = nextSpawnTime + ENEMY_SPAWN_PERIOD;
			spawnEnemy();
		}
	}

	private void spawnEnemy() {
		enemyCount++;
		switch (enemyCount % 3) {
		case 0:
			spawnLargeAsteroid();
		case 1:
			spawnCircularAlienShip();
		case 2:
			spawnTriangularAlienShip();
		}
		if (enemyCount > MAX_ENEMY_COUNT) {
			GameScene scene = (GameScene) Game.getInstance().getOpenScene();
			scene.removeObject(this);
			scene.addObject(new Wave1());
		}
	}

	private void spawnLargeAsteroid() {
		GameScene scene = (GameScene) Game.getInstance().getOpenScene();
		Vector2 position = new Vector2(Main.WIDTH + 100, Main.HEIGHT * Math.random());
		Vector2 direction = new Vector2(-1, Math.random() - 0.5);
		AsteroidLarge enemy = new AsteroidLarge(position, direction);
		scene.addObject(enemy);
	}

	private void spawnCircularAlienShip() {
		GameScene scene = (GameScene) Game.getInstance().getOpenScene();
		Vector2 position = new Vector2(Main.WIDTH + 50, Math.random() * Main.HEIGHT * 0.85 + 0.075 * Main.HEIGHT);
		Javelin enemy = new Javelin(position);
		scene.addObject(enemy);
	}

	private void spawnTriangularAlienShip() {
		GameScene scene = (GameScene) Game.getInstance().getOpenScene();
		Vector2 position = new Vector2(Main.WIDTH + 50, Math.random() * Main.HEIGHT);
		MantaRay enemy = new MantaRay(position);
		scene.addObject(enemy);
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public void dispose() {
	}

}
