package com.glutilities.test;

import org.lwjgl.glfw.GLFW;

import com.glutilities.ui.GLWindow;

public class Test {

	private static GLWindow window;
	private static boolean running = true;

	public static void main(String[] args) {

		// Initialize GLFW
		GLFW.glfwInit();
		//GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 8);

		// Create the GLWindow
		window = new GLWindow(800, 800, "Testing", 0);
		window.setVsyncEnabled(false);
		//TestRenderer r = new TestRenderer();
		TextDemo r = new TextDemo();
		window.setMasterRenderer(r);
		window.create();
		window.init();
		window.makeContextCurrent();
		
		// FPS Counter
		Thread fps = new Thread() {
			@Override
			public void run() {
				while (running) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {

					}
					System.out.println(window.resetFrameCounter() + " FPS");
				}
			}
		};
		fps.start();

		// main window loop
		window.loop();
		
		running = false;
		window.delete();

		// Terminate GLFW
		GLFW.glfwTerminate();

	}

}
