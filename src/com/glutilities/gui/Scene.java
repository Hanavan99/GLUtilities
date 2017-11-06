package com.glutilities.gui;

/**
 * Describes the context in which a {@code GLWindow} will render the scene.
 * 
 * @author Hanavan99
 */
public class Scene {

	/**
	 * The global orthographic projection to use for drawing UI elements
	 */
	private SceneProjection orthoProjection;

	/**
	 * The global perspective projection to use for drawing the game
	 */
	private SceneProjection perspectiveProjection;

	/**
	 * Whether or not the window should render directly to the screen or first to a
	 * texture buffer. Mainly used if there are post-processing shaders in use.
	 */
	private final boolean drawToTexture;

	/**
	 * Creates a new {@code Scene} object in which to use for setting up rendering
	 * contexts for the scene.
	 * 
	 * @param orthoProjection
	 *            the global orthographic projection to use for drawing UI elements
	 * @param perspectiveProjection
	 *            the global perspective projection to use for drawing the game
	 * @param drawToTexture
	 *            whether or not to first draw the scene to a texture for easier use
	 *            of post-processing shaders
	 */
	public Scene(SceneProjection orthoProjection, SceneProjection perspectiveProjection, boolean drawToTexture) {
		this.orthoProjection = orthoProjection;
		this.perspectiveProjection = perspectiveProjection;
		this.drawToTexture = drawToTexture;
	}

	/**
	 * Gets the global orthographic projection
	 * 
	 * @return the projection
	 */
	public SceneProjection getOrthographicProjection() {
		return orthoProjection;
	}

	/**
	 * Gets the global perspective projection
	 * 
	 * @return the projection
	 */
	public SceneProjection getPerspectiveProjection() {
		return perspectiveProjection;
	}

	/**
	 * Gets whether or not OpenGL should render the scene to a texture before
	 * drawing to the screen.
	 * 
	 * @return if it should draw to a texture
	 */
	public boolean shouldDrawToTexture() {
		return drawToTexture;
	}

}
