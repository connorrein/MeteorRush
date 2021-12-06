package impl.entities;

import gameEngine.Entity;
import gameEngine.Game;
import gameEngine.Vector2;

public abstract class Drop extends Entity {
    private double deathTime;
    private double startY;

    public Drop(Vector2 position, Vector2 size, double lifetime) {
	super(position, size);
	deathTime = Game.getInstance().getTime() + lifetime;
	startY = position.getY();
    }

    @Override
    public void tick() {
	double time = Game.getInstance().getTime();
	if (time >= deathTime) {
	    Game.getInstance().getOpenScene().removeObject(this);
	} else {
	    Vector2 position = getPosition();
	    position.setX(position.getX() - 150.0 * Game.getInstance().getDeltaTime());
	    position.setY(startY + 15.0 * Math.sin(2.0 * (deathTime - time)));
	    setPosition(position);
	}
    }

    @Override
    public void onCollisionEnter(Entity other) {
	if (other instanceof PlayerShip) {
	    onPickup((PlayerShip) other);
	    Game.getInstance().getOpenScene().removeObject(this);
	}
    }

    @Override
    public void onCollisionExit(Entity other) {
    }

    public abstract void onPickup(PlayerShip player);
}
