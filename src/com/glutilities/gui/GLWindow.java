package com.glutilities.gui;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import com.glutilities.gui.fbo.Framebuffer;
import com.glutilities.shader.ShaderProgram;

/**
 * A GLFW window with an OpenGL context that can be used to render the game.
 * 
 * @author Hanavan99
 */
public class GLWindow {

	/**
	 * The window ID used by GLFW.
	 */
	private final long window;

	/**
	 * The list of renderers that are used to render the game.
	 */
	private List<Renderer> renderers = new ArrayList<Renderer>();

	/**
	 * The list of shader programs that are used to render the game.
	 */
	private List<ShaderProgram> shaders = new ArrayList<ShaderProgram>();

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
	 * Creates a new window with the specified dimensions, title, and monitor to
	 * render on. If monitor is 0, the width and height attributes describe the size
	 * of the window. If monitor is a valid identifier, width and height describe
	 * the window resolution.
	 * 
	 * @param width
	 *            the width of the window in pixels
	 * @param height
	 *            the height of the window in pixels
	 * @param title
	 *            the title of the window
	 * @param monitor
	 *            the monitor on which to place the window in fullscreen mode. If 0,
	 *            puts the window in windowed mode.
	 */
	public GLWindow(int width, int height, String title, long monitor, Scene scene) {
		window = GLFW.glfwCreateWindow(width, height, title, monitor, 0);
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwShowWindow(window);
		this.scene = scene;
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
		//GL11.glEnable(GL11.GL_LIGHTING);
		//GL11.glEnable(GL11.GL_LIGHT0);

		GL11.glClearColor(0, 0, 0, 1);

		
		 framebuffer = new Framebuffer(getWindowWidth(), getWindowHeight());
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

			//GL11.glEnable(GL11.GL_TEXTURE_2D);
			
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
					renderOrtho();
				}

				SceneProjection perspective = scene.getPerspectiveProjection();
				if (perspective != null) {
					perspective.setupProjectionMatrix(width, height, aspect);
					GL11.glMatrixMode(GL11.GL_MODELVIEW);
					GL11.glLoadIdentity();
					renderPerspective();
				}

				if (scene.shouldDrawToTexture()) {
					framebuffer.unbind();
					
					//GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
					/*GL11.glClearColor(0, 0, 0, 1);
					GL11.glViewport(0, 0, width, height);
					
					GL11.glMatrixMode(GL11.GL_PROJECTION);
					GL11.glLoadIdentity();
					GL11.glOrtho(0, 1, 1, 0, -1, 1);
					
					GL11.glMatrixMode(GL11.GL_PROJECTION);
					GL11.glLoadIdentity();
					GL11.glPushMatrix();
					GL11.glDisable(GL11.GL_LIGHTING);*/
					framebuffer.renderToScreen(width, height);
					//GL11.glPopMatrix();
					//GL20.glDrawBuffers(framebuffer);
				}
			}

			GLFW.glfwPollEvents();
			GLFW.glfwSwapInterval(vsync ? 1 : 0);
			GLFW.glfwSwapBuffers(window);
		}
	}

	public int getWindowWidth() {
		return getWindowSize()[0];
	}

	public int getWindowHeight() {
		return getWindowSize()[1];
	}

	public int[] getWindowSize() {
		int[] width = new int[1], height = new int[1];
		GLFW.glfwGetWindowSize(window, width, height);
		return new int[] { width[0], height[0] };
	}

	public float getWindowAspect() {
		int[] windowSize = getWindowSize();
		return (float) windowSize[0] / windowSize[1];
	}

	public boolean getVsyncEnabled() {
		return vsync;
	}

	public void setWindowWidth(int width) {

	}

	public void setWindowHeight(int height) {

	}

	public void setWindowSize(int width, int height) {
		GLFW.glfwSetWindowSize(window, width, height);
	}

	public void setWindowSize(int[] size) {
		if (size.length >= 2) {
			setWindowSize(size[0], size[1]);
		}
	}

	public void setVsyncEnabled(boolean vsync) {
		this.vsync = vsync;
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

	public void addRenderer(Renderer r) {
		renderers.add(r);
	}

	public void addShader(ShaderProgram sp) {
		shaders.add(sp);
	}

	public void renderOrtho() {
		for (Renderer r : renderers) {
			if (r.getRenderMode() == Renderer.ORTHOGRAPHIC_SCENE) {
				GL11.glPushMatrix();
				r.render();
				GL11.glPopMatrix();
			}
		}
	}

	public void renderPerspective() {
		for (Renderer r : renderers) {
			if (r.getRenderMode() == Renderer.PERSPECTIVE_SCENE) {
				GL11.glPushMatrix();
				r.render();
				GL11.glPopMatrix();
			}
		}
	}

	public void makeContextCurrent() {
		GLFW.glfwMakeContextCurrent(window);
	}

	public void destroyWindow() {
		GLFW.glfwDestroyWindow(window);
	}

}
