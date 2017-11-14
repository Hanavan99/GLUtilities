package com.glutilities.test;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.glutilities.buffer.VBO;
import com.glutilities.model.BufferedModelManager;
import com.glutilities.shader.FragmentShader;
import com.glutilities.shader.ShaderObject;
import com.glutilities.shader.ShaderProgram;
import com.glutilities.shader.VertexShader;
import com.glutilities.terrain.PerlinGenerator;
import com.glutilities.text.FontManager;
import com.glutilities.text.texture.Charset;
import com.glutilities.text.texture.FontLoader;
import com.glutilities.text.texture.TextFormat;
import com.glutilities.ui.GLWindow;
import com.glutilities.ui.MasterRenderer;
import com.glutilities.ui.RenderContext;
import com.glutilities.ui.fbo.FBO;
import com.glutilities.util.ArrayUtils;
import com.glutilities.util.EarClipper;
import com.glutilities.util.Vertex3f;
import com.glutilities.util.Vertex4f;
import com.glutilities.util.matrix.Matrix4f;
import com.glutilities.util.matrix.MatrixMath;

public class TestRenderer extends MasterRenderer {

	private int fps = 0;
	private float yaw = 0;
	private float pitch = 120;
	private VBO terrainVBO;
	private VBO fbo1VBO;
	private VBO fbo2VBO;
	private Matrix4f orthoMat;
	private Matrix4f perspMat;
	private ShaderProgram program;
	private ShaderProgram fprogram;
	private FBO reflectionFBO;
	private FBO refractionFBO;
	private BufferedModelManager mm;
	private FontManager fm;
	private Thread fpsThread;
	private VBO clipperVBO;
	private VBO clipperVBO2;
	private VBO origin;
	private float[] clipvertcolors;
	private float[] tricolors;
	private FontLoader fl;
	private Charset chr;
	private TextFormat textFormat;
	private String text = "ASDF";

	@Override
	public void init(RenderContext context) {
		// float[] vertices = new float[] { -1, -1, 0, -1, 1, 0, 1, 1, 0, 1, -1,
		// 0 };
		float[] vertices = new float[] { -1, -1, 0, -1, -0.5f, 0, -0.5f, -0.5f, 0, -0.5f, -1, 0 };
		float[] vertices2 = new float[] { -0.5f, -0.5f, 0, -0.5f, 0, 0, 0, 0, 0, 0, -0.5f, 0 };
		float[] colors = new float[] { 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1 };
		float[] texcoords = new float[] { 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0 };
		float[] normals = new float[] { 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1 };
		// float[] texcoords = new float[] { 0, 0, 0, 1, 1, 1, 1, 0 };

		origin = new VBO(new float[] { -1, 0, 0, 1, 0, 0, 0, -1, 0, 0, 1, 0 }, colors, null, null, null, GL11.GL_LINES);
		origin.create();

		//clip();

		fbo1VBO = new VBO(vertices, colors, normals, texcoords, null, GL11.GL_QUADS);
		fbo1VBO.create();

		fbo2VBO = new VBO(vertices2, colors, normals, texcoords, null, GL11.GL_QUADS);
		fbo2VBO.create();

		terrainVBO = buildTerrainVBO();
		terrainVBO.create();

		// Create a model manager to load models
		mm = new BufferedModelManager();
		mm.load(new File("res/models/car2.obj"), "test");

		// Create a font manager to load fonts
		fm = new FontManager();
		// fm.load(new Font("Arial", Font.PLAIN, 12), "test");
		fl = new FontLoader();
		textFormat = new TextFormat();
		fl.setTextFormat(textFormat);
		textFormat.textureScale = 4;
		textFormat.textColor = new Vertex4f(1, 1, 1, 1);
		textFormat.bgColor = new Vertex4f(0, 0, 0, 1);
		try {
			chr = fl.load(new com.glutilities.text.texture.Font("Arial", Font.PLAIN), "test");
		} catch (IOException e) {
			System.out.println("Unable to load font");
		}

		// Create 2 shader programs
		VertexShader vertex = new VertexShader("testv", null);
		FragmentShader fragment = new FragmentShader("testf", null);
		updateShaderCode(vertex, fragment);
		vertex.create();
		fragment.create();
		program = new ShaderProgram(vertex, fragment, null, null, null);
		program.create();
		program.link();
		System.out.println(program.isLinked() + ": " + program.getError());

		// Set up some matrices
		updateMatrices(context);

		

		
	}

