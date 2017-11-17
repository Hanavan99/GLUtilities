package com.glutilities.test;

//import java.awt.Font;
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
import com.glutilities.text.Font;
import com.glutilities.text.FontManager;
import com.glutilities.ui.MasterRenderer;
import com.glutilities.ui.RenderContext;
import com.glutilities.ui.gfx.TextBox;
import com.glutilities.util.Vertex3f;
import com.glutilities.util.Vertex4f;
import com.glutilities.util.matrix.Matrix4f;
import com.glutilities.util.matrix.MatrixMath;

public class TestRenderer extends MasterRenderer {

	private VBO fbo1VBO;
	private VBO fbo2VBO;
	private Matrix4f orthoMat;
	private Matrix4f perspMat;
	private ShaderProgram program;
	private BufferedModelManager mm;
	private FontManager fm;
	private VBO origin;

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

		// clip();

		fbo1VBO = new VBO(vertices, colors, normals, texcoords, null, GL11.GL_QUADS);
		fbo1VBO.create();

		fbo2VBO = new VBO(vertices2, colors, normals, texcoords, null, GL11.GL_QUADS);
		fbo2VBO.create();

		// Create a model manager to load models
		mm = new BufferedModelManager();
		mm.load(new File("res/models/car2.obj"), "test");

		// Create a font manager to load fonts
		fm = new FontManager();
		fm.load(new Font("Arial", Font.PLAIN, Vertex4f.WHITE, Vertex4f.BLUE, Vertex4f.BLACK, 0, 0, 0, 6), "test");
		fm.load(new Font("Arial", Font.PLAIN, Vertex4f.WHITE, Vertex4f.BLACK, Vertex4f.BLACK, 0, 0, 0, 6), "test2");
		fm.load(new Font("Arial", Font.ITALIC, Vertex4f.PURPLE, Vertex4f.BLACK, Vertex4f.BLACK, 0, 0, 0, 6), "test3");
		fm.load(new Font("Courier New", Font.PLAIN, Vertex4f.YELLOW, Vertex4f.BLACK, Vertex4f.BLACK, 0, 2, 0, 6), "test4");

		// Create 2 shader programs
		VertexShader vertex = new VertexShader("testv", null);
		FragmentShader fragment = new FragmentShader("testf", null);
		updateShaderCode(vertex, fragment);
		vertex.create();
		fragment.create();
		program = new ShaderProgram(vertex, fragment);
		program.create();
		program.link();
		System.out.println(program.isLinked() + ": " + program.getError());

		// Set up some matrices
		updateMatrices(context);
		//box.setup(context);
	}

	@Override
	public void render(RenderContext context) {
		clearViewport(context.getWidth(), context.getHeight());

		// GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

		// fbo.bind();

		program.enable();
		program.setBool("useTexture", true);

		program.setMatrix4f("projectionMatrix", orthoMat);
		float scale = 0.02f;
		// float scale = 0.2f;
		Matrix4f transform = Matrix4f.IDENTITY_MATRIX;
		// transform = MatrixMath.rotate(transform, (float) GLFW.glfwGetTime() /
		// 1f, 0b001);

		transform = MatrixMath.scale(transform, new Vertex3f(scale, scale * 4f, scale));
		// transform = MatrixMath.translate(transform, new Vertex3f(-0.7f, 0.8f,
		// 0));
		transform = MatrixMath.translate(transform, new Vertex3f(-0.9f, -0.9f, 0));

		program.setMatrix4f("transformMatrix", transform);
		origin.draw();
		// clipperVBO.draw();
		// clipperVBO2.draw();
		// FontManager.drawString(fm.get("test"), "Test", transform, 10f,
		// program, "transformMatrix");
		fm.get("test").draw("Hello World!", transform, program, "transformMatrix", "glyphTex");
		transform = MatrixMath.translate(transform, new Vertex3f(0, 0.1f, 0));
		fm.get("test2").draw("Hello World!", transform, program, "transformMatrix", "glyphTex");
		transform = MatrixMath.translate(transform, new Vertex3f(0, 0.1f, 0));
		fm.get("test3").draw("Hello World!", transform, program, "transformMatrix", "glyphTex");
		transform = MatrixMath.translate(transform, new Vertex3f(0, 0.1f, 0));
		fm.get("test4").draw("Hello World!", transform, program, "transformMatrix", "glyphTex");
		// render2D(window, "R: Recompile Shader");
		transform = MatrixMath.setTranslation(transform, new Vertex3f(0, 0, 0));
		program.setMatrix4f("transformMatrix", transform);
		//box.render(context, program, null);
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
		fbo1VBO.delete();
		fbo2VBO.delete();
		program.delete();
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
			}
		}
	}

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
