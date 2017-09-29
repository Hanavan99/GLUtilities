package com.glutilities.util;

public class Boundary {

	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;

	private Boundary(int xMin, int xMax, int yMin, int yMax) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}

	/**
	 * @return the xMin
	 */
	public int getXMin() {
		return xMin;
	}

	/**
	 * @return the xMax
	 */
	public int getXMax() {
		return xMax;
	}

	/**
	 * @return the yMin
	 */
	public int getYMin() {
		return yMin;
	}

	/**
	 * @return the yMax
	 */
	public int getYMax() {
		return yMax;
	}

}
