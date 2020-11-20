package edu.uw.project.common;

import java.util.ArrayList;
import java.util.List;

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

	public boolean collidesWith(Collider other) {
		return this.minX <= other.maxX && this.maxX >= other.minX && this.minY <= other.maxY && this.maxY >= other.minY;
	}

	private void checkForCollision() {
		for (Collider other : colliders) {
			boolean collides = this.collidesWith(other);
			if (contacts.contains(other)) {
				if (!collides) {
					onCollisionExit(other);
					contacts.remove(other);
				}
			} else {
				if (collides) {
					onCollisionEnter(other);
					contacts.add(other);
				}
			}
		}
	}

	public void dispose() {
		colliders.remove(this);
	}

	public abstract void onCollisionEnter(Collider other);

	public abstract void onCollisionExit(Collider other);

}
