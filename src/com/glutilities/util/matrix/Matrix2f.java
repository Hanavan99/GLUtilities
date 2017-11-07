package com.glutilities.util.matrix;

import org.lwjgl.opengl.GL11;

public class Matrix2f {

	public static final int SIZE = 2;
	public static final Matrix2f IDENTITY_MATRIX = new Matrix2f(new float[] { 1, 0, 0, 1 });

	private float[] matrix;

	public Matrix2f() {
		matrix = new float[4];
	}

	public Matrix2f(float[] matrix) {
		this.matrix = matrix;
	}

	public Matrix2f add(Matrix2f matrix) {
		float[] newMatrix = new float[SIZE * SIZE];
		for (int i = 0; i < newMatrix.length; i++) {
			newMatrix[i] = this.matrix[i] + matrix.matrix[i];
		}
		return new Matrix2f(newMatrix);
	}

	public Matrix2f subtract(Matrix2f matrix) {
		return add(matrix.negate());
	}
	
	public Matrix2f multiply(Matrix2f matrix) {
		float[] newMatrix = new float[SIZE * SIZE];
		for (int i = 0; i < newMatrix.length; i += 2) {
			newMatrix[i] = this.matrix[i] * matrix.matrix[i * SIZE] + this.matrix[i + 1] * matrix.matrix[(i + 1) * SIZE];
		}
		return new Matrix2f(newMatrix);
	}

	public Matrix2f negate() {
		float[] newMatrix = new float[SIZE * SIZE];
		for (int i = 0; i < newMatrix.length; i++) {
			newMatrix[i] = -this.matrix[i];
		}
		return new Matrix2f(newMatrix);
	}

	public float get(int col, int row) {
		return matrix[row / SIZE + col];
	}

	public void glLoadMatrixf() {
		GL11.glLoadMatrixf(matrix);
	}
	
	@Override
	public String toString() {
		return String.format("[%.5f %.5f]\n[%.5f %.5f]", matrix[0], matrix[1], matrix[2], matrix[3]);
	}

}
