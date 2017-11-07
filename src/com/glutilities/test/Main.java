package com.glutilities.test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;

import com.glutilities.model.ModelManager;
import com.glutilities.shader.ShaderObject;
import com.glutilities.shader.ShaderProgram;
import com.glutilities.ui.GLWindow;
import com.glutilities.ui.Renderer;
import com.glutilities.ui.scene.OrthographicProjection;
import com.glutilities.ui.scene.PerspectiveProjection;
import com.glutilities.ui.scene.Scene;
import com.glutilities.ui.scene.SceneProjection;

public class Main {
	public static void main(String[] args) {
		
		// Initialize GLFW
		GLFW.glfwInit();

		// Create scene projections for the window
		SceneProjection ortho = new OrthographicProjection(0, 1, 1, 0, -1, 1);
		SceneProjection perspective = new PerspectiveProjection(80, 1, 100);
		Scene scene = new Scene(ortho, perspective, true);

		// Create the GLWindow
		GLWindow window = new GLWindow(800, 600, "Testing", 0, scene);
		window.setVsyncEnabled(false);
		
		// Create a model manager to load models
		ModelManager mm = new ModelManager();
		mm.load(new File("C:/Users/Hanavan/Desktop/car.obj"), "test");

		Thread fps = new Thread(() -> {
			boolean done = false;
			while (!done) {
				try {
					Thread.sleep(1000);
					System.out.printf("FPS: %d\n", window.resetFrameCounter());
				} catch (InterruptedException e) {
					done = true;
				}
			}
		});
		fps.start();

		window.init();
		window.makeContextCurrent();
		
		ShaderProgram colorprogram = new ShaderProgram();
		ShaderObject colorvertex = new ShaderObject(ARBVertexShader.GL_VERTEX_SHADER_ARB, "testv", loadFile(new File("C:/Users/Hanavan/Desktop/test.vsh")));
		//ShaderObject color2vertex = new ShaderObject(ARBVertexShader.GL_VERTEX_SHADER_ARB, "testv2", loadFile(new File("C:/Users/Hanavan/Desktop/test2.vsh")));
		ShaderObject colorfragment = new ShaderObject(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB, "testf", loadFile(new File("C:/Users/Hanavan/Desktop/test.fsh")));
		colorprogram.addShaderObject(colorvertex);
		//colorprogram.addShaderObject(color2vertex);
		colorprogram.addShaderObject(colorfragment);
		colorprogram.compile();
		
		System.out.println("Shader status: " + (colorprogram.isLinked() ? "Compiled" : ("ERROR: " + colorprogram.getError())));
		
		//ShaderProgram ppprogram = new ShaderProgram();
		//ShaderObject vertex = new ShaderObject(ARBVertexShader.GL_VERTEX_SHADER_ARB, "testv", loadFile(new File("C:/Users/Hanavan/Desktop/test.vsh")));
		//ShaderObject fragment = new ShaderObject(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB, "testf", loadFile(new File("C:/Users/Hanavan/Desktop/test.fsh")));
		//ppprogram.addShaderObject(vertex);
		//ppprogram.addShaderObject(fragment);
		//ppprogram.compile();
		
		//window.setShaderProgram(program);
		
		//System.out.println("Shader status: " + (ppprogram.isLinked() ? "Compiled" : ("ERROR: " + ppprogram.getError())));
		
		window.addRenderer(new Renderer(Renderer.PERSPECTIVE_SCENE) {

			@Override
			public void render() {
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_LIGHT0);

				GL11.glTranslated(0, 0, -10);

				GL11.glRotated(-40, 1, 0, 0);
				GL11.glRotated(GLFW.glfwGetTime() * 10d, 0, 0, 1);
				colorprogram.enable();
				mm.get("test").draw();
				colorprogram.disable();
			}

		});
		
		window.loop();
		fps.interrupt();
	}

	private static String loadFile(File f) {
		try {
			Scanner s = new Scanner(f);
			String result = "";
			while (s.hasNext()) {
				result += s.nextLine() + "\n";
			}
			s.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
