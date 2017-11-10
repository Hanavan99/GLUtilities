package com.glutilities.buffer;

import org.lwjgl.opengl.ARBVertexArrayObject;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.glutilities.core.Bindable;
import com.glutilities.core.Reusable;

public class VBO implements Reusable, Bindable {

	private int vboVertices;
	private int vboColors;
	private int vboNormals;
	private int vboTexcoords;
	private int vboIndices;
	private int vao;
	private float[] vertices;
	private float[] colors;
	private float[] normals;
	private float[] texcoords;
	private int[] indices;
	private int drawMode = GL11.GL_TRIANGLES;

	public VBO(float[] vertices, float[] colors, float[] normals, float[] texcoords, int[] indices, int drawMode) {
		this.vertices = vertices;
		this.colors = colors;
		this.normals = normals;
		this.texcoords = texcoords;
		this.indices = indices;
		this.drawMode = drawMode;
	}

	public void draw() {
		// bind();
		// GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		// GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, vboData);
		// GL11.glDrawElements(drawMode, indices);
		// GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		ARBVertexArrayObject.glBindVertexArray(vao);
		// GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		// GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		// ARBVertexArrayObject.glB
		// GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0,
		// vboVertices);
		// GL20.glEnableVertexAttribArray(0);
		// GL11.glDrawElements(GL11.GL_TRIANGLES, indicesBuffer);
		if (indices != null) {
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, vboIndices);
			// GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
			// System.out.println(indicesBuffer.get(2));

			// GL11.glDrawElements(GL11.GL_TRIANGLES, indicesBuffer);
			GL11.glDrawElements(drawMode, indices.length, GL11.GL_UNSIGNED_INT, 0);
		} else {
			GL11.glDrawArrays(drawMode, 0, vertices.length);
		}
		// GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		// GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		ARBVertexArrayObject.glBindVertexArray(0);
		// unbind();
	}

	@Override
	public void create() {

		// create vertex buffer object
		if (vertices != null) {
			vboVertices = ARBVertexBufferObject.glGenBuffersARB();
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vboVertices);
			ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertices, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		}

		// create color buffer object
		if (colors != null) {
			vboColors = ARBVertexBufferObject.glGenBuffersARB();
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vboColors);
			ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, colors, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		}

		// create normal buffer object
		if (normals != null) {
			vboNormals = ARBVertexBufferObject.glGenBuffersARB();
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vboNormals);
			ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, normals, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		}

		// create texcoord buffer object
		if (texcoords != null) {
			vboTexcoords = ARBVertexBufferObject.glGenBuffersARB();
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vboTexcoords);
			ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, texcoords, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		}

		// create index buffer object
		if (indices != null) {
			vboIndices = ARBVertexBufferObject.glGenBuffersARB();
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, vboIndices);
			ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, indices, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		}

		// create vertex access object
		vao = ARBVertexArrayObject.glGenVertexArrays();
		ARBVertexArrayObject.glBindVertexArray(vao);
		if (vertices != null) {
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vboVertices);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
			GL20.glEnableVertexAttribArray(0);
		}
		if (colors != null) {
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vboColors);
			GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
			GL20.glEnableVertexAttribArray(1);
		}
		if (normals != null) {
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vboNormals);
			GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);
			GL20.glEnableVertexAttribArray(2);
		}
		if (texcoords != null) {
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vboTexcoords);
			GL20.glVertexAttribPointer(3, 2, GL11.GL_FLOAT, false, 0, 0);
			GL20.glEnableVertexAttribArray(3);
		}
		
	}

	@Override
	public void delete() {
		ARBVertexBufferObject.glDeleteBuffersARB(vboVertices);
		ARBVertexBufferObject.glDeleteBuffersARB(vboColors);
	}

	@Override
	public void bind() {
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vboVertices);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, vboColors);
	}

	@Override
	public void unbind() {
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, 0);
	}

}
