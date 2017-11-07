package com.glutilities.terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates a more advanced and detailed noise generator by summing the values of
 * many perlin noise generators to get more interesting surfaces.
 * 
 * @author Hanavan99
 *
 */
public class FractalGenerator {

	/**
	 * Apply no scaling to the subsequent octaves. (Ex. scale, scale, scale,
	 * scale, ...)
	 */
	public static final int OCTAVE_NONE = 0x0;

	/**
	 * Subtract one from the scale for each octave. (Ex. scale, scale - 1, scale
	 * - 2, scale - 3, ...)
	 */
	public static final int OCTAVE_DECREASE_BY_ONE = 0x1;

	/**
	 * Add one to the scale for each octave. (Ex. scale, scale + 1, scale + 2,
	 * scale + 3, ...)
	 */
	public static final int OCTAVE_INCREASE_BY_ONE = 0x2;

	/**
	 * Scales the scale by itself for each octave. (Ex. scale, 2 * scale, 3 *
	 * scale, 4 * scale, ...)
	 */
	public static final int OCTAVE_LINEAR = 0x3;

	/**
	 * Double the scale for each octave. (Ex. scale, 2 * scale, 4 * scale, 8 *
	 * scale, ...)
	 */
	public static final int OCTAVE_DOUBLE = 0x4;

	/**
	 * Halve the scale for each octave. (Ex. scale, scale / 2, scale / 4, scale
	 * / 8, ...)
	 */
	public static final int OCTAVE_HALVE = 0x5;

	/**
	 * Divides the scale by the current octave. (Ex. scale, scale / 2, scale /
	 * 3, scale / 4, ...)
	 */
	public static final int OCTAVE_ONE_OVER_OCTAVE = 0x6;

	/**
	 * Squares the scale for each octave. (Ex. scale, scale<sup>2</sup>,
	 * scale<sup>4</sup>, scale<sup>8</sup>, ...)
	 */
	public static final int OCTAVE_SQUARE = 0x7;

	private Random random = null;
	private List<PerlinGenerator> generators = new ArrayList<PerlinGenerator>();

	/**
	 * Creates a new fractal noise generator.
	 */
	public FractalGenerator() {

	}

	/**
	 * Adds a new perlin noise generator to the generator list.
	 * 
	 * @param xscale the x scale
	 * @param yscale the y scale
	 * @param zscale the z scale
	 * @param outscale the output scale
	 */
	public void addPerlinGenerator(double xscale, double yscale, double zscale, double outscale) {
		generators.add(new PerlinGenerator(xscale, yscale, zscale, outscale));
	}

	/**
	 * Adds several perlin noise generators to the list using an octave to
	 * determine the influence of each perlin generator.
	 * 
	 * @param basexscale the base x scale of the noise generators
	 * @param baseyscale the base y scale of the noise generators
	 * @param basezscale the base z scale of the noise generators
	 * @param baseoutscale the base output scale of the noise generators
	 * @param octaves the number of octaves to add
	 * @param inOctaveMode the input octave mode used to determine the input
	 *            scales of each octave
	 * @param outOctaveMode the output octave mode used to determine the output
	 *            scales of each octave
	 */
	public void addPerlinGenerators(double basexscale, double baseyscale, double basezscale, double baseoutscale, int octaves, int inOctaveMode, int outOctaveMode) {
		for (int i = 0; i < octaves; i++) {

		}
	}

}
