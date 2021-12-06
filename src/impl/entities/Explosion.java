package impl.entities;

import java.awt.Graphics;
import java.awt.Image;

import gameEngine.Entity;
import gameEngine.Game;
import gameEngine.ResourceLoader;
import gameEngine.Vector2;

public class Explosion extends Entity {
    private static final Image[] FRAMES = { ResourceLoader.loadImage("res/images/entities/explosion/Explosion1.png"),
	    ResourceLoader.loadImage("res/images/entities/explosion/Explosion2.png"),
	    ResourceLoader.loadImage("res/images/entities/explosion/Explosion3.png"),
	    ResourceLoader.loadImage("res/images/entities/explosion/Explosion4.png"),
	    ResourceLoader.loadImage("res/images/entities/explosion/Explosion5.png"),
	    ResourceLoader.loadImage("res/images/entities/explosion/Explosion6.png"),
	    ResourceLoader.loadImage("res/images/entities/explosion/Explosion7.png") };

    private double lifetime;
    private double startTime;
    private Image[] scaledFrames;

    public Explosion(Vector2 position, int size, double lifetime) {
	super(position, new Vector2(size, size));
	this.lifetime = lifetime;
	startTime = Game.getInstance().getTime();
	scaledFrames = new Image[FRAMES.length];
	for (int i = 0; i < FRAMES.length; i++) {
	    scaledFrames[i] = FRAMES[i].getScaledInstance(size, size, Image.SCALE_FAST);
	}
    }

    @Override
    public void initialize() {
	super.initialize();
	ResourceLoader.loadAudioClip("res/audio/Explosion.wav").start();
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
	double time = Game.getInstance().getTime();
	double proportion = (time - startTime) / lifetime;
	int frameIndex = (int) (proportion * FRAMES.length);
	if (frameIndex >= FRAMES.length) {
	    Game.getInstance().getOpenScene().removeObject(this);
	    return;
	}
	Image frame = scaledFrames[frameIndex];
	Vector2 position = getPosition();
	Vector2 size = getSize();
	int x = (int) (position.getX() - size.getX() / 2.0);
	int y = (int) (position.getY() - size.getY() / 2.0);
	g.drawImage(frame, x, y, null);
    }

    @Override
    public void onCollisionEnter(Entity other) {
    }

    @Override
    public void onCollisionExit(Entity other) {
    }
}
