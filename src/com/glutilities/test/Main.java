package com.glutilities.test;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;

import com.glutilities.buffer.VBO;
import com.glutilities.model.BufferedModelManager;
import com.glutilities.shader.ShaderObject;
import com.glutilities.shader.ShaderProgram;
import com.glutilities.terrain.FractalGenerator;
import com.glutilities.terrain.PerlinGenerator;
import com.glutilities.text.FontManager;
import com.glutilities.ui.GLWindow;
import com.glutilities.ui.MasterRenderer;
import com.glutilities.ui.fbo.Framebuffer;
import com.glutilities.ui.scene.OrthographicProjection;
import com.glutilities.ui.scene.PerspectiveProjection;
import com.glutilities.ui.scene.SceneProjection;
import com.glutilities.util.GLMath;
import com.glutilities.util.Vertex3f;
import com.glutilities.util.matrix.Matrix4f;

public class Main {

	private static int fps = 0;
	private static float yaw = 0;
	private static VBO terrainVBO;

	public static void main(String[] args) {

		Matrix4f projMat = new Matrix4f();

		// Initialize GLFW
		GLFW.glfwInit();
		GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 8);

		// Create scene projections for the window
		SceneProjection ortho = new OrthographicProjection(0, 1, 1, 0, -1, 1);
		SceneProjection fbo = new OrthographicProjection(0, 1, 0, 1, -1, 1);
		SceneProjection perspective = new PerspectiveProjection(80, 0.25f, 100);
		// Scene scene = new Scene(ortho, perspective, true);

		// create a new terrain generator
		FractalGenerator fg = new FractalGenerator(FractalGenerator.NOISE_SUM);
		fg.addPerlinGenerator(1, 1, 1, 5);

		// fg.addPerlinGenerator(0.05, 0.05, 0.05, 5);

		// Create the GLWindow
		GLWindow window = new GLWindow(800, 600, "Testing", 0);
		window.create();
		window.setVsyncEnabled(false);

		Thread fps = new Thread(() -> {
			boolean done = false;
			while (!done) {
				try {
					Thread.sleep(1000);
					Main.fps = window.resetFrameCounter();
					// System.out.printf("FPS: %d\n", Main.fps =
					// window.resetFrameCounter() * 4);
				} catch (InterruptedException e) {
					done = true;
				}
			}
		});
		fps.start();

		window.init();
		window.makeContextCurrent();

