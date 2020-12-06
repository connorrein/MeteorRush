package edu.uw.meteorRush.impl.waves;

import java.awt.Graphics;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.Main;
import edu.uw.meteorRush.impl.entities.MantaRay;
import edu.uw.meteorRush.impl.scenes.GameScene;

public class Wave3 extends Wave {

	private static final double ENEMY_SPAWN_PERIOD = 1 / Main.difficulty;
	private static final int MAX_ENEMY_COUNT = (int) (15 * Main.difficulty);

	private int enemyCount;
	private double startTime;
	private double nextSpawnTime;

	public Wave3() {
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
		spawnTriangularAlienShip();
		if (enemyCount > MAX_ENEMY_COUNT) {
			GameScene scene = (GameScene) Game.getInstance().getOpenScene();
			scene.removeObject(this);
			scene.addObject(new Wave4());
		}
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
