package com.glutilities.util;

public class Rectangle {

	private Vertex2f a;
	private Vertex2f b;

	public Rectangle() {
		a = new Vertex2f(0, 0);
		b = new Vertex2f(0, 0);
	}

	public Rectangle(Vertex2f a, Vertex2f b) {
		this.a = a;
		this.b = b;
	}

	public Rectangle(float ax, float ay, float bx, float by) {
		a = new Vertex2f(ax, ay);
		b = new Vertex2f(bx, by);
	}

	public Vertex2f getMin() {
		return new Vertex2f((float) Math.min(a.getX(), b.getX()), (float) Math.min(a.getY(), b.getY()));
	}

	public Vertex2f getMax() {
		return new Vertex2f((float) Math.max(a.getX(), b.getX()), (float) Math.max(a.getY(), b.getY()));
	}

}
