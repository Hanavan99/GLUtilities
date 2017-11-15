package com.glutilities.ui;

import com.glutilities.shader.ShaderProgram;

/**
 * Abstract class for defining a renderer for a particular type of object, or
 * for some type of UI element.
 * 
 * @author Hanavan99
 *
 */
public abstract class Renderer<E> {

	/**
	 * This method is called to set up this renderer, and the {@code render()}
	 * method is called every frame when the main loop is running. This method
	 * can be used to create any resources that will be used for rendering.
	 * 
	 * @param context the render context
	 */
	public void setup(RenderContext context) {
		// Does nothing by default
	}

	/**
	 * Renders a particular item on the screen using the specified shader
	 * program. A shader program should be passed in by the calling class. If no
	 * shader program has been written, there are default shader implementations
	 * that can be used for debugging purposes.
	 * 
	 * @param context the render context
	 * @param program the shader program used to render objects; should not be
	 *            null
	 * @param object the object to render
	 */
	public abstract void render(RenderContext context, ShaderProgram program, E object);

	/**
	 * Called when the program is closing, or when this renderer is no longer
	 * needed. This method should delete any resources consumed by this object.
	 */
	public void cleanup() {
		// does nothing by default
	}

}
