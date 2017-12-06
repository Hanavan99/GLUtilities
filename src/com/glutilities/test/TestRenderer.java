package com.glutilities.test;

//import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.glutilities.buffer.FBO;
import com.glutilities.buffer.VBO;
import com.glutilities.model.AttributeArray;
import com.glutilities.model.Model;
import com.glutilities.model.ModelManager;
import com.glutilities.model.SplineModelBuilder;
import com.glutilities.shader.FragmentShader;
import com.glutilities.shader.ShaderObject;
import com.glutilities.shader.ShaderProgram;
import com.glutilities.shader.VertexShader;
import com.glutilities.text.Font;
import com.glutilities.text.FontManager;
import com.glutilities.ui.MasterRenderer;
import com.glutilities.ui.RenderContext;
import com.glutilities.util.GLMath;
import com.glutilities.util.Vertex4f;
import com.glutilities.util.matrix.Matrix4f;
import com.glutilities.util.matrix.MatrixMath;
import com.glutilities.util.matrix.TransformMatrix;

public class TestRenderer extends MasterRenderer {

	private Matrix4f perspMat;
	private ShaderProgram program;
	private ShaderProgram fboProgram;
	private ModelManager mm;
	private FontManager fm;
	private TransformMatrix transMat;
	private Model spline;
	private VBO test;
	private FBO fbo;
	private VBO fbovbo;

	@Override
	public void init(RenderContext context) {

		// Create a model manager to load models
		mm = new ModelManager();
		mm.load(new File("res/models/cube.obj"), "test");
		mm.load(new File("res/models/locomotive.obj"), "train");
		spline = SplineModelBuilder.build(new float[] { -30, 0, 30, 0 }, 20,
				new float[] { 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1 });

		// Create a font manager to load fonts
		fm = new FontManager();
		fm.load(new Font("Arial", Font.PLAIN, Vertex4f.WHITE, Vertex4f.BLUE, Vertex4f.BLACK, 0, 0, 0, 6), "test");

		// Create 2 shader programs
		VertexShader vertex = new VertexShader("testv", null);
		FragmentShader fragment = new FragmentShader("testf", null);
		updateShaderCode(vertex, fragment);
		vertex.create();
		fragment.create();
		program = new ShaderProgram(vertex, fragment);
		program.create();
		program.link();
		
		VertexShader vertex2 = new VertexShader("testv", null);
		FragmentShader fragment2 = new FragmentShader("testf", null);
		updateShaderCode(vertex2, fragment2);
		vertex2.create();
		fragment2.create();
		fboProgram = new ShaderProgram(vertex2, fragment2);
		fboProgram.create();
		fboProgram.link();
		System.out.println(fboProgram.isLinked() + ": " + fboProgram.getError());

		// Set up some matrices
		updateMatrices(context);
		transMat = new TransformMatrix(program, "transformMatrix");

		// testing new VBO
		AttributeArray verts = new AttributeArray(0, new Float[] { 0f, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 0f, 1f, 0f, 0f }, 3);
		AttributeArray colors = new AttributeArray(1, new Float[] { 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f },
				3);
		test = new VBO(new AttributeArray[] { verts, colors }, 4, GL11.GL_TRIANGLES);
		test.create();

		fbo = new FBO(800, 600);
		fbo.create();

		AttributeArray fbovertex = new AttributeArray(0,
				new Float[] { -1f, -1f, 0f, -1f, 1f, 0f, 1f, 1f, 0f, 1f, -1f, 0f }, 3);
		AttributeArray fbotex = new AttributeArray(3, new Float[] { 0f, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 0f, 1f, 0f, 0f },
				3);

		fbovbo = new VBO(new AttributeArray[] { fbotex, fbovertex }, 4, GL11.GL_QUADS);
		fbovbo.create();
	}

	@Override
	public void render(RenderContext context) {
		clearViewport(context.getWidth(), context.getHeight());

		transMat.reset();

		program.enable();

		fbo.bind();
		fbo.clearFramebuffer();

		program.setInt("renderMode", 0);

		program.setMatrix4f("projectionMatrix", perspMat);
		transMat.setTranslation(0, 0, -15);

		transMat.setScale(1, 1, 1);
		transMat.setRotation(GLMath.PI_2_3, GLMath.PI, 0);
		transMat.rotate(0, 0, (float) GLFW.glfwGetTime() / 4f);

		mm.get("train").draw(program);

		fbo.unbind();

		program.disable();
		//program.setInt("renderMode", 1);
		fboProgram.enable();
		fbo.renderToVBO(fbovbo);

		fboProgram.disable();
	}

	@Override
	public void update(RenderContext context) {
		updateMatrices(context);
		fbo.setSize(context.getWidth(), context.getHeight());
		fbo.delete();
		fbo.create();
	}

	@Override
	public void exit(RenderContext context) {
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
		perspMat = MatrixMath.createPerspectiveMatrix(90f, context.getAspect(), 1f, 300f);
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
