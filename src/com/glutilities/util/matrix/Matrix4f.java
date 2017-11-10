package com.glutilities.util.matrix;

public class Matrix4f extends Matrixf<Matrix4f> {

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
		for (int i = 0; i < newMatrix.length; i += 2) {
			newMatrix[i] = this.matrix[i] * matrix.matrix[i * size] + this.matrix[i + 1] * matrix.matrix[(i + 1) * size] + this.matrix[i + 2] * matrix.matrix[(i + 2) * size] + this.matrix[i + 3] * matrix.matrix[(i + 3) * size];
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

}
