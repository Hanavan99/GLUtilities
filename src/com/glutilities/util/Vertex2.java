package com.glutilities.util;

public class Vertex2 {

	private double x;
	private double y;

	public Vertex2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void add(Vertex2 v) {
		x += v.x;
		y += v.y;
	}

	public void normalize() {
		double scale = Math.sqrt(x * x + y * y);
		x /= scale;
		y /= scale;
	}

	public void scale(Vertex2 b) {
		x *= b.x;
		y *= b.y;
	}

	public void negate() {
		x *= -1;
		y *= -1;
	}

	public double distance() {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	public Vertex3 toVertex3() {
		return new Vertex3(x, y, 0);
	}

}