		// testing stuff
		float[] vertices = new float[] { 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0 };
		float[] colors = new float[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		float[] texcoords = new float[] { 0, 0, 0, 1, 1, 1, 1, 0 };
		// float[] texcoords = new float[] {0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0};
		VBO fboVbo = new VBO(vertices, colors, null, texcoords, null, GL11.GL_QUADS);
		fboVbo.create();
		fboVbo.unbind();

		terrainVBO = buildTerrainVBO();
		terrainVBO.create();
		terrainVBO.unbind();

		// Create a model manager to load models
		BufferedModelManager mm = new BufferedModelManager();
		//mm.load(new File("C:/Users/Hanavan/Desktop/testres/models/car2.obj"), "test");

		// Create a font manager to load fonts
		FontManager fm = new FontManager();
		fm.load(new Font("Arial", Font.PLAIN, 12), "test");

		// Create a shader program

		ShaderObject vertex = new ShaderObject(ARBVertexShader.GL_VERTEX_SHADER_ARB, "testv", loadFile(new File("C:/Users/Hanavan/Desktop/test.vsh")));
		vertex.create();
		ShaderObject fragment = new ShaderObject(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB, "testf", loadFile(new File("C:/Users/Hanavan/Desktop/test.fsh")));
		fragment.create();
		ShaderProgram program = new ShaderProgram(vertex, fragment, null, null, null);
		program.create();
		program.link();
		System.out.println(program.isLinked() + ": " + program.getError());
		program.glUniformMatrix4f("modelviewMatrix", false, Matrix4f.IDENTITY_MATRIX);


		MasterRenderer renderer = new MasterRenderer() {
			@Override
			public void render(GLWindow parent) {
				ortho.setupProjectionMatrix(100, 100, 1);
				float[] fmat = new float[16];
				GL11.glGetFloatv(GL11.GL_PROJECTION_MATRIX, fmat);
				Matrix4f mat = new Matrix4f(fmat); 
				System.out.println(mat);
			}
			
			@Override
			public void update(GLWindow parent) {
				//Matrix4f projMat = GLMath.createPerspectiveMatrix(80f, parent.getWindowAspect(), 0.1f, 100f);
				Matrix4f projMat = GLMath.createOrthographicMatrix(0, 1, 0, 1, -1, 1);
				program.glUniformMatrix4f("projectionMatrix", false, projMat);
			}
		};
		
		window.setMasterRenderer(renderer);
		
		// Add everything to the render pipeline
		//RenderPipeline rp = window.getRenderPipeline();

		Framebuffer fbo1 = new Framebuffer(window.getWindowWidth(), window.getWindowHeight());
		fbo1.create();
		fbo1.unbind();

		Framebuffer fbo2 = new Framebuffer(window.getWindowWidth(), window.getWindowHeight());
		fbo2.create();
		fbo1.unbind();

//		rp.addFramebuffer("fbo1", fbo1);
//		rp.addFramebuffer("fbo2", fbo2);
//
//		rp.addScene("ortho", ortho);
//		rp.addScene("fbo", fbo);
//		rp.addScene("persp", perspective);
//
//		rp.addRenderer("testpersp", new Renderer(Renderer.PERSPECTIVE_SCENE) {
//			boolean eye = false;
//
//			@Override
//			public void render() {
//				Main.render3D(window, mm, eye);
//				eye = !eye;
//			}
//		});
//
//		rp.addRenderer("testortho", new Renderer(Renderer.ORTHOGRAPHIC_SCENE) {
//			@Override
//			public void render() {
//				Main.render2D(window, fm, "R: Recompile Shaders");
//			}
//		});
//
//		rp.addRenderer("fbo", new Renderer(0) {
//			@Override
//			public void render() {
//				GL11.glDisable(GL11.GL_LIGHTING);
//				GL11.glDisable(GL11.GL_LIGHT0);
//				// fbo1.renderToQuad(0, 0.25, 0, 0.75, 0.5, 0.75, 0.5, 0.25);
//				// fbo2.renderToQuad(0.5, 0.25, 0.5, 0.75, 1, 0.75, 1, 0.25);
//				program.enable();
//				program.glUniform1i("fbo", 0);
//				program.glUniform1i("depthfbo", 1);
//				fbo1.renderToVBO(fboVbo, program);
//				program.disable();
//			}
//		});
//
//		rp.addCommand(RenderPipeline.CMD_GL_CLEAR_AND_VIEWPORT);
//		rp.addCommand(RenderPipeline.CMD_SETUP_PROJECTION, "ortho");
//		rp.addCommand(RenderPipeline.CMD_SETUP_MODELVIEW);
//		rp.addCommand(RenderPipeline.CMD_GL_PUSH_MATRIX);
//		rp.addCommand(RenderPipeline.CMD_CALL_RENDERER, "testortho");
//		rp.addCommand(RenderPipeline.CMD_GL_POP_MATRIX);
//		rp.addCommand(RenderPipeline.CMD_BIND_FRAMEBUFFER, "fbo1");
//		rp.addCommand(RenderPipeline.CMD_GL_CLEAR_AND_VIEWPORT);
//		rp.addCommand(RenderPipeline.CMD_SETUP_PROJECTION, "persp");
//		rp.addCommand(RenderPipeline.CMD_SETUP_MODELVIEW);
//		rp.addCommand(RenderPipeline.CMD_GL_PUSH_MATRIX);
//		rp.addCommand(RenderPipeline.CMD_CALL_RENDERER, "testpersp");
//		rp.addCommand(RenderPipeline.CMD_GL_POP_MATRIX);
//		rp.addCommand(RenderPipeline.CMD_UNBIND_FRAMEBUFFER, "fbo1");

		// rp.addCommand(RenderPipeline.CMD_BIND_FRAMEBUFFER, "fbo2");
		// rp.addCommand(RenderPipeline.CMD_GL_CLEAR_AND_VIEWPORT);
		// rp.addCommand(RenderPipeline.CMD_SETUP_PROJECTION, "ortho");
		// rp.addCommand(RenderPipeline.CMD_SETUP_MODELVIEW);
		// rp.addCommand(RenderPipeline.CMD_GL_PUSH_MATRIX);
		// rp.addCommand(RenderPipeline.CMD_CALL_RENDERER, "testortho");
		// rp.addCommand(RenderPipeline.CMD_GL_POP_MATRIX);
		// rp.addCommand(RenderPipeline.CMD_SETUP_PROJECTION, "persp");
		// rp.addCommand(RenderPipeline.CMD_SETUP_MODELVIEW);
		// rp.addCommand(RenderPipeline.CMD_GL_PUSH_MATRIX);
		// rp.addCommand(RenderPipeline.CMD_CALL_RENDERER, "testpersp");
		// rp.addCommand(RenderPipeline.CMD_GL_POP_MATRIX);
		// rp.addCommand(RenderPipeline.CMD_UNBIND_FRAMEBUFFER, "fbo2");

		// rp.addCommand(RenderPipeline.CMD_GL_CLEAR_AND_VIEWPORT);
		// rp.addCommand(RenderPipeline.CMD_SETUP_PROJECTION, "fbo");
		// rp.addCommand(RenderPipeline.CMD_SETUP_MODELVIEW);
		// rp.addCommand(RenderPipeline.CMD_GL_PUSH_MATRIX);
		//rp.addCommand(RenderPipeline.CMD_CALL_RENDERER, "fbo");
		// rp.addCommand(RenderPipeline.CMD_GL_POP_MATRIX);

		// add callbacks
		window.setKeyCallback(new GLFWKeyCallbackI() {

			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				// TODO Auto-generated method stub
				if (action == GLFW.GLFW_PRESS) {
					switch (key) {
					case GLFW.GLFW_KEY_R:
						//vertex.setCode(loadFile(new File("C:/Users/Hanavan/Desktop/test.vsh")));
						//fragment.setCode(loadFile(new File("C:/Users/Hanavan/Desktop/test.fsh")));
						//program.link();
						//System.out.println("Shader Linked\n" + program.getError());
						break;
					}
				}
			}
		});

