package com.glutilities.util;

public class Vertex4f extends Vertex3f {

	private float w;

	public Vertex4f(float x, float y, float z, float w) {
		super(x, y, z);
		this.w = w;
	}

	public void add(Vertex4f v) {
		super.add(v);
		w += v.w;
	}

	@Override
	public void normalize() {
		float scale = (float) Math.sqrt(x * x + y * y + z * z + w * w);
		x /= scale;
		y /= scale;
		z /= scale;
		w /= scale;
	}

//	public void crossProduct(Vertex4f b) {
//		float _x = y * b.z - z * b.y;
//		float _y = z * b.x - x * b.x;
//		float _z = x * b.y - y * b.z;
//		x = _x;
//		y = _y;
//		z = _z;
//	}
	
	public void scale(Vertex4f b) {
		super.scale(b);
		w *= b.w;
	}
	
	public void negate() {
		super.negate();
		w *= -1;
	}

	@Override
	public float distance() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * @return the w
	 */
	public float getW() {
		return w;
	}

	/**
	 * @param w
	 *            the w to set
	 */
	public void setW(float w) {
		this.w = w;
	}

	
	public Vertex2f toVertex2() {
		return new Vertex2f(x, y);
	}

}
