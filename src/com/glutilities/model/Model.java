package com.glutilities.model;

import org.lwjgl.opengl.GL11;

public class Model {

	private final String modelName;
	private final float[] vertices;
	private final float[] normals;
	private final float[] texcoords;
	private final int[] draworder;
	private final ModelMaterial[] materials;

	public Model(String modelName, float[] vertices, float[] normals, float[] texcoords, int[] draworder, ModelMaterial[] materials) {
		this.modelName = modelName;
		this.vertices = vertices;
		this.normals = normals;
		this.texcoords = texcoords;
		this.draworder = draworder;
		this.materials = materials;
	}
	
	public String getModelName() {
		return modelName;
	}
	
	public void draw(int[] draworder) {
		int index = 0;
		GL11.glBegin(GL11.GL_TRIANGLES);
		while (index < draworder.length) {
			int coord = draworder[index] * 3;
			// int tex = drawi[index + 1] * 3;
			int norm = draworder[index + 2] * 3;
			if (norm != -1) {
				float na = normals[norm];
				float nb = normals[norm + 1];
				float nc = normals[norm + 2];
				GL11.glNormal3f(na, nb, nc);
			}
			if (coord != -1) {
				float ca = vertices[coord];
				float cb = vertices[coord + 1];
				float cc = vertices[coord + 2];
				GL11.glVertex3f(ca, cb, cc);
			}
			index += 3;
		}
		GL11.glEnd();
	}

	public void draw() {
		draw(draworder);
	}

}
