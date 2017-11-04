package com.glutilities.util;

public class Vertex3 {

	private double x;
	private double y;
	private double z;

	public Vertex3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void add(Vertex3 v) {
		x += v.x;
		y += v.y;
		z += v.z;
	}

	public void normalize() {
		double scale = Math.sqrt(x * x + y * y + z * z);
		x /= scale;
		y /= scale;
		z /= scale;
	}

	public void crossProduct(Vertex3 b) {
		double _x = y * b.z - z * b.y;
		double _y = z * b.x - x * b.x;
		double _z = x * b.y - y * b.x;
		x = _x;
		y = _y;
		z = _z;
	}
	
	public void scale(Vertex3 b) {
		x *= b.x;
		y *= b.y;
		z *= b.z;
	}
	
	public void negate() {
		x *= -1;
		y *= -1;
		z *= -1;
	}

	public double distance() {
		return Math.sqrt(x * x + y * y + z * z);
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

	/**
	 * @return the z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * @param z
	 *            the z to set
	 */
	public void setZ(double z) {
		this.z = z;
	}
	
	public Vertex2 toVertex2() {
		return new Vertex2(x, y);
	}

}
