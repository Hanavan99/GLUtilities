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
		// window.setMasterRenderer(new TestRenderer());
		// UIRenderer r = new UIRenderer();
		window.setMasterRenderer(r);
		window.create();
		window.init();
		window.makeContextCurrent();

		// r.addElement(new TextBox(new Vertex3f(10, 10, 0), new Vertex3f(500,
		// 30, 0)));
		// r.addElement(new Label(new Vertex3f(0.5f, 3.5f, 0), new
		// Vertex3f(0)));

		window.loop();
		window.delete();

		// Terminate GLFW
		GLFW.glfwTerminate();

	}

}
