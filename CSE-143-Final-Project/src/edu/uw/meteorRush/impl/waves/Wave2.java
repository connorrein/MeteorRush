package edu.uw.meteorRush.impl.waves;

import java.awt.Graphics;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.Main;
import edu.uw.meteorRush.impl.entities.AlienShip;
import edu.uw.meteorRush.impl.scenes.GameScene;

public class Wave2 extends Wave {

	private static final double ENEMY_SPAWN_PERIOD = 1;
	private static final int MAX_ENEMY_COUNT = 15;

	private int enemyCount;
	private double startTime;
	private double nextSpawnTime;

	public Wave2() {
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
		GameScene scene = (GameScene) Game.getInstance().getOpenScene();
		Vector2 position = new Vector2(Main.WIDTH + 100, Main.WIDTH / 2.0);
		AlienShip enemy = new AlienShip(position);
		scene.addObject(enemy);
		if (enemyCount > MAX_ENEMY_COUNT) {
			scene.removeObject(this);
			// scene.addObject(new Wave3());
		}
	}

	@Override
	public void render(Graphics g) {
	}

	@Override
	public void dispose() {
	}

}
