package com.glutilities.test;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.glutilities.model.BufferedModelManager;
import com.glutilities.terrain.FractalGenerator;
import com.glutilities.text.FontManager;
import com.glutilities.ui.GLWindow;
import com.glutilities.ui.Renderer;
import com.glutilities.ui.scene.OrthographicProjection;
import com.glutilities.ui.scene.PerspectiveProjection;
import com.glutilities.ui.scene.Scene;
import com.glutilities.ui.scene.SceneProjection;

public class Main {
	
	private static int fps = 0;
	
	public static void main(String[] args) {
		
		// Initialize GLFW
		GLFW.glfwInit();

		// Create scene projections for the window
		SceneProjection ortho = new OrthographicProjection(0, 1, 1, 0, -1, 1);
		SceneProjection perspective = new PerspectiveProjection(80, 0.25f, 100);
		Scene scene = new Scene(ortho, perspective, false);

		// create a new terrain generator
		FractalGenerator fg = new FractalGenerator(FractalGenerator.NOISE_SUM);
		fg.addPerlinGenerator(1, 1, 1, 5);

		// fg.addPerlinGenerator(0.05, 0.05, 0.05, 5);

		// Create the GLWindow
		GLWindow window = new GLWindow(800, 600, "Testing", 0, scene);
		window.create();
		window.setVsyncEnabled(false);

		Thread fps = new Thread(() -> {
			boolean done = false;
			while (!done) {
				try {
					Thread.sleep(250);
					Main.fps = window.resetFrameCounter() * 4;
					//System.out.printf("FPS: %d\n", Main.fps = window.resetFrameCounter() * 4);
				} catch (InterruptedException e) {
					done = true;
				}
			}
		});
		fps.start();

		window.init();
		window.makeContextCurrent();

		// Create a model manager to load models
		BufferedModelManager mm = new BufferedModelManager();
		mm.load(new File("C:/Users/Hanavan/Desktop/testres/models/car2.obj"), "test");
		
		// Create a font manager to load fonts
		FontManager fm = new FontManager();
		fm.load(new Font("Times New Roman", Font.PLAIN, 12), "test");

		window.addRenderer(new Renderer(Renderer.PERSPECTIVE_SCENE) {
			@Override
			public void render() {
				Main.render3D(window, mm);
			}
		});
		
		window.addRenderer(new Renderer(Renderer.ORTHOGRAPHIC_SCENE) {
			@Override
			public void render() {
				Main.render2D(window, fm);
			}
		});

		window.loop();
		fps.interrupt();
	}
	
	private static void render2D(GLWindow window, FontManager fm) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_LIGHT0);
		FontManager.drawString(fm.get("test"), fps + " FPS", 0, 0.2, 1d / window.getWindowAspect() * 0.02d, window.getWindowAspect(), 0.1);
	}
	
	private static void render3D(GLWindow window, BufferedModelManager mm) {
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LIGHT0);

		GL11.glTranslated(0, 0, -7);
		GL11.glRotated(-60, 1, 0, 0);
		
		double theta = GLFW.glfwGetTime() / 5d;
		
		GL11.glRotated(-theta / Math.PI * 180 + 90, 0, 0, 1);
		GL11.glTranslated(Math.cos(theta) * 0.25, Math.sin(theta) * 6, -1);

		mm.get("test").draw();
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
