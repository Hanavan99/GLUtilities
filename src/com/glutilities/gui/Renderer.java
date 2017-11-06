package com.glutilities.gui;

/**
 * Abstract class for defining a renderer for a scene or UI system.
 * 
 * @author Hanavan99
 *
 */
public abstract class Renderer {

	/**
	 * Tells the renderer to use the perspective scene described in the
	 * {@code Scene} class.
	 */
	public static final int PERSPECTIVE_SCENE = 0x0;
	/**
	 * Tells the renderer to use the orthographic scene described in the
	 * {@code Scene} class.
	 */
	public static final int ORTHOGRAPHIC_SCENE = 0x1;
	/**
	 * Tells the renderer to call the {@code setup()} method which will set up a
	 * specific scene for this renderer.
	 */
	public static final int CUSTOM_SCENE = 0x2;

	/**
	 * The render mode that determines how this renderer is used.
	 */
	private final int renderMode;

	/**
	 * Creates a new {@code Renderer} object with the specified render mode. If an
	 * invalid render mode is defined, the scene will not be rendered.
	 * 
	 * @param renderMode
	 *            The render mode
	 */
	public Renderer(int renderMode) {
		this.renderMode = renderMode;
	}

	/**
	 * Gets the render mode of the renderer.
	 * 
	 * @return the render mode
	 */
	public int getRenderMode() {
		return renderMode;
	}

	/**
	 * If the render mode is set to {@code CUSTOM_SCENE}, this method is called to
	 * set up that scene, and the {@code render()} method is called directly after.
	 * 
	 * @param width
	 *            the width of the viewort
	 * @param height
	 *            the height of the viewport
	 * @param aspect
	 *            the aspect ratio of the viewport; width / height
	 */
	public void setup(int width, int height, float aspect) {
		// Does nothing by default
	}

	/**
	 * Renders the scene.
	 */
	public abstract void render();

}
