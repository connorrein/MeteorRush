package edu.uw.meteorRush.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import edu.uw.meteorRush.common.Game;
import edu.uw.meteorRush.common.ResourceLoader;
import edu.uw.meteorRush.common.SceneObject;
import edu.uw.meteorRush.common.Vector2;
import edu.uw.meteorRush.impl.entities.PlayerShip;
import edu.uw.meteorRush.impl.scenes.GameScene;

public class PlayerFollowingText extends SceneObject {

	private static final double DURATION = 1.25;
	private static final Font FONT = ResourceLoader.loadFont("res/Font.ttf", 30);

	private String text;
	private double deathTime;

	public PlayerFollowingText(String text) {
		this.text = text;
		deathTime = Game.getInstance().getTime() + DURATION;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void tick() {
		if (Game.getInstance().getTime() >= deathTime) {
			Game.getInstance().getOpenScene().removeObject(this);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setFont(FONT);
		g.setColor(Color.WHITE);
		PlayerShip player = ((GameScene) Game.getInstance().getOpenScene()).getPlayer();
		Vector2 textPosition = player.getPosition().add(75, -20);
		g.drawString(text, (int) textPosition.getX(), (int) textPosition.getY());
	}

	@Override
	public void dispose() {
	}

}
