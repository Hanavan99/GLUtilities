package com.glutilities.test;

import org.lwjgl.glfw.GLFW;

import com.glutilities.ui.GLWindow;
import com.glutilities.util.EarClipper;
import com.glutilities.util.Vertex2f;

public class Test {

	private static GLWindow window;
	public static float[] verts = new float[] { 0, 0, 0, 0, 1, 0, 0, 2, 0, 1, 2, 0, 1, 3, 0, 0, 3, 0, 0, 4, 0, 1, 5, 0,
			2, 5, 0, 3, 4, 0, 4, 5, 0, 5, 5, 0, 5, 6, 0, 6, 6, 0, 6, 5, 0, 7, 4, 0, 7, 3, 0, 7, 2, 0, 7, 1, 0, 7, 0, 0,
			6, 0, 0, 5, 1, 0, 4, 1, 0, 3, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0 };
	public static float[] clipverts = new float[0];

	public static void main(String[] args) {

		// Initialize GLFW
		GLFW.glfwInit();

		// float[] triangles = EarClipper.clip(verts);
		// int i = 0;
		// for (float f : triangles) {
		// System.out.print(f + " ");
		// if (i % 3 == 2) {
		// System.out.println();
		// }
		// i++;
		// }
		//
		// System.exit(0);
		System.out.println(EarClipper.intersection(new Vertex2f(-1, -1), new Vertex2f(-2, -2), new Vertex2f(1,  1), new Vertex2f(2, 2)));
		//System.exit(0);

		// Create the GLWindow
		window = new GLWindow(800, 600, "Testing", 0);
		window.setVsyncEnabled(false);
		window.setMasterRenderer(new TestRenderer());
		window.create();
		window.init();
		window.makeContextCurrent();
		window.loop();
		window.delete();

		// Terminate GLFW
		GLFW.glfwTerminate();
	}

}
