package com.glutilities.test;

import org.lwjgl.glfw.GLFW;

import com.glutilities.ui.GLWindow;

public class Test {

	private static GLWindow window;

	public static void main(String[] args) {

		// Initialize GLFW
		GLFW.glfwInit();
		GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 8);

		// Create the GLWindow
		window = new GLWindow(800, 600, "Testing", 0);
		window.setVsyncEnabled(false);
		TestRenderer r = new TestRenderer();
		window.setMasterRenderer(r);
		window.create();
		window.init();
		window.makeContextCurrent();

		// main window loop
		window.loop();

		window.delete();

		// Terminate GLFW
		GLFW.glfwTerminate();

	}

}
