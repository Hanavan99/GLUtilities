package com.glutilities.gui.scene;

import org.lwjgl.opengl.GL11;

import com.glutilities.gui.SceneProjection;

public class OrthographicProjection extends SceneProjection {

	private float left;
	private float right;
	private float bottom;
	private float top;
	private float near;
	private float far;

	/**
	 * Creates a new orthographic scene projection.
	 * 
	 * @param left
	 *            the left-most bound of the viewport
	 * @param right
	 *            the right-most bound of the viewport
	 * @param bottom
	 *            the lower-most bound of the viewport
	 * @param top
	 *            the upper-most bound of the viewport
	 * @param near
	 *            the nearest z-value visible
	 * @param far
	 *            the furthest z-value visible
	 */
	public OrthographicProjection(float left, float right, float bottom, float top, float near, float far) {
		this.left = left;
		this.right = right;
		this.bottom = bottom;
		this.top = top;
		this.near = near;
		this.far = far;
	}

	@Override
	public void setupProjectionMatrix(int width, int height, float aspect) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(left, right, bottom, top, near, far);
	}

}
