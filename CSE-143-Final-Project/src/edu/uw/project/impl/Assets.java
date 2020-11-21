package edu.uw.project.impl;

import java.awt.Image;
import edu.uw.project.common.ResourceLoader;

public class Assets {

	public static Image player = ResourceLoader.loadImage("res/Player.png").getScaledInstance(75, 75, 0);
	public static Image alien = ResourceLoader.loadImage("res/Alien.png");
	public static Image gameBackground = ResourceLoader.loadImage("res/GameBackground.jpg");

}
