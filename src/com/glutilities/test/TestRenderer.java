package com.glutilities.test;

//import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

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
import com.glutilities.texture.TextureManager;
import com.glutilities.ui.MasterRenderer;
import com.glutilities.ui.RenderContext;
import com.glutilities.util.ArrayUtils;
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
	private TextureManager tm;
	private TransformMatrix transMat;
	private Model spline;
	private VBO ground;
	private FBO fbo;
	private VBO fbovbo;

	@Override
	public void init(RenderContext context) {

		// Create a model manager to load models
		mm = new ModelManager();
		mm.load(new File("res/models/cube.obj"), "test");
		mm.load(new File("res/models/locomotive.obj"), "train");
		spline = SplineModelBuilder.build(new float[] { -30, 0, 30, 0 }, 20, new float[] { 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1 });

		// Create a font manager to load fonts
		fm = new FontManager();
		fm.load(new Font("Arial", Font.PLAIN, Vertex4f.WHITE, Vertex4f.BLUE, Vertex4f.BLACK, 0, 0, 0, 6), "test");
		
		// load textures
		tm = new TextureManager();
		tm.load(new File("res/textures/grass.jpg"), "grass");

		// Create 2 shader programs
		VertexShader vertex = new VertexShader(null);
		FragmentShader fragment = new FragmentShader(null);
		updateShaderCode(vertex, "res/shaders/samplevertex.vsh", fragment, "res/shaders/samplefragment.fsh");
		vertex.create();
		fragment.create();
		program = new ShaderProgram(vertex, fragment);
		program.create();
		program.link();

		VertexShader vertex2 = new VertexShader(null);
		FragmentShader fragment2 = new FragmentShader(null);
		updateShaderCode(vertex2, "res/shaders/test.vsh", fragment2, "res/shaders/test.fsh");
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
		List<Float> verts = new ArrayList<Float>();
		for (int i = -100; i < 100; i++) {
			for (int j = -100; j < 100; j++) {
				verts.add((float) i);
				verts.add((float) j);
				verts.add(0f);
				verts.add((float) i + 1);
				verts.add((float) j);
				verts.add(0f);
				verts.add((float) i + 1);
				verts.add((float) j + 1);
				verts.add(0f);
				verts.add((float) i);
				verts.add((float) j + 1);
				verts.add(0f);
			}
		}
		AttributeArray groundverts = new AttributeArray(0, verts.toArray(new Float[0]), 3);
		Float[] texcs = new Float[verts.size()];
		ArrayUtils.fill(texcs, new Float[] {0f, 0f, 0f, 1f, 0f, 0f, 1f, 1f, 0f, 0f, 1f, 0f});
		AttributeArray groundtex = new AttributeArray(1, texcs, 3);
		ground = new VBO(new AttributeArray[] { groundverts, groundtex }, verts.size() / 12, GL11.GL_QUADS);
		ground.create();

		fbo = new FBO(800, 600);
		fbo.create();

		AttributeArray fbovertex = new AttributeArray(0, new Float[] { -1f, -1f, 0f, -1f, 1f, 0f, 1f, 1f, 0f, 1f, -1f, 0f }, 3);
		AttributeArray fbotex = new AttributeArray(3, new Float[] { 0f, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 0f, 1f, 0f, 0f }, 3);

		fbovbo = new VBO(new AttributeArray[] { fbotex, fbovertex }, 4, GL11.GL_QUADS);
		fbovbo.create();
	}

	@Override
	public void render(RenderContext context) {
		clearViewport(context.getWidth(), context.getHeight());

		transMat.reset();

		program.enable();

		//fbo.bind();
		//fbo.clearFramebuffer();

		

		program.setInt("renderMode", 1);
		program.setMatrix4f("projectionMatrix", perspMat);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		tm.get("grass").bind();
		ground.draw();
		transMat.setTranslation(0, 0, -15);

		transMat.setScale(1, 1, 1);
		
		transMat.setRotation(GLMath.PI_2_3, GLMath.PI, 0);
		
		transMat.rotate(0, 0, (float) GLFW.glfwGetTime() / 4f);

		program.setInt("renderMode", 0);
		mm.get("train").draw(program);
		

		//fbo.unbind();

		program.disable();
//		fboProgram.enable();
//		fboProgram.setInt("colorfbo", 0);
//		fboProgram.setInt("depthfbo", 1);
//		fboProgram.setFloat("time", (float) GLFW.glfwGetTime() % 30);
//		fbo.renderToVBO(fbovbo);
//
//		fboProgram.disable();
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
				updateShaderCode(program.getVertexShader(), "res/shaders/samplevertex.vsh", program.getFragmentShader(), "res/shaders/samplefragment.fsh");
				updateShaderCode(fboProgram.getVertexShader(), "res/shaders/test.vsh", fboProgram.getFragmentShader(), "res/shaders/test.fsh");
				program.link();
				fboProgram.link();
				System.out.println("Shader Linked\n" + program.getError());
				System.out.println("FBO Shader Linked\n" + fboProgram.getError());
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