	@Override
	public void render(RenderContext context) {
		clearViewport(context.getWidth(), context.getHeight());
		
		// GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

		// fbo.bind();

		program.enable();

		program.glUniformMatrix4f("projectionMatrix", true, orthoMat);
		float scale = 0.05f;
		// float scale = 0.2f;
		Matrix4f transform = Matrix4f.IDENTITY_MATRIX;
		//transform = MatrixMath.rotate(transform, (float) GLFW.glfwGetTime() / 1f, 0b001);
		
		transform = MatrixMath.scale(transform, new Vertex3f(scale, scale * 4f, scale));
		// transform = MatrixMath.translate(transform, new Vertex3f(-0.7f, 0.8f,
		// 0));
		transform = MatrixMath.translate(transform, new Vertex3f(-0.9f, -0.9f, 0));
		
		program.glUniformMatrix4f("transformMatrix", true, transform);
		origin.draw();
		// clipperVBO.draw();
		// clipperVBO2.draw();
		// FontManager.drawString(fm.get("test"), "Test", transform, 10f,
		// program, "transformMatrix");
		textFormat.leading = 0.1f;
		textFormat.kerning = 0;
		chr.draw("In a world, where leafy bullies people with mental disablilities...", textFormat, transform, program, "transformMatrix", "glyphTex");
		// render2D(window, "R: Recompile Shader");

		// DRAW ON FBO
		/*
		 * reflectionFBO.bind(); program.glUniform4f("plane", new Vertex4f(0, 0,
		 * 1, 1)); clearViewport(window.getWindowWidth(),
		 * window.getWindowHeight());
		 * 
		 * program.glUniformMatrix4f("projectionMatrix", true, perspMat);
		 * render3D(window, true); reflectionFBO.unbind();
		 * 
		 * refractionFBO.bind(); program.glUniform4f("plane", new Vertex4f(0, 0,
		 * -1, -1)); clearViewport(window.getWindowWidth(),
		 * window.getWindowHeight());
		 * 
		 * program.glUniformMatrix4f("projectionMatrix", true, perspMat);
		 * render3D(window, false); refractionFBO.unbind();
		 */

		// DRAW NORMALLY
		// program.glUniform4f("plane", new Vertex4f(0, 0, 1, 100000));
		// program.glUniformMatrix4f("projectionMatrix", true, perspMat);
		// render3D(window, false);
		program.disable();

		/*
		 * fprogram.enable(); fprogram.glUniform1i("fbo", 0);
		 * fprogram.glUniform1i("depthfbo", 1);
		 * reflectionFBO.renderToVBO(fbo1VBO);
		 * refractionFBO.renderToVBO(fbo2VBO); // fbo.renderToQuad(-1, -1, -1,
		 * 1, 1, 1, 1, -1); fprogram.disable();
		 */
	}

	@Override
	public void update(RenderContext context) {
		updateMatrices(context);
	}

	@Override
	public void exit(RenderContext context) {
		terrainVBO.delete();
		fbo1VBO.delete();
		fbo2VBO.delete();
		//clipperVBO.delete();
		program.delete();
		// fprogram.delete();
		// reflectionFBO.delete();
		// refractionFBO.delete();
		//fpsThread.interrupt();
	}
	
	@Override
	public void keyPressed(RenderContext context, int key, int action) {
		if (action == GLFW.GLFW_PRESS) {
			switch (key) {
			case GLFW.GLFW_KEY_R:
				updateShaderCode(program.getVertexShader(), program.getFragmentShader());
				program.link();
				System.out.println("Shader Linked\n" + program.getError());
				break;
			case GLFW.GLFW_KEY_M:
				updateMatrices(context);
				System.out.println("Updated matrices");
				break;
			case GLFW.GLFW_KEY_T:
				text = loadFile(new File("res/shaders/samplevertex.vsh"));
				break;
			}
		}
	}
//	
//	private void processInput() {
//		if (action == GLFW.GLFW_PRESS) {
//			switch (key) {
//			case GLFW.GLFW_KEY_R:
//				updateShaderCode(vertex, fragment);
//				program.link();
//				System.out.println("Shader Linked\n" + program.getError());
//				break;
//			case GLFW.GLFW_KEY_M:
//				updateMatrices(context);
//				System.out.println("Updated matrices");
//				break;
//			case GLFW.GLFW_KEY_Z:
//				clip();
//				System.out.println("Re-ran clipping algorithm");
//				break;
//			case GLFW.GLFW_KEY_T:
//				text = loadFile(new File("res/shaders/samplevertex.vsh"));
//				break;
//			}
//		}
//	}

//	private void clip() {
//		Test.clipverts = EarClipper.clip(Test.verts, 200, 0.5f);
//		clipvertcolors = new float[Test.verts.length];
//		tricolors = new float[Test.clipverts.length];
//		ArrayUtils.fill(clipvertcolors, new float[] { 0, 0, 1 });
//		ArrayUtils.fillrandom(tricolors, 9);
//		ArrayUtils.fillmask(Test.clipverts, new float[] { 0, 0, -0.3f }, 0b001);
//		if (clipperVBO == null) {
//			clipperVBO = new VBO(Test.verts, clipvertcolors, null, null, null, GL11.GL_POINTS);
//		} else {
//			clipperVBO.setVertices(Test.verts);
//			clipperVBO.setColors(clipvertcolors);
//			clipperVBO.delete();
//		}
//		clipperVBO.create();
//		if (clipperVBO2 == null) {
//			clipperVBO2 = new VBO(Test.clipverts, tricolors, null, null, null, GL11.GL_TRIANGLES);
//		} else {
//			clipperVBO2.setVertices(Test.clipverts);
//			clipperVBO2.setColors(tricolors);
//			clipperVBO2.delete();
//		}
//		clipperVBO2.create();
//	}

