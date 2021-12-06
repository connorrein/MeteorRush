package gameEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a 2D axis-aligned bounding box that can collide with others.
 * setActive(true) must be invoked on Colliders after construction before they
 * can interact with other Colliders. Once a Collider is no longer in use,
 * setActive(false) should be invoked.
 * 
 * @author Connor Reinholdtsen
 */
public abstract class Collider {
    private static List<Collider> activeColliders = new ArrayList<>();

    private double minX, minY, maxX, maxY;
    private List<Collider> contacts;

    /**
     * Constructs a new Collider with the given bounds.
     * 
     * @param minX the minimum bound on the x-axis
     * @param minY the minimum bound on the y-axis
     * @param maxX the maximum bound on the x-axis
     * @param maxY the maximum bound on the y-axis
     */
    public Collider(double minX, double minY, double maxX, double maxY) {
	this.minX = minX;
	this.minY = minY;
	this.maxX = maxX;
	this.maxY = maxY;
	contacts = new ArrayList<>();
	activeColliders.add(this);
    }

    /**
     * Constructs a new Collider with the given center and dimensions.
     * 
     * @param center the location of the Collider's center
     * @param width  the length of this collider along the x-axis
     * @param height the length of this collider along the y-axis
     */
    public Collider(Vector2 center, double width, double height) {
	this(center.getX() - width / 2.0, center.getY() - height / 2.0, center.getX() + width / 2.0,
		center.getY() + height / 2.0);
    }

    /**
     * Sets whether this Collider will interact with other Colliders. After
     * construction, setActive(true) must be invoked on this Collider before it can
     * interact with other Colliders. After a Collider is no longer in use,
     * setActive(false) should be invoked on this Collider.
     * 
     * @param active whether this collider will interact with other Colliders
     */
    public void setActive(boolean active) {
	if (active) {
	    if (!activeColliders.contains(this)) {
		activeColliders.add(this);
		checkForCollision();
	    }
	} else {
	    activeColliders.remove(this);
	    for (int i = 0; i < contacts.size(); i++) {
		Collider contact = contacts.get(i);
		handleCollisionExit(contact);
	    }
	}
    }

    /**
     * Returns the minimum bound of this Collider on the x-axis.
     * 
     * @return the minimum bound on the x-axis
     */
    public double getMinX() {
	return minX;
    }

    /**
     * Sets the minimum bound of this Collider on the x-axis and checks for
     * collision with other Colliders.
     * 
     * @param minX the minimum bound on the x-axis
     */
    public void setMinX(double minX) {
	this.minX = minX;
	checkForCollision();
    }

    /**
     * Returns the minimum bound of this Collider on the y-axis.
     * 
     * @return the minimum bound on the y-axis
     */
    public double getMinY() {
	return minY;
    }

    /**
     * Sets the minimum bound of this Collider on the y-axis and checks for
     * collision with other Colliders.
     * 
     * @param minY the minimum bound on the y-axis
     */
    public void setMinY(double minY) {
	this.minY = minY;
	checkForCollision();
    }

    /**
     * Returns the maximum bound of this Collider on the x-axis.
     * 
     * @return the maximum bound on the x-axis
     */
    public double getMaxX() {
	return maxX;
    }

    /**
     * Sets the maximum bound of this Collider on the x-axis and checks for
     * collision with other Colliders.
     * 
     * @param maxX the maximum bound on the x-axis
     */
    public void setMaxX(double maxX) {
	this.maxX = maxX;
	checkForCollision();
    }

    /**
     * Returns the maximum bound of this Collider on the y-axis.
     * 
     * @return the maximum bound on the y-axis
     */
    public double getMaxY() {
	return maxY;
    }

    /**
     * Sets the maximum bound of this Collider on the y-axis and checks for
     * collision with other Colliders.
     * 
     * @param maxY the maximum bound on the y-axis
     */
    public void setMaxY(double maxY) {
	this.maxY = maxY;
	checkForCollision();
    }

    /**
     * Returns the location of this Collider's minimum bound.
     * 
     * @return a vector representing the location of the minimum bound
     */
    public Vector2 getMin() {
	return new Vector2(minX, minY);
    }

    /**
     * Returns the location of this Collider's maximum bound.
     * 
     * @return a vector representing the location of the maximum bound
     */
    public Vector2 getMax() {
	return new Vector2(maxX, maxY);
    }

    /**
     * Returns the location of this Collider's center.
     * 
     * @return a vector representing the location of the center
     */
    public Vector2 getCenter() {
	return new Vector2((minX + maxX) / 2.0, (minY + maxY) / 2.0);
    }

    /**
     * Sets the location of this Collider's center, preserving dimensions, and
     * checks for collision with other Colliders.
     * 
     * @param center a vector representing the location of the center
     */
    public void setCenter(Vector2 center) {
	double semiWidth = (maxX - minX) / 2.0;
	double semiHeight = (maxY - minY) / 2.0;
	minX = center.getX() - semiWidth;
	minY = center.getY() - semiHeight;
	maxX = center.getX() + semiWidth;
	maxY = center.getY() + semiHeight;
	checkForCollision();
    }

    /**
     * Returns the dimensions of this Collider.
     * 
     * @return a vector representing the width and height
     */
    public Vector2 getDimensions() {
	return new Vector2(maxX - minX, maxY - minY);
    }

    /**
     * Sets the dimensions of this Collider, preserving center location, and
     * checking for collision with other Colliders.
     * 
     * @param dimensions a vector representing the width and height
     */
    public void setDimensions(Vector2 dimensions) {
	Vector2 center = getCenter();
	minX = center.getX() - dimensions.getX() / 2.0;
	minY = center.getY() - dimensions.getY() / 2.0;
	maxX = center.getX() + dimensions.getX() / 2.0;
	maxY = center.getY() + dimensions.getX() / 2.0;
	checkForCollision();
    }

    /**
     * Returns true if this Collider overlaps with the other given Collider. Returns
     * falase otherwise.
     * 
     * @param other the Collider to check for contact
     * @return whether this Collider is contacting the other Collider
     */
    public boolean isContacting(Collider other) {
	return this.minX <= other.maxX && this.maxX >= other.minX && this.minY <= other.maxY && this.maxY >= other.minY;
    }

    /**
     * Returns the Colliders that this Collider is contacting.
     * 
     * @return a List of Colliders that this Collider is contacting
     */
    public List<Collider> getContacts() {
	return new ArrayList<>(contacts);
    }

    /**
     * Checks if this Collider has entered or exited collisions with other
     * Colliders. onCollisionEnter(Collider) and onCollisionExit(Collider) will be
     * called if appropriate.
     */
    private void checkForCollision() {
	for (int i = 0; i < activeColliders.size(); i++) {
	    Collider other = activeColliders.get(i);
	    if (other == this) {
		continue;
	    }
	    boolean collides = this.isContacting(other);
	    if (contacts.contains(other)) {
		if (!collides) {
		    handleCollisionExit(other);
		}
	    } else {
		if (collides) {
		    handleCollisionEnter(other);
		}
	    }
	}
    }

    private void handleCollisionEnter(Collider other) {
	this.contacts.add(other);
	other.contacts.add(this);
	this.onCollisionEnter(other);
	other.onCollisionEnter(this);
    }

    private void handleCollisionExit(Collider other) {
	this.contacts.remove(other);
	other.contacts.remove(this);
	this.onCollisionExit(other);
	other.onCollisionExit(this);
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
