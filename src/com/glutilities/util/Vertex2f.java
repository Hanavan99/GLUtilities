package com.glutilities.util;

public class Vertex2f {

	protected float x;
	protected float y;

	public Vertex2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void add(Vertex2f v) {
		x += v.x;
		y += v.y;
	}

	public void normalize() {
		float scale = (float) Math.sqrt(x * x + y * y);
		x /= scale;
		y /= scale;
	}

	public void scale(Vertex2f b) {
		x *= b.x;
		y *= b.y;
	}

	public void negate() {
		x *= -1;
		y *= -1;
	}

	public float distance() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	public Vertex3f toVertex3() {
		return new Vertex3f(x, y, 0);
	}
	
	@Override
	public String toString() {
		return String.format("x=%.2f y=%.2f", x, y);
	}

}
