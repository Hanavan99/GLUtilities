package com.glutilities.util;

public class Vertex3f extends Vertex2f {

	protected float z;

	public Vertex3f(float f) {
		super(f);
		z = f;
	}
	
	public Vertex3f(float x, float y, float z) {
		super(x, y);
		this.z = z;
	}

	public void add(Vertex3f v) {
		super.add(v);
		z += v.z;
	}

	@Override
	public void normalize() {
		float scale = (float) Math.sqrt(x * x + y * y + z * z);
		x /= scale;
		y /= scale;
		z /= scale;
	}

	public void crossProduct(Vertex3f b) {
		float _x = y * b.z - z * b.y;
		float _y = z * b.x - x * b.x;
		float _z = x * b.y - y * b.x;
		x = _x;
		y = _y;
		z = _z;
	}

	public void scale(Vertex3f b) {
		super.scale(b);
		z *= b.z;
	}

	public void negate() {
		super.negate();
		z *= -1;
	}

	@Override
	public float distance() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * @return the z
	 */
	public float getZ() {
		return z;
	}

	/**
	 * @param z the z to set
	 */
	public void setZ(float z) {
		this.z = z;
	}

	public float getR() {
		return getX();
	}
	
	public void setR(float r) {
		setX(r);
	}
	
	public float getG() {
		return getY();
	}
	
	public void setG(float g) {
		setY(g);
	}
	
	public float getB() {
		return getZ();
	}
	
	public void setB(float b) {
		setZ(b);
	}

}
