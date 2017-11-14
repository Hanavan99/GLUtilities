package com.glutilities.util.matrix;

import org.lwjgl.opengl.GL11;

import com.glutilities.util.ArrayUtils;

public abstract class Matrixf<E> implements Cloneable {

	protected final float[] matrix;
	protected final int size;

	public Matrixf(int size) {
		matrix = new float[size * size];
		this.size = size;
	}

	public Matrixf(float[] matrix, int size) {
		if (matrix.length != size * size) {
			throw new IllegalArgumentException("Matrix is not of correct size");
		}
		this.matrix = matrix;
		this.size = size;

	}
	
	public float[] getMatrix() {
		return matrix;
	}

	public abstract E add(E matrix);

	public abstract E subtract(E matrix);

	public abstract E multiply(E matrix);

	public abstract E negate();

	public float get(int row, int col) {
		if (row >= 0 && col >= 0 && row < size && col < size) {
			return matrix[row * size + col];
		}
		throw new IllegalArgumentException("Matrix element row=" + row + " col=" + col + " does not exist in matrix");
	}
	
	public abstract E set(int row, int col, float value);

	public void glLoadMatrixf() {
		GL11.glLoadMatrixf(matrix);
	}
	
	@Override
	public String toString() {
		String rowFormat = "[";
		String format = "";
		for (int i = 0; i < size; i++) {
			rowFormat += "%.5f";
			if (i < size - 1) {
				rowFormat += " ";
			}
		}
		rowFormat += "]";
		for (int i = 0; i < size; i++) {
			format += rowFormat;
			if (i < size - 1) {
				format += "\n";
			}
		}
		return String.format(format, (Object[]) ArrayUtils.toObjectArray(matrix));
	}
	
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

}
