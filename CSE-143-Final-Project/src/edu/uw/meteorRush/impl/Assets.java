package edu.uw.meteorRush.impl;

import java.awt.Image;

import edu.uw.meteorRush.common.ResourceLoader;

public class Assets {

	public static final Image PLAYER_1 = ResourceLoader.loadImage("res/Player1.png").getScaledInstance(185, 100, 0);
	public static final Image PLAYER_2 = ResourceLoader.loadImage("res/Player2.png").getScaledInstance(185, 100, 0);
	public static final Image PLAYER_BACKARD_1 = ResourceLoader.loadImage("res/PlayerBackward1.png")
			.getScaledInstance(185, 100, 0);
	public static final Image PLAYER_BACKARD_2 = ResourceLoader.loadImage("res/PlayerBackward2.png")
			.getScaledInstance(185, 100, 0);
	public static final Image PLAYER_DOWN_1 = ResourceLoader.loadImage("res/PlayerDown1.png").getScaledInstance(185,
			100, 0);
	public static final Image PLAYER_DOWN_2 = ResourceLoader.loadImage("res/PlayerDown2.png").getScaledInstance(185,
			100, 0);
	public static final Image PLAYER_FORWARD_1 = ResourceLoader.loadImage("res/PlayerForward1.png")
			.getScaledInstance(185, 100, 0);
	public static final Image PLAYER_FORWARD_2 = ResourceLoader.loadImage("res/PlayerForward2.png")
			.getScaledInstance(185, 100, 0);
	public static final Image PLAYER_UP_1 = ResourceLoader.loadImage("res/PlayerUp1.png").getScaledInstance(185, 100,
			0);
	public static final Image PLAYER_UP_2 = ResourceLoader.loadImage("res/PlayerUp2.png").getScaledInstance(185, 100,
			0);
	public static final Image PLAYER_LASER = ResourceLoader.loadImage("res/PlayerLaser.png").getScaledInstance(50, 10,
			0);
	public static final Image ALIEN = ResourceLoader.loadImage("res/Alien.png").getScaledInstance(150, 150, 0);
	public static final Image GAME_BACKGROUND = ResourceLoader.loadImage("res/GameBackground.jpg");
	public static final Image EXPLOSION = ResourceLoader.loadImage("res/Explosion.png");
}
