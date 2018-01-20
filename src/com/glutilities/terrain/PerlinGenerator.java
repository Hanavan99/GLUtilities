package com.glutilities.terrain;

import java.util.Random;

public class PerlinGenerator implements Cloneable {

	/*
	 * Perlin.cpp
	 *
	 * Copyright Chris Little 2012 Author: Chris Little
	 * 
	 * Adapted for Java by Hanavan99
	 */

	private int RAND_MAX = Integer.MAX_VALUE;

	private Random random;

	private int[] p;
	private double[] Gx;
	private double[] Gy;
	private double[] Gz;

	private double xoff = 0;
	private double yoff = 0;
	private double zoff = 0;
	private double xscale = 1;
	private double yscale = 1;
	private double zscale = 1;
	private double outscale = 1;

	/**
	 * Creates a new perlin noise generator with a random seed.
	 */
	public PerlinGenerator() {
		random = new Random();
		init();
	}

	/**
	 * Creates a new perlin noise generator with a given seed.
	 * 
	 * @param seed the seed
	 */
	public PerlinGenerator(long seed) {
		random = new Random(seed);
		init();
	}

	/**
	 * Creates a new perlin noise generator with a pre-defined input sample
	 * scale and output scale.
	 * 
	 * @param xscale the x scale
	 * @param yscale the y scale
	 * @param zscale the z scale
	 * @param outscale the output scale
	 */
	public PerlinGenerator(double xscale, double yscale, double zscale, double outscale) {
		this();
		this.xscale = xscale;
		this.yscale = yscale;
		this.zscale = zscale;
		this.outscale = outscale;
	}

	/**
	 * Creates a new perlin noise generator with a given seed and a pre-defined
	 * input sample scale and output scale.
	 * 
	 * @param seed the seed
	 * @param xscale the x scale
	 * @param yscale the y scale
	 * @param zscale the z scale
	 * @param outscale the output scale
	 */
	public PerlinGenerator(long seed, double xscale, double yscale, double zscale, double outscale) {
		this(seed);
		this.xscale = xscale;
		this.yscale = yscale;
		this.zscale = zscale;
		this.outscale = outscale;
	}

	public PerlinGenerator(long seed, double xoff, double yoff, double zoff, double xscale, double yscale, double zscale, double outscale) {
		this(seed);
		this.xoff = xoff;
		this.yoff = yoff;
		this.zoff = zoff;
		this.xscale = xscale;
		this.yscale = yscale;
		this.zscale = zscale;
		this.outscale = outscale;
	}

	/**
	 * Initializes the arrays and maps.
	 */
	private void init() {
		p = new int[256];
		Gx = new double[256];
		Gy = new double[256];
		Gz = new double[256];

		for (int i = 0; i < 256; ++i) {
			p[i] = i;

			Gx[i] = ((double) (random.nextInt()) / (RAND_MAX / 2)) - 1.0f;
			Gy[i] = ((double) (random.nextInt()) / (RAND_MAX / 2)) - 1.0f;
			Gz[i] = ((double) (random.nextInt()) / (RAND_MAX / 2)) - 1.0f;
		}

		int j = 0;
		int swp = 0;
		for (int i = 0; i < 256; i++) {
			j = random.nextInt() & 255;

			swp = p[i];
			p[i] = p[j];
			p[j] = swp;
		}
	}

