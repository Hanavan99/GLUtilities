package com.glutilities.ui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.glutilities.core.Reusable;
import com.glutilities.shader.ShaderProgram;
import com.glutilities.ui.fbo.Framebuffer;
import com.glutilities.ui.scene.Scene;
import com.glutilities.ui.scene.SceneProjection;

/**
 * A GLFW window with an OpenGL context that can be used to render the game.
 * 
 * @author Hanavan99
 */
public class GLWindow implements Reusable {

	/**
	 * The window ID used by GLFW.
	 */
	private long window;

	/**
	 * The width of the window.
	 */
	private int width;

	/**
	 * The height of the window.
	 */
	private int height;

	/**
	 * The title of the window.
	 */
	private String title;

	/**
	 * The ID of the monitor to place the window on.
	 */
	private long monitor;

	/**
	 * The list of renderers that are used to render the game.
	 */
	private List<Renderer> renderers = new ArrayList<Renderer>();

	/**
	 * The shader program to use for post-processing and other effects.
	 */
	private ShaderProgram program;

	/**
	 * The scene used to set up the modelview and projection matrices.
	 */
	private Scene scene;

	/**
	 * True if this OpenGL context should use vsync to prevent screen tearing.
	 */
	private boolean vsync = true;

	private Framebuffer framebuffer;

	/**
	 * Keeps track of the total frames drawn since the last
	 * {@code resetFrameCounter()} call. When the window is created, this value
	 * 0.
	 */
	private int frameCounter = 0;

