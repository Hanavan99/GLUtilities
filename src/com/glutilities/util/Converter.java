package com.glutilities.util;

/**
 * Useful utility class for converting data.
 * 
 * @author Hanavan99
 */
public class Converter {

	/**
	 * Convert a {@code Float[]} array to a {@code float[]} array.
	 * 
	 * @param array the array
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
	 * @param array the array
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
	 * @param array the array
	 * @return the primitive array
	 */
	public static int[] toPrimitiveArray(Integer[] array) {
		int[] result = new int[array.length];
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

}
