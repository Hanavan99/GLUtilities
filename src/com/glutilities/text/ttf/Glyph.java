package com.glutilities.text.ttf;

import com.glutilities.util.Boundary;

public abstract class Glyph {

	private int numberOfContours;
	private Boundary boundary;

	public Glyph(int numberOfContours, Boundary boundary) {
		this.numberOfContours = numberOfContours;
		this.boundary = boundary;
	}

	/**
	 * @return the numberOfContours
	 */
	public int getNumberOfContours() {
		return numberOfContours;
	}

	/**
	 * @return the boundary
	 */
	public Boundary getBoundary() {
		return boundary;
	}

}