		window.loop();
		fps.interrupt();
	}

	private static VBO buildTerrainVBO() {
		PerlinGenerator perlin = new PerlinGenerator(12345, 0.05, 0.05, 0.05, 5);
		final int size = 256;
		float[] points = new float[size * size * 3 * 4];
		float[] normals = new float[size * size * 3 * 4];
		float[] colors = new float[size * size * 3 * 4];
		for (int i = 0; i < points.length; i += 12) {
			int x = (i / 12) % size - (size / 2);
			int y = (i / 12) / size - (size / 2);
			points[i] = x;
			points[i + 1] = y;
			points[i + 2] = (float) perlin.noise(x, y, 0f);
			points[i + 3] = x;
			points[i + 4] = y + 1;
			points[i + 5] = (float) perlin.noise(x, y + 1, 0f);
			points[i + 6] = x + 1;
			points[i + 7] = y + 1;
			points[i + 8] = (float) perlin.noise(x + 1, y + 1, 0f);
			points[i + 9] = x + 1;
			points[i + 10] = y;
			points[i + 11] = (float) perlin.noise(x + 1, y, 0f);
			colors[i + 1] = 1;
			colors[i + 4] = 1;
			colors[i + 7] = 1;
			colors[i + 10] = 1;
		}
		for (int i = 0; i < points.length; i += 12) {
			int x = (i / 12) % size;
			int y = (i / 12) / size;
			Vertex3f normal00 = normal(x, y, points, size);
			Vertex3f normal10 = normal(x + 1, y, points, size);
			Vertex3f normal01 = normal(x, y + 1, points, size);
			Vertex3f normal11 = normal(x + 1, y + 1, points, size);
			normals[i] = (float) normal00.getX();
			normals[i + 1] = (float) normal00.getY();
			normals[i + 2] = (float) normal00.getZ();

			normals[i + 3] = (float) normal01.getX();
			normals[i + 4] = (float) normal01.getY();
			normals[i + 5] = (float) normal01.getZ();

			normals[i + 6] = (float) normal11.getX();
			normals[i + 7] = (float) normal11.getY();
			normals[i + 8] = (float) normal11.getZ();

			normals[i + 9] = (float) normal10.getX();
			normals[i + 10] = (float) normal10.getY();
			normals[i + 11] = (float) normal10.getZ();
		}
		return new VBO(points, colors, normals, null, null, GL11.GL_QUADS);
	}

	private static Vertex3f normal(int x, int y, float[] vertices, int size) {
		float x2y1 = get(x + 1, y, vertices, size);
		float x2y3 = get(x + 1, y + 2, vertices, size);
		float x1y2 = get(x, y + 1, vertices, size);
		float x3y2 = get(x + 2, y + 1, vertices, size);
		Vertex3f a1 = new Vertex3f(0, 2, x2y3 - x2y1);
		a1.crossProduct(new Vertex3f(2, 0, x3y2 - x1y2));
		a1.normalize();
		a1.negate();
		return new Vertex3f(a1.getX(), a1.getY(), a1.getZ());
	}

	private static float get(int x, int y, float[] vertices, int size) {
		if (x >= 0 && x < size && y >= 0 && y < size) {
			return vertices[(y * size + x) * 12 + 2];
		}
		return 0;
	}

	private static void render2D(GLWindow window, FontManager fm, String extraData) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_LIGHT0);
		double scale = 0.004;
		double size = 1d / window.getWindowAspect() * scale;
		double pos = scale * 10;
		FontManager.drawString(fm.get("test"), fps + " FPS", 0, pos, size, window.getWindowAspect(), 0.1);
		String[] lines = extraData.split("\n");
		pos += scale * 10;
		for (String line : lines) {
			FontManager.drawString(fm.get("test"), line, 0, pos, size, window.getWindowAspect(), 0.1);
			pos += scale * 10;
		}
	}

	private static void render3D(GLWindow window, BufferedModelManager mm, boolean eye) {
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LIGHT0);

		GL11.glTranslated(0, 0, -20);
		GL11.glRotated(-60, 1, 0, 0);
		GL11.glRotated(yaw, 0, 0, 1);

		if (window.getKey(GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_PRESS) {
			yaw += 0.05;
		}
		if (window.getKey(GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS) {
			yaw -= 0.05;
		}

		double theta = GLFW.glfwGetTime() / 3d;

		// GL11.glRotated(-theta / Math.PI * 180 + 90, 0, 0, 1);
		// GL11.glTranslated(Math.cos(theta) * 0.25, Math.sin(theta) * 6, -1);
		// GL11.glTranslated(0, Math.sin(theta) * 10 - 8, 0);

		
		// mm.get("test").draw();
		terrainVBO.draw();
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3d(0, 0, 1);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(-128, -128);
		GL11.glVertex2d(-128, 128);
		GL11.glVertex2d(128, 128);
		GL11.glVertex2d(128, -128);
		GL11.glEnd();

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
