package com.glutilities.terrain;

import java.util.ArrayList;
import java.util.List;

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
	 * Squares the octave for each octave. (Ex. scale, 4 * scale, 9 * scale, 16
	 * * scale, ...)
	 */
	public static final int OCTAVE_SQUARE = 0x7;

	/**
	 * Raises the octave to the -2nd power and multiplies it by the base scale.
	 * (Ex. scale, scale / 4, scale / 9, scale / 16, ...)
	 */
	public static final int OCTAVE_ONE_OVER_SQUARE = 0x8;
	
	/**
	 * Adds up all of the octaves of noise.
	 */
	public static final int NOISE_SUM = 0x10;
	
	/**
	 * Averages the noises produced by each octave.
	 */
	public static final int NOISE_AVERAGE = 0x11;
	
	/**
	 * Multiplies all of the octaves together.
	 */
	public static final int NOISE_MULTIPLY = 0x12;

	//private Random random = null;
	private final List<PerlinGenerator> generators = new ArrayList<PerlinGenerator>();
	private final int noiseMode;

	/**
	 * Creates a new fractal noise generator with a default noise mode of {@code NOISE_AVERAGE}.
	 */
	public FractalGenerator() {
		noiseMode = NOISE_SUM;
	}
	
	/**
	 * Creates a new fractal noise generator with the specified output noise mode.
	 * @param noiseMode the noise mode
	 */
	public FractalGenerator(int noiseMode) {
		if (noiseMode != NOISE_SUM && noiseMode != NOISE_AVERAGE && noiseMode != NOISE_MULTIPLY) {
			throw new IllegalArgumentException("Invalid noise mode");
		}
		this.noiseMode = noiseMode;
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
		for (int octave = 1; octave <= octaves; octave++) {
			generators.add(new PerlinGenerator(basexscale, baseyscale, basezscale, baseoutscale));
			switch (inOctaveMode) {
			case OCTAVE_NONE:
				break;
			case OCTAVE_DECREASE_BY_ONE:
				basexscale--;
				baseyscale--;
				basezscale--;
				break;
			case OCTAVE_INCREASE_BY_ONE:
				basexscale++;
				baseyscale++;
				basezscale++;
				break;
			case OCTAVE_LINEAR:
				basexscale += basexscale;
				baseyscale += baseyscale;
				basezscale += basezscale;
				break;
			case OCTAVE_DOUBLE:
				basexscale *= 2;
				baseyscale *= 2;
				basezscale *= 2;
				break;
			case OCTAVE_HALVE:
				basexscale /= 2;
				baseyscale /= 2;
				basezscale /= 2;
				break;
			case OCTAVE_ONE_OVER_OCTAVE:
				basexscale = 1.0 / octave;
				baseyscale = 1.0 / octave;
				basezscale = 1.0 / octave;
				break;
			case OCTAVE_SQUARE:
				basexscale += basexscale * (2 * octave + 1);
				baseyscale += baseyscale * (2 * octave + 1);
				basezscale += basezscale * (2 * octave + 1);
				break;
			case OCTAVE_ONE_OVER_SQUARE:
				basexscale += -1.0 / (octave * octave);
				baseyscale += -1.0 / (octave * octave);
				basezscale += -1.0 / (octave * octave);
				break;
			default:
				throw new IllegalArgumentException("Invalid input octave scaling mode");
			}
			
			switch (inOctaveMode) {
			case OCTAVE_NONE:
				break;
			case OCTAVE_DECREASE_BY_ONE:
				baseoutscale--;
				break;
			case OCTAVE_INCREASE_BY_ONE:
				baseoutscale++;
				break;
			case OCTAVE_LINEAR:
				baseoutscale += baseoutscale;
				break;
			case OCTAVE_DOUBLE:
				baseoutscale *= 2;
				break;
			case OCTAVE_HALVE:
				baseoutscale /= 2;
				break;
			case OCTAVE_ONE_OVER_OCTAVE:
				baseoutscale = 1.0 / octave;
				break;
			case OCTAVE_SQUARE:
				baseoutscale += basexscale * (2 * octave + 1);
				break;
			case OCTAVE_ONE_OVER_SQUARE:
				baseoutscale += -1.0 / (octave * octave);
				break;
			default:
				throw new IllegalArgumentException("Invalid output octave scaling mode");
			}
		}
	}
	
	public double noise(double x, double y, double z) {
		int count = 0;
		double result = (noiseMode == NOISE_MULTIPLY ? 1 : 0);
		for (PerlinGenerator generator : generators) {
			if (noiseMode == NOISE_MULTIPLY) {
				result *= generator.noise(x, y, z);
			} else {
				result += generator.noise(x, y, z);
			}
			count++;
		}
		if (noiseMode == NOISE_AVERAGE) {
			result /= count;
		}
		return result;
	}

}
