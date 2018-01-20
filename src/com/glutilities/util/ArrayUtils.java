package com.glutilities.util;

/**
 * Useful utility class for converting data.
 * 
 * @author Hanavan99
 */
public class ArrayUtils {

	/**
	 * Convert a {@code Float[]} array to a {@code float[]} array.
	 * 
	 * @param array
	 *            the array
	 * @return the primitive array
	 */
	public static float[] toPrimitiveArray(Float[] array) {
		float[] result = new float[array.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = array[i];
		}
		return result;
	}

	/**
	 * Convert a {@code Double[]} array to a {@code double[]} array.
	 * 
	 * @param array
	 *            the array
	 * @return the primitive array
	 */
	public static double[] toPrimitiveArray(Double[] array) {
		double[] result = new double[array.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = array[i];
		}
		return result;
	}

	/**
	 * Convert a {@code Integer[]} array to a {@code int[]} array.
	 * 
	 * @param array
	 *            the array
	 * @return the primitive array
	 */
	public static int[] toPrimitiveArray(Integer[] array) {
		int[] result = new int[array.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = array[i];
		}
		return result;
	}
	
	/**
	 * Convert a {@code Byte[]} array to a {@code byte[]} array.
	 * 
	 * @param array
	 *            the array
	 * @return the primitive array
	 */
	public static byte[] toPrimitiveArray(Byte[] array) {
		byte[] result = new byte[array.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = array[i];
		}
		return result;
	}

	public static Float[] toObjectArray(float[] array) {
		Float[] result = new Float[array.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = array[i];
		}
		return result;
	}
	
	public static Byte[] toObjectArray(byte[] array) {
		Byte[] result = new Byte[array.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = array[i];
		}
		return result;
	}
	
	public static Integer[] toObjectArray(int[] array) {
		Integer[] result = new Integer[array.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = array[i];
		}
		return result;
	}
	
	public static int[] convert(byte[] array) {
		int[] result = new int[array.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = array[i] < 0 ? 256 + (int) array[i] : (int) array[i];
		}
		return result;
	}

	/**
	 * Fills the array with the specified pattern. If the array size is not a
	 * multiple of the pattern size, the pattern will be truncated for the last
	 * values.
	 * 
	 * @param array
	 *            the array to fill
	 * @param pattern
	 *            the pattern
	 */
	public static void fill(float[] array, float[] pattern) {
		for (int i = 0; i < array.length; i++) {
			array[i] = pattern[i % pattern.length];
		}
	}
	
	/**
	 * {@code Object[]} version of {@link #fill(float[], float[])}.
	 * @param array
	 * @param pattern
	 */
	public static void fill(Object[] array, Object[] pattern) {
		for (int i = 0; i < array.length; i++) {
			array[i] = pattern[i % pattern.length];
		}
	}

	public static void fillmask(float[] array, float[] pattern, int mask) {
		for (int i = 0; i < array.length; i++) {
			if ((mask & (1 << (pattern.length - (i % pattern.length) - 1))) != 0) {
				array[i] = pattern[i % pattern.length];
			}
		}
	}
	
	public static void fillrandom(float[] array, int repeat) {
		float r = 0;
		float g = 0;
		float b = 0;
		for (int i = 0; i < array.length; i += 3) {
			if (i % repeat == 0) {
				r = (float) Math.random();
				g = (float) Math.random();
				b = (float) Math.random();
				
			}
			array[i] = r;
			array[i + 1] = g;
			array[i + 2] = b;
		}
	}
	
	public static void printArray(float[] array, int valsPerLine) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ", ");
			if (i % valsPerLine == valsPerLine - 1) {
				System.out.println();
			}
		}
	}

}
