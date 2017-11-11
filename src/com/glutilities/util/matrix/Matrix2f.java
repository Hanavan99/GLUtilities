package com.glutilities.util.matrix;

import java.util.Arrays;

public class Matrix2f extends Matrixf<Matrix2f> {

	public static final Matrix2f IDENTITY_MATRIX = new Matrix2f(new float[] { 1, 0, 0, 1 });

	private float[] matrix;

	public Matrix2f() {
		super(2);
	}

	public Matrix2f(float[] matrix) {
		super(matrix, 2);
	}

	@Override
	public Matrix2f add(Matrix2f matrix) {
		float[] newMatrix = new float[size * size];
		for (int i = 0; i < newMatrix.length; i++) {
			newMatrix[i] = this.matrix[i] + matrix.matrix[i];
		}
		return new Matrix2f(newMatrix);
	}

	@Override
	public Matrix2f subtract(Matrix2f matrix) {
		float[] newMatrix = new float[size * size];
		for (int i = 0; i < newMatrix.length; i++) {
			newMatrix[i] = this.matrix[i] - matrix.matrix[i];
		}
		return new Matrix2f(newMatrix);
	}

	@Override
	public Matrix2f multiply(Matrix2f matrix) {
		float[] newMatrix = new float[size * size];
		for (int i = 0; i < newMatrix.length; i += 2) {
			newMatrix[i] = this.matrix[i] * matrix.matrix[i * size] + this.matrix[i + 1] * matrix.matrix[(i + 1) * size];
		}
		return new Matrix2f(newMatrix);
	}

	@Override
	public Matrix2f negate() {
		float[] newMatrix = new float[size * size];
		for (int i = 0; i < newMatrix.length; i++) {
			newMatrix[i] = -this.matrix[i];
		}
		return new Matrix2f(newMatrix);
	}
	
	@Override
	public Matrix2f set(int row, int col, float value) {
		float[] newMatrix = Arrays.copyOf(matrix, size * size);
		if (row >= 0 && col >= 0 && row < size && col < size) {
			newMatrix[row * size + col] = value;
			return new Matrix2f(newMatrix);
		}
		throw new IllegalArgumentException("Matrix element row=" + row + " col=" + col + " does not exist in matrix");
	}

	@Override
	public String toString() {
		return String.format("[%.5f %.5f]\n[%.5f %.5f]", matrix[0], matrix[1], matrix[2], matrix[3]);
	}

}
