package com.glutilities.util;

import org.lwjgl.opengl.GL11;

import com.glutilities.util.matrix.Matrix4f;

public class GLMath {

	/**
	 * Creates a {@code float[]} array that holds pairs of x and y coordinates for a
	 * beziér curve.
	 * 
	 * @param points
	 *            The points in the curve
	 * @param samples
	 *            The number of pairs of x and y coordinates sampled from the curve
	 * @return The sampled coordinates
	 */
	public static double[] bezier(double[] points, int samples) {
		double[] sampleArray = new double[samples * 2];
		int[] pascal = pascal(points.length / 2 - 1);
		for (int j = 0; j < sampleArray.length; j += 2) {
			double x = 0;
			double y = 0;
			double t = (double) j / sampleArray.length;
			for (int i = 0; i < points.length; i += 2) {
				int pointIndex = i / 2;
				x += pascal[pointIndex] * Math.pow(1 - t, points.length / 2 - pointIndex - 1) * Math.pow(t, pointIndex)
						* points[i];
				y += pascal[pointIndex] * Math.pow(1 - t, points.length / 2 - pointIndex - 1) * Math.pow(t, pointIndex)
						* points[i + 1];

			}
			sampleArray[j] = x;
			sampleArray[j + 1] = y;
		}
		return sampleArray;
	}

	public static double[] quadBezier(double cpx, double cpy, double x1, double y1, double x2, double y2, double t) {
		double[] result = new double[2];
		double nt = 1 - t;
		result[0] = cpx * nt * nt + 2 * x1 * nt * t + x2 * t * t;
		result[1] = cpy * nt * nt + 2 * y1 * nt * t + y2 * t * t;
		return result;
	}

	public static float[] quadBezier(float cpx, float cpy, float x1, float y1, float x2, float y2, float t) {
		float[] result = new float[2];
		float nt = 1 - t;
		result[0] = cpx * nt * nt + 2 * x1 * nt * t + x2 * t * t;
		result[1] = cpy * nt * nt + 2 * y1 * nt * t + y2 * t * t;
		return result;
	}

	/**
	 * Generates the numbers of level n of Pascal's Triangle.
	 * 
	 * @param level
	 *            The level to create, level 0 being { 1 }, 1 = { 1, 1 }, etc.
	 * @return The numbers
	 */
	public static int[] pascal(int level) {
		int[] numbers = new int[level + 1];
		for (int i = 0; i < numbers.length / 2 + 1; i++) {
			numbers[i] = reverseFactorial(level, i) / factorial(i);
			numbers[numbers.length - i - 1] = reverseFactorial(level, i) / factorial(i);
		}
		return numbers;
	}

	public static int factorial(int n) {
		int result = 1;
		for (int i = 2; i < n; i++) {
			result *= i;
		}
		return result;
	}

	public static int reverseFactorial(int n, int c) {
		int result = 1;
		for (int i = n; i > n - c; i--) {
			result *= i;
		}
		return result;
	}

	public static void createPerspective(double fovy, double aspect, double znear, double zfar) {
		double fh = Math.tan(fovy / 360 * Math.PI) * znear;
		double fw = fh * aspect;
		GL11.glFrustum(-fw, fw, -fh, fh, znear, zfar);
	}

	

}
