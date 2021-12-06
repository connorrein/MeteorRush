package impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import gameEngine.Game;
import gameEngine.ResourceLoader;
import gameEngine.SceneObject;
import gameEngine.Vector2;
import impl.entities.PlayerShip;
import impl.scenes.GameScene;

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
