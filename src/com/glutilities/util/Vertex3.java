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
	 * @param x the x to set
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
	 * @param y the y to set
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
	 * @param z the z to set
	 */
	public void setZ(double z) {
		this.z = z;
	}

}
