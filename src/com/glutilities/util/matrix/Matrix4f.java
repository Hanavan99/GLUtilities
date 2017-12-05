package com.glutilities.util.matrix;

import java.util.Arrays;

public class Matrix4f extends Matrixf<Matrix4f> {

	public static final Matrix4f IDENTITY_MATRIX = new Matrix4f(new float[] { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 });

	public Matrix4f() {
		super(4);
	}

	public Matrix4f(float[] matrix) {
		super(matrix, 4);
	}

	@Override
	public Matrix4f add(Matrix4f matrix) {
		float[] newMatrix = new float[size * size];
		for (int i = 0; i < newMatrix.length; i++) {
			newMatrix[i] = this.matrix[i] + matrix.matrix[i];
		}
		return new Matrix4f(newMatrix);
	}

	@Override
	public Matrix4f subtract(Matrix4f matrix) {
		float[] newMatrix = new float[size * size];
		for (int i = 0; i < newMatrix.length; i++) {
			newMatrix[i] = this.matrix[i] - matrix.matrix[i];
		}
		return new Matrix4f(newMatrix);
	}

	@Override
	public Matrix4f multiply(Matrix4f matrix) {
		float[] newMatrix = new float[size * size];
		for (int i = 0; i < newMatrix.length; i++) {
			int row = i / size;
			int col = i % size;
			float sum = 0;
			for (int j = 0; j < size; j++) {
				sum += this.matrix[row * size + j] * matrix.matrix[j * size + col];
			}
			newMatrix[i] = sum;
		}
		return new Matrix4f(newMatrix);
	}

	@Override
	public Matrix4f negate() {
		float[] newMatrix = new float[size * size];
		for (int i = 0; i < newMatrix.length; i++) {
			newMatrix[i] = -this.matrix[i];
		}
		return new Matrix4f(newMatrix);
	}
	
	@Override
	public Matrix4f set(int row, int col, float value) {
		float[] newMatrix = Arrays.copyOf(matrix, size * size);
		if (row >= 0 && col >= 0 && row < size && col < size) {
			newMatrix[row * size + col] = value;
			return new Matrix4f(newMatrix);
		}
		throw new IllegalArgumentException("Matrix element row=" + row + " col=" + col + " does not exist in matrix");
	}

}
