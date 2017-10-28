package com.glutilities.model;

import org.lwjgl.opengl.GL11;

public class ModelMaterial {

	public static final float[] DEFAULT_SPECULAR = new float[] { 1, 1, 1, 1 };
	// public static final float[] DEFAULT_DIFFUSE = new float[] {
	public static final float[] DEFAULT_SHININESS = new float[] { 50, 0, 0, 0 };

	private final float[] ambientColor;
	private final float[] diffuseReflection;
	private final float[] specularReflection;
	private final float[] shininess;

	public ModelMaterial(float[] ambientColor, float[] diffuseReflection, float[] specularReflection, float shininess) {
		this.ambientColor = ambientColor;
		this.diffuseReflection = diffuseReflection;
		this.specularReflection = specularReflection;
		this.shininess = new float[] { shininess, 0, 0, 0 };
	}

	public void setupLighting() {
		GL11.glColor4fv(ambientColor);
		if (specularReflection != null) {
			GL11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SPECULAR, specularReflection);
		} else {
			GL11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SPECULAR, DEFAULT_SPECULAR);
		}
		if (shininess != null) {
			GL11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SHININESS, shininess);
		} else {
			GL11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SHININESS, DEFAULT_SHININESS);
		}
		GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE);
	}

}
