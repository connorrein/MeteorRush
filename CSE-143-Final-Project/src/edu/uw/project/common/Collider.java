package edu.uw.project.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a 2D axis-aligned bounding box that can collide with others.
 * Colliders should be disposed once they are no longer in use.
 * 
 * @author Connor Reinholdtsen
 */
public abstract class Collider {

	private static List<Collider> colliders = new ArrayList<>();

	private double minX, minY, maxX, maxY;
	private List<Collider> contacts;

	public Collider(double minX, double minY, double maxX, double maxY) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		contacts = new ArrayList<>();
	}

	public Collider(Vector2 min, Vector2 max) {
		this(min.getX(), min.getY(), max.getX(), max.getY());
	}

	public Collider(Vector2 center, double width, double height) {
		this(center.getX() - width / 2.0, center.getY() - height / 2.0, center.getX() + width / 2.0,
				center.getY() + height / 2.0);
	}

	public double getMinX() {
		return minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
		checkForCollision();
	}

	public double getMinY() {
		return minY;
	}

	public void setMinY(double minY) {
		this.minY = minY;
		checkForCollision();
	}

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
		checkForCollision();
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
		checkForCollision();
	}

	public Vector2 getMin() {
		return new Vector2(minX, minY);
	}

	public Vector2 getMax() {
		return new Vector2(maxX, maxY);
	}

	public boolean isContacting(Collider other) {
		return this.minX <= other.maxX && this.maxX >= other.minX && this.minY <= other.maxY && this.maxY >= other.minY;
	}

	/**
	 * Returns the Colliders that this collider is contacting.
	 * 
	 * @return the List of colliders that this collider is contacting
	 */
	public List<Collider> getContacts() {
		return new ArrayList<>(contacts);
	}

	private void checkForCollision() {
		for (Collider other : colliders) {
			boolean collides = this.isContacting(other);
			if (contacts.contains(other)) {
				if (!collides) {
					this.onCollisionExit(other);
					other.onCollisionExit(this);
					this.contacts.remove(other);
					other.contacts.remove(this);
				}
			} else {
				if (collides) {
					this.onCollisionEnter(other);
					other.onCollisionEnter(this);
					this.contacts.add(other);
					other.contacts.add(this);
				}
			}
		}
	}

	/**
	 * Disposes this Collider so that it can no longer collide with other Colliders.
	 * This will also be treated as exiting a collision with all of this Collider's
	 * contacts.
	 */
	public void dispose() {
		for (Collider contact : contacts) {
			onCollisionExit(contact);
			contact.onCollisionExit(contact);
			contact.contacts.remove(this);
		}
		contacts.clear();
		colliders.remove(this);
	}

	/**
	 * Invoked when this Collider enters a collision with another Collider
	 * 
	 * @param other the other Collider in the collision
	 */
	public abstract void onCollisionEnter(Collider other);

	/**
	 * Invoked when this Collider exits a collision with another Collider
	 * 
	 * @param other the other Collider in the collision
	 */
	public abstract void onCollisionExit(Collider other);

}
