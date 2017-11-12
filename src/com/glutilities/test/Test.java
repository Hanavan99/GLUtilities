package com.glutilities.test;

import org.lwjgl.glfw.GLFW;

import com.glutilities.ui.GLWindow;

public class Test {

	private static GLWindow window;

	public static void main(String[] args) {

		// Initialize GLFW
		GLFW.glfwInit();

		// Create the GLWindow
		window = new GLWindow(800, 600, "Testing", 0);
		window.setVsyncEnabled(false);
		window.setMasterRenderer(new TestRenderer());
		window.create();
		window.init();
		window.makeContextCurrent();
		window.loop();
		
		// Terminate GLFW
		GLFW.glfwTerminate();
	}

}
