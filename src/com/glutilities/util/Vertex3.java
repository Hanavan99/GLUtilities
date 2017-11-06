package com.glutilities.util;

public class Vertex3 extends Vertex2 {

	private double z;

	public Vertex3(double x, double y, double z) {
		super(x, y);
		this.z = z;
	}

	public void add(Vertex3 v) {
		super.add(v);
		z += v.z;
	}

	@Override
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
		super.scale(b);
		z *= b.z;
	}
	
	public void negate() {
		super.negate();
		z *= -1;
	}

	@Override
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
