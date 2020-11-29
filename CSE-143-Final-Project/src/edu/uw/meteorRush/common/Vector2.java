package edu.uw.meteorRush.common;

/**
 * Represents a 2-dimensional vector with an x-component and a y-component.
 * 
 * @author Connor Reinholdtsen
 */
public class Vector2 {

	private double x, y;

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public Vector2 setX(double x) {
		this.x = x;
		return this;
	}

	public double getY() {
		return y;
	}

	public Vector2 setY(double y) {
		this.y = y;
		return this;
	}

	public Vector2 add(Vector2 other) {
		return add(other.x, other.y);
	}

	public Vector2 add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public Vector2 subtract(Vector2 other) {
		return subtract(other.x, other.y);
	}

	public Vector2 subtract(double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	public Vector2 multiply(double scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}

	public double dotProduct(Vector2 other) {
		return this.x * other.x + this.y * other.y;
	}

	public double angleBetween(Vector2 other) {
		return Math.acos(this.dotProduct(other) / (this.magnitude() * other.magnitude()));
	}

	public Vector2 rotate(double radians) {
		double sin = Math.sin(radians);
		double cos = Math.cos(radians);
		// must create temps because values of old x and y are used to calculate new
		// values of x and y
		double newX = x * cos - y * sin;
		double newY = x * sin + y * cos;
		x = newX;
		y = newY;
		return this;
	}

	public Vector2 clone() {
		return new Vector2(x, y);
	}

	@Override
	public String toString() {
		return String.format("[%f, %f]", x, y);
	}

}
