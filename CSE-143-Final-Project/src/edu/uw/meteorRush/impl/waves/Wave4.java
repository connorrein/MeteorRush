package edu.uw.meteorRush.impl.waves;

import java.awt.Graphics;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.Main;
import edu.uw.meteorRush.impl.entities.AsteroidLarge;
import edu.uw.meteorRush.impl.entities.Javelin;
import edu.uw.meteorRush.impl.entities.Hornet;
import edu.uw.meteorRush.impl.scenes.GameScene;

public class Wave4 extends Wave {

	private static final double BASE_ENEMY_SPAWN_PERIOD = 1.5;
	private static final int BASE_MAX_ENEMY_COUNT = 15;

	private double modifiedEnemySpawnPeriod;
	private double modifiedMaxEnemyCount;
	private int enemyCount;
	private double startTime;
	private double nextSpawnTime;

	public Wave4() {
		modifiedEnemySpawnPeriod = BASE_ENEMY_SPAWN_PERIOD / Main.difficulty.getModifier();
		modifiedMaxEnemyCount = BASE_MAX_ENEMY_COUNT * Main.difficulty.getModifier();
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
			nextSpawnTime = nextSpawnTime + modifiedEnemySpawnPeriod;
			spawnEnemy();
		}
	}

	private void spawnEnemy() {
		enemyCount++;
		switch (enemyCount % 3) {
		case 0:
			spawnAsteroid();
		case 1:
			spawnJavelin();
		case 2:
			spawnHornet();
		}
		if (enemyCount > modifiedMaxEnemyCount) {
			GameScene scene = (GameScene) Game.getInstance().getOpenScene();
			scene.removeObject(this);
			scene.addObject(new Wave1());
		}
	}

	private void spawnAsteroid() {
		GameScene scene = (GameScene) Game.getInstance().getOpenScene();
		Vector2 position = new Vector2(Main.WIDTH + 100, Main.HEIGHT * Math.random());
		Vector2 direction = new Vector2(-1, Math.random() - 0.5);
		AsteroidLarge enemy = new AsteroidLarge(position, direction);
		scene.addObject(enemy);
	}

	private void spawnJavelin() {
		GameScene scene = (GameScene) Game.getInstance().getOpenScene();
		Vector2 position = new Vector2(Main.WIDTH + 50, Math.random() * Main.HEIGHT * 0.85 + 0.075 * Main.HEIGHT);
		Javelin enemy = new Javelin(position);
		scene.addObject(enemy);
	}

	private void spawnHornet() {
		GameScene scene = (GameScene) Game.getInstance().getOpenScene();
		Vector2 position = new Vector2(Main.WIDTH + 50, Math.random() * Main.HEIGHT);
		Hornet enemy = new Hornet(position);
		scene.addObject(enemy);
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public void dispose() {
	}

}