	/**
	 * Gets the noise with the given coordinates, ignoring any input and output
	 * scaling or translation.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 * @return the normalized value in the interval [-1, 1]
	 */
	public double unscaledNoise(double x, double y, double z) {
		// Unit cube vertex coordinates surrounding the sample point
		int x0 = (int) (Math.floor(x));
		int x1 = x0 + 1;
		int y0 = (int) (Math.floor(y));
		int y1 = y0 + 1;
		int z0 = (int) (Math.floor(z));
		int z1 = z0 + 1;

		// Determine sample point position within unit cube
		double px0 = x - x0;
		double px1 = px0 - 1.0f;
		double py0 = y - y0;
		double py1 = py0 - 1.0f;
		double pz0 = z - z0;
		double pz1 = pz0 - 1.0f;

		// Compute dot product between gradient and sample position vector
		int gIndex = p[(x0 + p[(y0 + p[z0 & 255]) & 255]) & 255];
		double d000 = Gx[gIndex] * px0 + Gy[gIndex] * py0 + Gz[gIndex] * pz0;
		gIndex = p[(x1 + p[(y0 + p[z0 & 255]) & 255]) & 255];
		double d001 = Gx[gIndex] * px1 + Gy[gIndex] * py0 + Gz[gIndex] * pz0;

		gIndex = p[(x0 + p[(y1 + p[z0 & 255]) & 255]) & 255];
		double d010 = Gx[gIndex] * px0 + Gy[gIndex] * py1 + Gz[gIndex] * pz0;
		gIndex = p[(x1 + p[(y1 + p[z0 & 255]) & 255]) & 255];
		double d011 = Gx[gIndex] * px1 + Gy[gIndex] * py1 + Gz[gIndex] * pz0;

		gIndex = p[(x0 + p[(y0 + p[z1 & 255]) & 255]) & 255];
		double d100 = Gx[gIndex] * px0 + Gy[gIndex] * py0 + Gz[gIndex] * pz1;
		gIndex = p[(x1 + p[(y0 + p[z1 & 255]) & 255]) & 255];
		double d101 = Gx[gIndex] * px1 + Gy[gIndex] * py0 + Gz[gIndex] * pz1;

		gIndex = p[(x0 + p[(y1 + p[z1 & 255]) & 255]) & 255];
		double d110 = Gx[gIndex] * px0 + Gy[gIndex] * py1 + Gz[gIndex] * pz1;
		gIndex = p[(x1 + p[(y1 + p[z1 & 255]) & 255]) & 255];
		double d111 = Gx[gIndex] * px1 + Gy[gIndex] * py1 + Gz[gIndex] * pz1;

		// Interpolate dot product values at sample point using polynomial
		// interpolation 6x^5 - 15x^4 + 10x^3
		double wx = ((6 * px0 - 15) * px0 + 10) * px0 * px0 * px0;
		double wy = ((6 * py0 - 15) * py0 + 10) * py0 * py0 * py0;
		double wz = ((6 * pz0 - 15) * pz0 + 10) * pz0 * pz0 * pz0;

		double xa = d000 + wx * (d001 - d000);
		double xb = d010 + wx * (d011 - d010);
		double xc = d100 + wx * (d101 - d100);
		double xd = d110 + wx * (d111 - d110);
		double ya = xa + wy * (xb - xa);
		double yb = xc + wy * (xd - xc);
		double value = ya + wz * (yb - ya);

		return value;
	}

	/**
	 * Gets the noise with the given coordinates, and applies scaling to the x,
	 * y, and z coordinates as well as the output.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 * @return the scaled noise value in the interval [-outscale, outscale]
	 */
	public double noise(double x, double y, double z) {
		return unscaledNoise((x + xoff) * xscale, (y + yoff) * yscale, (z + zoff) * zscale) * outscale;
	}
	
	public PerlinGenerator clone() {
		try {
			return (PerlinGenerator) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public double getXoff() {
		return xoff;
	}

	public void setXoff(double xoff) {
		this.xoff = xoff;
	}

	public double getYoff() {
		return yoff;
	}

	public void setYoff(double yoff) {
		this.yoff = yoff;
	}

	public double getZoff() {
		return zoff;
	}

	public void setZoff(double zoff) {
		this.zoff = zoff;
	}

	public double getXscale() {
		return xscale;
	}

	public void setXscale(double xscale) {
		this.xscale = xscale;
	}

	public double getYscale() {
		return yscale;
	}

	public void setYscale(double yscale) {
		this.yscale = yscale;
	}

	public double getZscale() {
		return zscale;
	}

	public void setZscale(double zscale) {
		this.zscale = zscale;
	}

	public double getOutscale() {
		return outscale;
	}

	public void setOutscale(double outscale) {
		this.outscale = outscale;
	}

}
