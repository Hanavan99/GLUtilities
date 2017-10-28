package com.glutilities.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.glutilities.util.GLMath;

public class GLRootPane {

	private final long window;
	private List<GLComponent> components = new ArrayList<GLComponent>();

	public GLRootPane(int width, int height, String title, long monitor) {
		window = GLFW.glfwCreateWindow(width, height, title, monitor, 0);
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwShowWindow(window);
		init();
	}

	public boolean isClosing() {
		return GLFW.glfwWindowShouldClose(window);
	}

	private void init() {
		GL.createCapabilities();

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void loop() {
		while (!GLFW.glfwWindowShouldClose(window)) {
			int[] width = new int[1], height = new int[1];
			GLFW.glfwGetWindowSize(window, width, height);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glClearColor(0, 0, 0, 1);
			GL11.glViewport(0, 0, width[0], height[0]);

			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			//GL11.glOrtho(0, 1, 1, 0, -1, 1);
			GLMath.createPerspective(80, (double) width[0] / height[0], 0.1, 30);

			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();

			GL11.glPushMatrix();

			render();

			GL11.glPopMatrix();

			GLFW.glfwPollEvents();
			GLFW.glfwSwapInterval(1);
			GLFW.glfwSwapBuffers(window);
		}
	}
	
	public void addComponent(GLComponent c) {
		components.add(c);
	}

	public void render() {
		for (GLComponent c : components) {
			c.render();
		}
	}

}
