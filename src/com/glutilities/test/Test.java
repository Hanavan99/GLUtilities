package com.glutilities.test;

import org.lwjgl.glfw.GLFW;

import com.glutilities.ui.GLWindow;
import com.glutilities.util.matrix.Matrix4f;

public class Test {

	private static GLWindow window;

	public static void main(String[] args) {

		// Initialize GLFW
		GLFW.glfwInit();

		Matrix4f a = new Matrix4f(new float[] { 1, 2, -3, 1, 0, 1, 20, 5, 2, 13, 34, 10, 11, 14, 1, 81 });
		Matrix4f b = new Matrix4f(new float[] { 2, 5, 51, 11, 6, 7, 6, 12, 1, 8, -8, 9, 6, 7, 2, 13 });
		System.out.println(a.multiply(b));
		//System.exit(0);
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
