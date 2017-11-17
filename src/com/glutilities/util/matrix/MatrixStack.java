package com.glutilities.util.matrix;

import com.glutilities.util.Vertex3f;

public class MatrixStack {

	private Matrix4f[] stack = new Matrix4f[64];
	private int index = 0;

	public MatrixStack() {

	}

	/**
	 * Sets the current matrix value.
	 * 
	 * @param matrix the matrix;
	 */
	public void set(Matrix4f matrix) {
		stack[index] = matrix;
	}
	
	public Matrix4f get() {
		return stack[index];
	}
	
	public void translate(Vertex3f translation) {
		stack[index] = MatrixMath.translate(stack[index], translation);
	}
	
	public void scale(Vertex3f scale) {
		stack[index] = MatrixMath.scale(stack[index], scale);
	}
	
	public void setTranslation(Vertex3f position) {
		stack[index] = MatrixMath.setTranslation(stack[index], position);
	}
	
	public void setScale(Vertex3f scale) {
		stack[index] = MatrixMath.setScale(stack[index], scale);
	}

	/**
	 * Pushes a copy of the first matrix onto the stack. Any changes to this
	 * matrix will not affect matrices lower on the stack, but will affect
	 * matrices pushed onto the stack later.
	 */
	public void push() {
		if (index < stack.length - 1) {
			index++;
			stack[index] = (Matrix4f) stack[index - 1].clone();
		}
	}

	/**
	 * Takes the top matrix off of the stack.
	 */
	public void pop() {
		if (index > 0) {
			stack[index] = null;
			index--;
		}
	}

}
