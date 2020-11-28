package edu.uw.meteorRush.impl;

import java.awt.Image;

import edu.uw.meteorRush.common.ResourceLoader;

public class Assets {

	public static final Image PLAYER_1 = ResourceLoader.loadImage("res/Player1.png").getScaledInstance(100, 100, 0);
	public static final Image PLAYER_2 = ResourceLoader.loadImage("res/Player2.png").getScaledInstance(100, 100, 0);
	public static final Image PLAYER_LASER = ResourceLoader.loadImage("res/PlayerLaser.png").getScaledInstance(50, 10,
			0);
	public static final Image ALIEN = ResourceLoader.loadImage("res/Alien.png").getScaledInstance(150, 150, 0);
	public static final Image GAME_BACKGROUND = ResourceLoader.loadImage("res/GameBackground.jpg");
	public static final Image EXPLOSION = ResourceLoader.loadImage("res/Explosion.png");
}
