package com.glutilities.buffer;

import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.glutilities.core.Reusable;
import com.glutilities.model.AttributeArray;
import com.glutilities.util.ArrayUtils;

public class VBO implements Reusable {

	private int vao;
	private int vboIndices;
	private int[] attributeIDs;
	private AttributeArray[] attributes;
	private int count;
	private int[] indices;
	private int drawMode = GL11.GL_TRIANGLES;

	public VBO(AttributeArray[] attributes, int count, int drawMode) {
		this.attributes = attributes;
		this.count = count;
		this.drawMode = drawMode;
	}

	public VBO(AttributeArray[] attributes, int[] indices, int count, int drawMode) {
		this.attributes = attributes;
		this.indices = indices;
		this.count = count;
		this.drawMode = drawMode;
	}

	/**
	 * Draws each vertex in this Vertex Buffer Object sequentially, along with
	 * its color, normal, and texture coordinate compadres.
	 */
	public void draw() {
		ARBVertexArrayObject.glBindVertexArray(vao);
		if (indices != null) {
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, vboIndices);
			GL11.glDrawElements(drawMode, indices.length, GL11.GL_UNSIGNED_INT, 0);
		} else {
			GL11.glDrawArrays(drawMode, 0, count);
		}
		ARBVertexArrayObject.glBindVertexArray(0);
	}

	/**
	 * Same as {@code draw()}, but allows you to specify a specific subset of
	 * vertices to draw. Must be called after the {@code bind()} method, or
	 * nothing will be drawn.
	 * 
	 * @param start the index of the first element to draw
	 * @param count the number of vertices to draw
	 */
	public void draw(int start, int count) {
		GL11.glDrawArrays(drawMode, start, count);
	}

	public void set(String attributeName, AttributeArray value) {
		for (int i = 0; i < attributes.length; i++) {
			if (attributes[i].getName().equals(attributeName)) {
				attributes[i] = value;
			}
		}
	}

	public void bind() {
		ARBVertexArrayObject.glBindVertexArray(vao);
	}

	public void unbind() {
		ARBVertexArrayObject.glBindVertexArray(0);
	}

	@Override
	public void create() {
		attributeIDs = new int[attributes.length];
		vao = ARBVertexArrayObject.glGenVertexArrays();
		ARBVertexArrayObject.glBindVertexArray(vao);
		for (int i = 0; i < attributes.length; i++) {
			AttributeArray list = attributes[i];
			attributeIDs[i] = ARBVertexBufferObject.glGenBuffersARB();
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, attributeIDs[i]);
			Object[] data = list.getData();
			int dataType = 0;
			if (data instanceof Float[]) {
				ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, ArrayUtils.toPrimitiveArray((Float[]) data), ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
				dataType = GL11.GL_FLOAT;
			} else {
				throw new IllegalArgumentException("Data in AttributeList of type '" + data.getClass() + "' is not valid");
			}
			GL20.glVertexAttribPointer(list.getIndex(), list.getSize(), dataType, false, 0, 0);
			GL20.glEnableVertexAttribArray(list.getIndex());
		}

		if (indices != null) {
			vboIndices = ARBVertexBufferObject.glGenBuffersARB();
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, vboIndices);
			ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, indices, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		}

		ARBVertexArrayObject.glBindVertexArray(0);
	}

	@Override
	public void delete() {
		for (int i = 0; i < attributes.length; i++) {
			ARBVertexBufferObject.glDeleteBuffersARB(attributeIDs[i]);
		}
	}

}
