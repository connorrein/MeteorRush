package impl.scenes;

import java.awt.Color;
import java.awt.Graphics;

import gameEngine.Game;
import gameEngine.SceneObject;
import impl.Main;

public class FadeIn extends SceneObject {
    private double duration;
    private double startTime;

    public FadeIn(double duration) {
	this.duration = duration;
    }

    @Override
    public void initialize() {
	startTime = Game.getInstance().getTime();
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
	double time = Game.getInstance().getTime();
	if (time > startTime + duration) {
	    Game.getInstance().getOpenScene().removeObject(this);
	    return;
	}
	int alpha = (int) (255 - (time - startTime) / duration * 255.0);
	Color color = new Color(0, 0, 0, alpha);
	g.setColor(color);
	g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
    }

    @Override
    public void dispose() {
    }
}
