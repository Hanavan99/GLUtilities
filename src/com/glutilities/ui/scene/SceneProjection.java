package com.glutilities.ui.scene;

/**
 * Defines how to setup an OpenGL context for rendering specific scenes.
 * 
 * @author Hanavan99
 *
 */
public abstract class SceneProjection {

	/**
	 * Called to set up the OpenGL projection matrix for rendering.
	 * 
	 * @param width
	 *            the width of the viewport
	 * @param height
	 *            the height of the viewport
	 * @param aspect
	 *            the aspect ratio of the viewport; width / height
	 */
	public abstract void setupProjectionMatrix(int width, int height, float aspect);

}
