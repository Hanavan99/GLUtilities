package com.glutilities.ui;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.glutilities.core.Reusable;

/**
 * A GLFW window with an OpenGL context that can be used to render the game.
 * 
 * @author Hanavan99
 */
public final class GLWindow implements Reusable {

	private final RenderContext context;

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
	 * True if this OpenGL context should use vsync to prevent screen tearing.
	 */
	private boolean vsync = true;

	/**
	 * Keeps track of the total frames drawn since the last
	 * {@code resetFrameCounter()} call. When the window is created, this value
	 * 0.
	 */
	private int frameCounter = 0;

	/**
	 * The master renderer that is used to draw on the screen each frame.
	 */
	private MasterRenderer renderer;

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
	public GLWindow(int width, int height, String title, long monitor) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.monitor = monitor;
		context = new RenderContext() {

			@Override
			public int getWidth() {
				return GLWindow.this.getWindowWidth();
			}

			@Override
			public int getHeight() {
				return GLWindow.this.getWindowHeight();
			}

		};
	}

	/**
	 * Initializes the OpenGL context.
	 */
	public void init() {
		GL.createCapabilities();

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
		if (renderer != null) {
			renderer.init(context);
		}

		linkCallbacksToRenderer();
	}

	public void linkCallbacksToRenderer() {
		if (renderer != null) {
			GLFW.glfwSetKeyCallback(window, GLFWKeyCallback.create((window, key, scancode, action, mods) -> renderer.keyPressed(context, key, action)));
			GLFW.glfwSetMouseButtonCallback(window, GLFWMouseButtonCallback.create((window, button, action, mods) -> renderer.mouseClicked(context, button, action)));
			GLFW.glfwSetCursorPosCallback(window, GLFWCursorPosCallback.create((window, xpos, ypos) -> renderer.mouseMoved(context, (int) xpos, (int) ypos)));
			GLFW.glfwSetCharCallback(window, GLFWCharCallback.create((window, codepoint) -> renderer.textTyped(context, codepoint)));
		}
	}

	/**
	 * The main loop used to render all of the scenes.
	 */
	public void loop() {
		while (!GLFW.glfwWindowShouldClose(window)) {

			if (renderer != null) {
				renderer.render(context);
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
		return width;
	}

	/**
	 * Gets the height of the window in pixels. Shorthand for
	 * {@code getWindowSize()[1]}.
	 * 
	 * @return the height of the window
	 */
	public int getWindowHeight() {
		return height;
	}

	/**
	 * Gets the size of the window in pixels. Index 0 of the resulting array is
	 * the width, and the index 1 is the height.
	 * 
	 * @return the dimensions of the window
	 */
	public int[] getWindowSize() {
		return new int[] { width, height };
	}

	/**
	 * Gets the aspect ratio of the window; shorthand for
	 * {@code width / height}.
	 * 
	 * @return the aspect ratio
	 */
	public float getWindowAspect() {
		return (float) width / height;
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
	 * Gets the master renderer that is used to draw on the screen each frame.
	 * 
	 * @return the renderer
	 */
	public MasterRenderer getMasterRenderer() {
		return renderer;
	}

	/**
	 * Gets the state of any key on the keyboard.
	 * 
	 * @param key the key
	 * @return the state of the key
	 */
	public int getKey(int key) {
		return GLFW.glfwGetKey(window, key);
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
	 * Sets the master renderer that is used to draw on the screen each frame.
	 * 
	 * @param renderer the renderer
	 */
	public void setMasterRenderer(MasterRenderer renderer) {
		this.renderer = renderer;
	}

	/**
	 * Sets the key callback for this window
	 * 
	 * @param callback the callback
	 */
	public void setKeyCallback(GLFWKeyCallbackI callback) {
		GLFW.glfwSetKeyCallback(window, GLFWKeyCallback.create(callback));
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
				if (renderer != null) {
					GLWindow.this.width = width;
					GLWindow.this.height = height;
					renderer.update(GLWindow.this.context);
				}
			}
		}));
	}

	@Override
	public void delete() {
		if (renderer != null) {
			renderer.exit(context);
		}
		GLFW.glfwDestroyWindow(window);
	}

	public static void initGLFW() {
		GLFW.glfwInit();
	}

	public static void terminateGLFW() {
		GLFW.glfwTerminate();
	}

}
