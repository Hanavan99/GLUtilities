package com.glutilities.test;

import org.lwjgl.glfw.GLFW;

import com.glutilities.ui.GLWindow;

public class Test {

	private static GLWindow window;
	// public static float[] verts = new float[] { 0, 0, 0, 0, 1, 0, 0, 2, 0, 1,
	// 2, 0, 1, 3, 0, 0, 3, 0, 0, 4, 0, 1, 5, 0,
	// 2, 5, 0, 3, 4, 0, 4, 5, 0, 5, 5, 0, 5, 6, 0, 6, 6, 0, 6, 5, 0, 7, 4, 0,
	// 7, 3, 0, 7, 2, 0, 7, 1, 0, 7, 0, 0,
	// 6, 0, 0, 5, 1, 0, 4, 1, 0, 3, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0 };
//	public static float[] verts = new float[] { 0.000000f, 4.000000f, 0f, 1.530734f, 3.695518f, 0f, 2.828427f, 2.828427f, 0f, 3.695518f, 1.530734f, 0f, 4.000000f, -0.000000f, 0f, 3.695518f, -1.530734f, 0f, 2.828427f, -2.828427f, 0f, 1.530734f,
//			-3.695518f, 0f, 0.000001f, -4.000000f, 0f, -1.530733f, -3.695518f, 0f, -2.828426f, -2.828428f, 0f, -3.695518f, -1.530734f, 0f, -4.000000f, 0.000000f, 0f, -3.695518f, 1.530734f, 0f, -2.828426f, 2.828428f, 0f, -1.530732f, 3.695519f, 0f,
//			0.000000f, 4.000000f, 0f };
//	public static float[] clipverts = new float[0];
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
		window.delete();

		// Terminate GLFW
		GLFW.glfwTerminate();
		
		
//		JFrame frame = new JFrame("Test");
//		frame.setBounds(50, 50, 800, 600);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		JGLComponent c = new JGLComponent();
//		frame.add(c);
//		
//		frame.setVisible(true);
	}

}
