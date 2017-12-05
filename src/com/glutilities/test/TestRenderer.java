package com.glutilities.test;

//import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.glutilities.model.BufferedModel;
import com.glutilities.model.BufferedModelManager;
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
	private BufferedModelManager mm;
	private FontManager fm;
	private TransformMatrix transMat = new TransformMatrix();
	private BufferedModel spline;

	@Override
	public void init(RenderContext context) {

		// Create a model manager to load models
		mm = new BufferedModelManager();
		mm.load(new File("res/models/locomotive.obj"), "test");
		spline = SplineModelBuilder.build(new float[] { 0, 0, 0, 10, 10, 10 }, 20, new float[] { 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1 });

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
		System.out.println(program.isLinked() + ": " + program.getError());

		// Set up some matrices
		updateMatrices(context);
		transMat.translate(0, 0, -30);

		System.out.println(perspMat);
	}

	@Override
	public void render(RenderContext context) {
		clearViewport(context.getWidth(), context.getHeight());

		// transMat.reset();

		program.enable();
		program.setInt("renderMode", 0);
		program.setMatrix4f("projectionMatrix", perspMat);
		program.setMatrix4f("transformMatrix", transMat.getMatrix());
		// transMat.translate(0, 0, 0.1f);
		transMat.setTranslation(0, 0, -20);
		transMat.setRotation(GLMath.PI_2_3, GLMath.PI, 0);
		transMat.rotate(0, 0, (float) GLFW.glfwGetTime() / 3f);

		mm.get("test").draw();
		spline.draw();
		program.disable();
	}

	@Override
	public void update(RenderContext context) {
		updateMatrices(context);
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
		// perspMat = MatrixMath.createOrthographicMatrix(-1, 1, 1, -1, -10,
		// 10);
		System.out.println(perspMat);
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
