package edu.uw.meteorRush.impl.waves;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.impl.Main;
import edu.uw.meteorRush.impl.scenes.GameScene;

public class Wave1 extends Wave {

	private static final double BASE_ENEMY_SPAWN_PERIOD = 0.75;
	private static final int BASE_MAX_ENEMY_COUNT = 20;

	private double modifiedEnemySpawnPeriod;
	private int modifiedMaxEnemyCount;
	private int enemyCount;
	private double startTime;
	private double nextSpawnTime;

	public Wave1() {
		modifiedEnemySpawnPeriod = BASE_ENEMY_SPAWN_PERIOD / Main.difficulty.getModifier();
		modifiedMaxEnemyCount = (int) (BASE_MAX_ENEMY_COUNT * Main.difficulty.getModifier());
		enemyCount = 0;
		startTime = Game.getInstance().getTime();
		nextSpawnTime = startTime + GameScene.FIRST_WAVE_WAIT_TIME;
	}

	@Override
	public void tick() {
		double currentTime = Game.getInstance().getTime();
		if (currentTime >= nextSpawnTime) {
			nextSpawnTime = nextSpawnTime + modifiedEnemySpawnPeriod;
			spawnEnemy();
		}
	}

	private void spawnEnemy() {
		enemyCount++;
		spawnAsteroid();
		GameScene scene = (GameScene) Game.getInstance().getOpenScene();
		if (enemyCount >= modifiedMaxEnemyCount) {
			scene.removeObject(this);
			scene.addObject(new Wave2());
		}
	}

}