	private void clearViewport(int width, int height) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glViewport(0, 0, width, height);
	}

	private void updateMatrices(RenderContext context) {
		orthoMat = MatrixMath.createOrthographicMatrix(-1, 1, 1, -1, -1, 1);
		perspMat = MatrixMath.createPerspectiveMatrix(80f, context.getAspect(), 0.1f, 100f);
	}

	private void updateShaderCode(ShaderObject vertex, ShaderObject fragment) {
		if (vertex != null) {
			vertex.setCode(loadFile(new File("res/shaders/samplevertex.vsh")));
		}
		if (fragment != null) {
			fragment.setCode(loadFile(new File("res/shaders/samplefragment.fsh")));
		}
	}

	private VBO buildTerrainVBO() {
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

	private Vertex3f normal(int x, int y, float[] vertices, int size) {
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

	private void render2D(GLWindow window, String extraData) {
		float scale = 0.05f;
		Matrix4f transform = Matrix4f.IDENTITY_MATRIX;
		transform = MatrixMath.scale(transform, new Vertex3f(scale, scale * window.getWindowAspect(), scale));

		FontManager.drawString(fm.get("test"), fps + " FPS", transform, 0.1f, program, "transformMatrix");
		transform = MatrixMath.translate(transform, new Vertex3f(0, -0.1f, 0));
		String[] lines = extraData.split("\n");
		for (String line : lines) {
			FontManager.drawString(fm.get("test"), line, transform, 0.1f, program, "transformMatrix");
		}
	}

	private void render3D(GLWindow window, boolean flipCamera) {

		// GL11.glTranslated(0, 0, -20);
		// GL11.glRotated(-60, 1, 0, 0);
		// GL11.glRotated(yaw, 0, 0, 1);

		if (window.getKey(GLFW.GLFW_KEY_LEFT) == GLFW.GLFW_PRESS) {
			yaw += 0.0005;
		}
		if (window.getKey(GLFW.GLFW_KEY_RIGHT) == GLFW.GLFW_PRESS) {
			yaw -= 0.0005;
		}
		if (window.getKey(GLFW.GLFW_KEY_UP) == GLFW.GLFW_PRESS) {
			pitch += 0.0005;
		}
		if (window.getKey(GLFW.GLFW_KEY_DOWN) == GLFW.GLFW_PRESS) {
			pitch -= 0.0005;
		}

		// double theta = GLFW.glfwGetTime() / 3d;

		Matrix4f transform = Matrix4f.IDENTITY_MATRIX;
		transform = MatrixMath.scale(transform, new Vertex3f(0.05f, 0.05f, 0.05f));
		transform = MatrixMath.translate(transform, new Vertex3f(0, 0, -1f));
		transform = MatrixMath.rotate(transform, flipCamera ? -pitch + (float) Math.PI / 1f : pitch, 4);
		// transform = MatrixMath.rotate(transform, yaw / 100f, 1);

		program.glUniformMatrix4f("transformMatrix", true, transform);

		terrainVBO.draw();
		mm.get("test").draw();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glPushMatrix();
		GL11.glColor3d(0, 0, 1);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(-128, -128);
		GL11.glVertex2d(-128, 128);
		GL11.glVertex2d(128, 128);
		GL11.glVertex2d(128, -128);
		GL11.glEnd();
		GL11.glPopMatrix();

	}

	private String loadFile(File f) {
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
