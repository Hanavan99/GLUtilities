package com.glutilities.test;

import org.lwjgl.glfw.GLFW;

import com.glutilities.ui.GLWindow;
import com.glutilities.ui.gfx.TextBox;
import com.glutilities.ui.gfx.UIRenderer;
import com.glutilities.util.Vertex3f;

public class Test {

	private static GLWindow window;

	public static void main(String[] args) {

		// Initialize GLFW
		GLFW.glfwInit();

		// Create the GLWindow
		window = new GLWindow(800, 600, "Testing", 0);
		window.setVsyncEnabled(false);
		//window.setMasterRenderer(new TestRenderer());
		UIRenderer r = new UIRenderer();
		window.setMasterRenderer(r);
		window.create();
		window.init();
		window.makeContextCurrent();
		
		r.addElement(new TextBox(new Vertex3f(0), new Vertex3f(8, 1, 1)));
		
		window.loop();
		window.delete();

		// Terminate GLFW
		GLFW.glfwTerminate();

	}

}