	/**
	 * Creates a new window with the specified dimensions, title, and monitor to
	 * render on. If monitor is 0, the width and height attributes describe the
	 * size of the window. If monitor is a valid identifier, width and height
	 * describe the window resolution.
	 * 
	 * @param width the width of the window in pixels
	 * @param height the height of the window in pixels
	 * @param title the title of the window
	 * @param monitor the monitor on which to place the window in fullscreen
	 *            mode. If 0, puts the window in windowed mode.
	 */
	public GLWindow(int width, int height, String title, long monitor, Scene scene) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.monitor = monitor;
		this.scene = scene;
	}

	/**
	 * Initializes the OpenGL context.
	 */
	public void init() {
		GL.createCapabilities();

		framebuffer = new Framebuffer(getWindowWidth(), getWindowHeight());
		framebuffer.unbind();

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SPECULAR, new float[] { 1, 1, 1, 1 });
		GL11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SHININESS, new float[] { 50, 0, 0, 0 });
		GL11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, new float[] { 15, 15, 15, 0 });
		GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE);

		GL11.glClearColor(0, 0, 0, 1);

	}

	/**
	 * The main loop used to render all of the scenes.
	 */
	public void loop() {
		while (!GLFW.glfwWindowShouldClose(window)) {
			final int[] windowSize = getWindowSize();
			final int width = windowSize[0];
			final int height = windowSize[1];
			final float aspect = (float) width / height;

			if (scene != null) {
				if (scene.shouldDrawToTexture()) {
					framebuffer.bind();
				}

				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
				GL11.glViewport(0, 0, width, height);

				SceneProjection ortho = scene.getOrthographicProjection();
				if (ortho != null) {
					ortho.setupProjectionMatrix(width, height, aspect);
					GL11.glMatrixMode(GL11.GL_MODELVIEW);
					GL11.glLoadIdentity();
					GL11.glPushMatrix();
					renderOrtho();
					GL11.glPopMatrix();
				}

				SceneProjection perspective = scene.getPerspectiveProjection();
				if (perspective != null) {
					perspective.setupProjectionMatrix(width, height, aspect);
					GL11.glMatrixMode(GL11.GL_MODELVIEW);
					GL11.glLoadIdentity();
					GL11.glPushMatrix();
					renderPerspective();
					GL11.glPopMatrix();
				}

				if (scene.shouldDrawToTexture()) {
					framebuffer.unbind();

					if (program != null) {
						program.enable();
					}

					GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
					GL11.glClearColor(0, 0, 0, 1);
					GL11.glViewport(0, 0, width, height);

					GL11.glMatrixMode(GL11.GL_PROJECTION);
					GL11.glLoadIdentity();
					GL11.glOrtho(0, 1, 0, 1, -1, 1);

					GL11.glMatrixMode(GL11.GL_MODELVIEW);
					GL11.glLoadIdentity();
					GL11.glPushMatrix();
					GL11.glDisable(GL11.GL_LIGHTING);
					framebuffer.renderToQuad();
					GL11.glPopMatrix();

					if (program != null) {
						program.disable();
					}
				}
			}

			GLFW.glfwPollEvents();
			GLFW.glfwSwapInterval(vsync ? 1 : 0);
			GLFW.glfwSwapBuffers(window);
			frameCounter++;
		}
	}

	/**
	 * Gets the width of the window in pixels. Shorthand for
	 * {@code getWindowSize()[0]}.
	 * 
	 * @return the width of the window
	 */
	public int getWindowWidth() {
		return getWindowSize()[0];
	}

	/**
	 * Gets the height of the window in pixels. Shorthand for
	 * {@code getWindowSize()[1]}.
	 * 
	 * @return the height of the window
	 */
	public int getWindowHeight() {
		return getWindowSize()[1];
	}

	/**
	 * Gets the size of the window in pixels. Index 0 of the resulting array is
	 * the width, and the index 1 is the height.
	 * 
	 * @return the dimensions of the window
	 */
	public int[] getWindowSize() {
		int[] width = new int[1], height = new int[1];
		GLFW.glfwGetWindowSize(window, width, height);
		return new int[] { width[0], height[0] };
	}

	/**
	 * Gets the aspect ratio of the window; shorthand for
	 * {@code width / height}.
	 * 
	 * @return the aspect ratio
	 */
	public float getWindowAspect() {
		int[] windowSize = getWindowSize();
		return (float) windowSize[0] / windowSize[1];
	}

	/**
	 * Returns if vsync is enabled for this window.
	 * 
	 * @return true if enabled; false if disabled
	 */
	public boolean getVsyncEnabled() {
		return vsync;
	}

	/**
	 * Sets the width of the window, without changing the height.
	 * 
	 * @param width the width in pixels
	 */
	public void setWindowWidth(int width) {
		setWindowSize(width, getWindowHeight());
	}

	/**
	 * Sets the height of the window, without changing the width.
	 * 
	 * @param height the height in pixels
	 */
	public void setWindowHeight(int height) {
		setWindowSize(getWindowWidth(), height);
	}

	/**
	 * Sets the window size in pixels.
	 * 
	 * @param width the width of the window
	 * @param height the height of the window
	 */
	public void setWindowSize(int width, int height) {
		GLFW.glfwSetWindowSize(window, width, height);
	}

	/**
	 * Sets the window size in pixels. The array must have at least 2 items, and
	 * if there are more, they are ignored. Index 0 is the width, and index 1 is
	 * the height.
	 * 
	 * @param size the dimensions of the window
	 */
	public void setWindowSize(int[] size) {
		if (size.length >= 2) {
			setWindowSize(size[0], size[1]);
		}
	}

	/**
	 * Enables vsync on the window. If enabled, the window will not try to
	 * render another frame until the previous one has been drawn on the screen.
	 * This means that the framerate of the window is capped at the refresh rate
	 * of the monitor. If disabled, the window will render frames as fast as
	 * possible. By default, vsync is on.
	 * 
	 * @param vsync true to enable; false to disable
	 */
	public void setVsyncEnabled(boolean vsync) {
		this.vsync = vsync;
	}

	/**
	 * Sets the post-processing shader to the provided object.
	 * 
	 * @param program the program
	 */
	public void setShaderProgram(ShaderProgram program) {
		this.program = program;
	}

	/**
	 * Adds a renderer to the window.
	 * 
	 * @param r the renderer to add
	 */
	public void addRenderer(Renderer r) {
		renderers.add(r);
	}

	/**
	 * Determines if the window is closing, either by the pressing of the close
	 * window button, or by some other form of killing the process.
	 * 
	 * @return if the window is closing
	 */
	public boolean isClosing() {
		return GLFW.glfwWindowShouldClose(window);
	}

	/**
	 * Gets the number of frames drawn since the last call to this method and
	 * resets the counter.
	 * 
	 * @return the number of previous frames drawn
	 */
	public int resetFrameCounter() {
		int result = frameCounter;
		frameCounter = 0;
		return result;
	}

	/**
	 * Binds the OpenGL context to the current thread.
	 */
	public void makeContextCurrent() {
		GLFW.glfwMakeContextCurrent(window);
	}

	@Override
	public void create() {
		window = GLFW.glfwCreateWindow(width, height, title, monitor, 0);
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwShowWindow(window);
		GLFW.glfwSetWindowSizeCallback(window, GLFWWindowSizeCallback.create(new GLFWWindowSizeCallbackI() {
			@Override
			public void invoke(long window, int width, int height) {
				if (scene.shouldDrawToTexture()) {
					framebuffer.delete();
					framebuffer.setSize(width, height);
					framebuffer.create();
				}
			}
		}));
	}

	@Override
	public void delete() {
		GLFW.glfwDestroyWindow(window);
	}

	/**
	 * Renders the orthographic modelview.
	 */
	private void renderOrtho() {
		for (Renderer r : renderers) {
			if (r.getRenderMode() == Renderer.ORTHOGRAPHIC_SCENE) {
				GL11.glPushMatrix();
				r.render();
				GL11.glPopMatrix();
			}
		}
	}

	/**
	 * Renders the perspective modelview.
	 */
	private void renderPerspective() {
		for (Renderer r : renderers) {
			if (r.getRenderMode() == Renderer.PERSPECTIVE_SCENE) {
				GL11.glPushMatrix();
				r.render();
				GL11.glPopMatrix();
			}
		}
	}

}
