package edu.uw.meteorRush.gameEngine;

/**
 * Represents a 2-dimensional vector with an x-component and a y-component that
 * support basic vector operations.
 * 
 * @author Connor Reinholdtsen
 */
public class Vector2 {

	private double x, y;

	/**
	 * Constructs a new vector with the given components.
	 * 
	 * @param x the x-component
	 * @param y the y-component
	 */
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructs a new vector with the given angle and magnitude.
	 * 
	 * @param angle     the angle in radians
	 * @param magnitude the magnitude of the vector
	 */
	public static Vector2 fromAngle(double angle, double magnitude) {
		double x = Math.cos(angle) * magnitude;
		double y = Math.sin(angle) * magnitude;
		return new Vector2(x, y);
	}

	/**
	 * Returns this vector's x-component.
	 * 
	 * @return the x-component
	 */
	public double getX() {
		return x;
	}

	/**
	 * Sets this vector's x-component.
	 * 
	 * @param x the x-component
	 */
	public Vector2 setX(double x) {
		this.x = x;
		return this;
	}

	/**
	 * Returns this vector's y-component.
	 * 
	 * @return the y-component
	 */
	public double getY() {
		return y;
	}

	/**
	 * Sets this vector's y-component.
	 * 
	 * @param y the y-component
	 */
	public Vector2 setY(double y) {
		this.y = y;
		return this;
	}

	/**
	 * Adds the other given vector to this vector. This vector will be modified.
	 * 
	 * @param other the vector to be added
	 * @return this vector
	 */
	public Vector2 add(Vector2 other) {
		return add(other.x, other.y);
	}

	/**
	 * Adds the given x-component and y-component to this vector. This vector will
	 * be modified.
	 * 
	 * @param x the x-component to be added
	 * @param y the y-component to be added
	 * @return this vector
	 */
	public Vector2 add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * Subtracts the other given vector from this vector. This vector will be
	 * modified.
	 * 
	 * @param other the vector to be subtracted
	 * @return this vector
	 */
	public Vector2 subtract(Vector2 other) {
		return subtract(other.x, other.y);
	}

	/**
	 * Subtracts the given x-component and y-component from this vector. This vector
	 * will be modified.
	 * 
	 * @param x the x-component to be subtracted
	 * @param y the y-component to be subtracted
	 * @return this vector
	 */
	public Vector2 subtract(double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	/**
	 * Multiplies this vector by the given scalar. The x-component and y-component
	 * of this vector will by multiplied by the scalar. This vector will be
	 * modified.
	 * 
	 * @param scalar the scalar
	 * @return this vector
	 */
	public Vector2 multiply(double scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	/**
	 * Returns the length of this vector.
	 * 
	 * @return the length of this vector
	 */
	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Normalizes this vector, makes it a unit vector.
	 * 
	 * @return this vector
	 */
	public Vector2 normalize() {
		double magnitude = magnitude();
		x /= magnitude;
		y /= magnitude;
		return this;
	}

	/**
	 * Returns the dot product of this vector with the other given vector.
	 * 
	 * @param other the other vector
	 * @return the dot product
	 */
	public double dotProduct(Vector2 other) {
		return this.x * other.x + this.y * other.y;
	}

	/**
	 * Returns the angle between this vector and the other given vector.
	 * 
	 * @param other the other vector
	 * @return the angle in radians
	 */
	public double angleBetween(Vector2 other) {
		return Math.acos(this.dotProduct(other) / (this.magnitude() * other.magnitude()));
	}

	/**
	 * Rotates this vector counterclockwise by the given angle.
	 * 
	 * @param angle the angle in radians
	 * @return this vector
	 */
	public Vector2 rotate(double angle) {
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		// must create temps because values of old x and y are used to calculate new
		// values of x and y
		double newX = x * cos - y * sin;
		double newY = x * sin + y * cos;
		x = newX;
		y = newY;
		return this;
	}

	/**
	 * Returns a copy of this vector.
	 * 
	 * @return a copy
	 */
	public Vector2 clone() {
		return new Vector2(x, y);
	}

	/**
	 * Returns a String representation of this vector's x-component and y-component.
	 * 
	 * @return a String representing this vector
	 */
	@Override
	public String toString() {
		return String.format("[%f, %f]", x, y);
	}

}
