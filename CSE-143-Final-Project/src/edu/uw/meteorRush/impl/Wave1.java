package edu.uw.meteorRush.impl;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.Scene;
import edu.uw.meteorRush.common.Vector2;

public class Wave1 extends Wave {

	private static final double ENEMY_SPAWN_PERIOD = 2.0;
	private static final int ENEMY_COUNT = 15;

	private double startTime;
	private double nextSpawnTime;

	public Wave1() {
		startTime = Game.getInstance().getTime();
		nextSpawnTime = startTime + ENEMY_SPAWN_PERIOD;
	}

	@Override
	public void tick() {
		double currentTime = Game.getInstance().getTime();
		if (currentTime > nextSpawnTime) {
			nextSpawnTime = nextSpawnTime + ENEMY_SPAWN_PERIOD;
			spawnEnemy();
		}
	}

	private void spawnEnemy() {
		Scene scene = Game.getInstance().getOpenScene();
		Vector2 position = new Vector2(Main.WIDTH + 100, Main.WIDTH / 2.0);
		AlienShip enemy = new AlienShip(position);
		scene.addEntity(enemy);
	}

}
