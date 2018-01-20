package com.glutilities.util;

import org.lwjgl.opengl.GL11;

public class OpenGL {

	public static void setViewport(int width, int height) {
		GL11.glViewport(0, 0, width, height);
	}

	public static void clearBuffer() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
	}

	public static void setClearColor(Vertex4f color) {
		GL11.glClearColor(color.getR(), color.getG(), color.getB(), color.getA());
	}

}
