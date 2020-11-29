package edu.uw.meteorRush.common;

import java.awt.Graphics;

public abstract class Entity {

	private Collider collider;

	public Entity(Vector2 position, Vector2 size) {
		collider = new EntityCollider(this, position, size);
	}

	public Vector2 getPosition() {
		return collider.getCenter();
	}

	public void setPosition(Vector2 position) {
		collider.setCenter(position);
	}

	public Vector2 getSize() {
		return collider.getDimensions().clone();
	}

	public void setSize(Vector2 size) {
		collider.setDimensions(size);
	}

	public void dispose() {
		collider.dispose();
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract void onCollisionEnter(Entity other);

	public abstract void onCollisionExit(Entity other);

	public static class EntityCollider extends Collider {
		private Entity entity;

		public EntityCollider(Entity entity, Vector2 position, Vector2 size) {
			super(position, size.getX(), size.getY());
			this.entity = entity;
		}

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
