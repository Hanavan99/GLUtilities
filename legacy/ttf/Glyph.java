package com.glutilities.text.ttf;

import com.glutilities.util.Rectangle;

public abstract class Glyph {

	private int numberOfContours;
	private Rectangle boundary;

	public Glyph(int numberOfContours, Rectangle boundary) {
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
	public Rectangle getBoundary() {
		return boundary;
	}

}
