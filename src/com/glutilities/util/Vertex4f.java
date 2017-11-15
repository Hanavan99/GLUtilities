package com.glutilities.util;

public class Vertex4f extends Vertex3f {

	public static final Vertex4f ORIGIN = new Vertex4f(0, 0, 0, 0);

	public static final Vertex4f TRANSPARENT = new Vertex4f(0, 0, 0, 0);
	public static final Vertex4f WHITE = new Vertex4f(1, 1, 1, 1);
	public static final Vertex4f BLACK = new Vertex4f(0, 0, 0, 1);
	public static final Vertex4f RED = new Vertex4f(1, 0, 0, 1);
	public static final Vertex4f GREEN = new Vertex4f(0, 1, 0, 1);
	public static final Vertex4f BLUE = new Vertex4f(0, 0, 1, 1);
	public static final Vertex4f YELLOW = new Vertex4f(1, 1, 0, 1);
	public static final Vertex4f CYAN = new Vertex4f(0, 1, 1, 1);
	public static final Vertex4f PURPLE = new Vertex4f(1, 0, 1, 1);

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

	// public void crossProduct(Vertex4f b) {
	// float _x = y * b.z - z * b.y;
	// float _y = z * b.x - x * b.x;
	// float _z = x * b.y - y * b.z;
	// x = _x;
	// y = _y;
	// z = _z;
	// }

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
	 * @param w the w to set
	 */
	public void setW(float w) {
		this.w = w;
	}
	
	public float getA() {
		return getW();
	}
	
	public void setA(float a) {
		setW(a);
	}

}
