package impl.entities;

import gameEngine.Entity;
import gameEngine.Game;
import gameEngine.Vector2;

public abstract class Projectile extends Entity {
    private Vector2 velocity;

    public Projectile(Vector2 position, Vector2 size, Vector2 velocity) {
	super(position, size);
	this.velocity = velocity.clone();
    }

    @Override
    public void tick() {
	Vector2 position = getPosition();
	position.add(velocity.clone().multiply(Game.getInstance().getDeltaTime()));
	setPosition(position);
    }
}
