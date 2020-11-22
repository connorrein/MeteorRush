package edu.uw.meteorRush.impl;

import java.awt.Image;

import edu.uw.meteorRush.common.ResourceLoader;

public class Assets {

	public static final Image PLAYER = ResourceLoader.loadImage("res/Player.png").getScaledInstance(75, 75, 0);
	public static final Image PLAYER_LASER = ResourceLoader.loadImage("res/PlayerLaser.png").getScaledInstance(50, 50, 0);
	public static final Image ALIEN = ResourceLoader.loadImage("res/Alien.png");
	public static final Image GAME_BACKGROUND = ResourceLoader.loadImage("res/GameBackground.jpg");

}
