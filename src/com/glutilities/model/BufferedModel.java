package com.glutilities.model;

import com.glutilities.buffer.VBO;

public class BufferedModel {

	private final String modelName;
	private VBO vbo;
	
	public BufferedModel(String modelName, float[] vertices, float[] colors, float[] normals, float[] texcoords, int[] indices, int drawMode) {
		this.modelName = modelName;
		vbo = new VBO(vertices, colors, normals, texcoords, indices, drawMode);
		vbo.create();
	}
	
	public String getModelName() {
		return modelName;
	}
	
	public void draw() {
		vbo.draw();
	}
	
}
