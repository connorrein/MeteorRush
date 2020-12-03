package edu.uw.meteorRush.impl.entities;

import java.awt.Graphics;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.scenes.GameScene;

public class HealthDrop extends Drop {

	private double healAmount;

	public HealthDrop(Vector2 position, Vector2 size, double healAmount) {
		super(position, size);
		this.healAmount = healAmount;
	}

	@Override
	public void onPickup() {
		GameScene scene = (GameScene) Game.getInstance().getOpenScene();
		PlayerShip player = scene.getPlayer();
		player.heal(healAmount);
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub

	}

}
