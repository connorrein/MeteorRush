package gameEngine;

/**
 * Represents a "living, breathing" SceneObject that can move and collide with
 * other Entities.
 * 
 * @author Connor Reinholdtsen
 */
public abstract class Entity extends SceneObject {
    private Collider collider;

    /**
     * Constructs a new Entity located at the given position with the given size.
     * 
     * @param position a vector representing the position
     * @param size     a vector representing the size/dimensions
     */
    public Entity(Vector2 position, Vector2 size) {
	collider = new EntityCollider(this, position, size);
    }

    /**
     * Invoked by a Scene when this Entity is added to it. Can be overridden in
     * subclasses of Entity, but overriding methods must invoke super.
     */
    @Override
    public void initialize() {
	collider.setActive(true);
    }

    /**
     * Returns this Entity's position.
     * 
     * @return a vector
     */
    public final Vector2 getPosition() {
	return collider.getCenter();
    }

    /**
     * Sets this Entity's position, checking for collision with other entities.
     * 
     * @param position a vector representing the new position
     */
    public final void setPosition(Vector2 position) {
	collider.setCenter(position);
    }

    /**
     * Returns the size/dimensions of this Entity.
     * 
     * @return a vector
     */
    public Vector2 getSize() {
	return collider.getDimensions().clone();
    }

    /**
     * Sets the size/dimensions of this Entity, checking for collision with other
     * entities.
     * 
     * @param size a vector
     */
    public void setSize(Vector2 size) {
	collider.setDimensions(size);
    }

    /**
     * Invoked by a Scene when this Entity is removed from it. Can be overridden in
     * subclasses of Entity, but overriding methods must invoke super.
     */
    @Override
    public void dispose() {
	collider.setActive(false);
    }

    /**
     * Invoked when this Entity enters a collision with the other given Entity.
     * 
     * @param other the other entity
     */
    public abstract void onCollisionEnter(Entity other);

    /**
     * Invoked when this Entity exits a collision with the other given Entity.
     * 
     * @param other the other entity
     */
    public abstract void onCollisionExit(Entity other);

    /**
     * EntityColliders represent the hitbox of an Entity.
     */
    public static class EntityCollider extends Collider {
	private Entity entity;

	private EntityCollider(Entity entity, Vector2 position, Vector2 size) {
	    super(position, size.getX(), size.getY());
	    this.entity = entity;
	}

	/**
	 * Returns the entity that this EntityCollider is attached to.
	 * 
	 * @return the entity
	 */
	public Entity getEntity() {
	    return entity;
	}

	@Override
	public void onCollisionEnter(Collider other) {
	    if (other instanceof EntityCollider) {
		Entity otherEntity = ((EntityCollider) other).getEntity();
		entity.onCollisionEnter(otherEntity);
	    }
	}

	@Override
	public void onCollisionExit(Collider other) {
	    if (other instanceof EntityCollider) {
		Entity otherEntity = ((EntityCollider) other).getEntity();
		entity.onCollisionExit(otherEntity);
	    }
	}
    }
}
