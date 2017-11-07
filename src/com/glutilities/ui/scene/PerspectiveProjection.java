package com.glutilities.ui.scene;

import org.lwjgl.opengl.GL11;

import com.glutilities.util.GLMath;

public class PerspectiveProjection extends SceneProjection {

	private float fov;
	private float znear;
	private float zfar;

	public PerspectiveProjection(float fov, float znear, float zfar) {
		this.fov = fov;
		this.znear = znear;
		this.zfar = zfar;
	}

	@Override
	public void setupProjectionMatrix(int width, int height, float aspect) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLMath.createPerspective(fov, aspect, znear, zfar);
	}

}
