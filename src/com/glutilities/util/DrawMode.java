package com.glutilities.util;

import org.lwjgl.opengl.GL11;

public enum DrawMode {

	POINTS(GL11.GL_POINTS), LINES(GL11.GL_LINES), TRIANGLES(GL11.GL_TRIANGLES), QUADS(GL11.GL_QUADS), LINE_STRIP(GL11.GL_LINE_STRIP), TRIANGLE_STRIP(GL11.GL_TRIANGLE_STRIP), QUAD_STRIP(GL11.GL_QUAD_STRIP);

	private int value;

	private DrawMode(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
