package com.glutilities.test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.glutilities.model.ModelManager;
import com.glutilities.shader.FragmentShader;
import com.glutilities.shader.ShaderObject;
import com.glutilities.shader.ShaderProgram;
import com.glutilities.shader.VertexShader;
import com.glutilities.text.Font;
import com.glutilities.text.FontManager;
import com.glutilities.texture.TextureManager;
import com.glutilities.ui.MasterRenderer;
import com.glutilities.ui.RenderContext;
import com.glutilities.util.Vertex4f;
import com.glutilities.util.matrix.Matrix4f;
import com.glutilities.util.matrix.MatrixMath;
import com.glutilities.util.matrix.TransformMatrix;

public class TextDemo extends MasterRenderer {

	private Matrix4f perspMat;
	private ShaderProgram program;
	private ModelManager mm;
	private FontManager fm;
	private TextureManager tm;
	private TransformMatrix transMat;

	@Override
	public void init(RenderContext context) {

		fm = new FontManager();
		fm.load(new Font("Arial", Font.PLAIN, Vertex4f.WHITE, Vertex4f.BLUE, Vertex4f.BLACK, 0, 0, 0, 6), "test");
		
		// Create 2 shader programs
		VertexShader vertex = new VertexShader(null);
		FragmentShader fragment = new FragmentShader(null);
		updateShaderCode(vertex, "res/shaders/samplevertex.vsh", fragment, "res/shaders/samplefragment.fsh");
		vertex.create();
		fragment.create();
		program = new ShaderProgram(vertex, fragment);
		program.create();
		program.link();

		// Set up some matrices
		updateMatrices(context);
		transMat = new TransformMatrix(program, "transformMatrix");
	}

	@Override
	public void render(RenderContext context) {
		clearViewport(context.getWidth(), context.getHeight());

		transMat.reset();
		program.enable();
		transMat.scale(1.0f, 1.0f, 1.0f);
		
		program.setMatrix4f("projectionMatrix", perspMat);
		program.setMatrix4f("transformMatrix", transMat.getMatrix());
		program.setInt("renderMode", 2);
		//fm.get("test").draw("ABC", transMat.getMatrix(), program, "transformMatrix", "glyphTex");
		fm.get("test").draw("AB", transMat, program, "glyphTex");
		
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
				updateShaderCode(program.getVertexShader(), "res/shaders/samplevertex.vsh", program.getFragmentShader(), "res/shaders/samplefragment.fsh");
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
		//perspMat = MatrixMath.createPerspectiveMatrix(90f, context.getAspect(), 1f, 300f);
		perspMat = MatrixMath.createOrthographicMatrix(-10, 10, -10, 10, -1, 1);
	}

	private void updateShaderCode(ShaderObject vertex, String vfile, ShaderObject fragment, String ffile) {
		if (vertex != null) {
			vertex.setCode(loadFile(new File(vfile)));
		}
		if (fragment != null) {
			fragment.setCode(loadFile(new File(ffile)));
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
