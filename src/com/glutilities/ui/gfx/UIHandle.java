package com.glutilities.ui.gfx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.glutilities.buffer.VBO;
import com.glutilities.model.BufferedModelManager;
import com.glutilities.shader.ShaderProgram;
import com.glutilities.text.Font;
import com.glutilities.text.FontManager;
import com.glutilities.texture.TextureManager;
import com.glutilities.util.ArrayUtils;
import com.glutilities.util.Vertex3f;
import com.glutilities.util.matrix.Matrix4f;
import com.glutilities.util.matrix.MatrixMath;
import com.glutilities.util.matrix.MatrixStack;

public class UIHandle {
	
	public static final int MODE_COLOR_ONLY = 0x0;
	public static final int MODE_TEXTURE_AND_COLOR = 0x1;
	public static final int MODE_TEXTURE_ONLY = 0x2;

	private static final String vertexCode = null;
	private static final String fragmentCode = null;

	private ShaderProgram program;
	private FontManager fontManager;
	private BufferedModelManager modelManager;
	private TextureManager texManager;
	private Matrix4f projectionMatrix;
	private MatrixStack transformStack;
	private List<VBO> vbos = new ArrayList<VBO>();

	public UIHandle(ShaderProgram program) {
		this.program = program;
		this.fontManager = new FontManager();
		this.modelManager = new BufferedModelManager();
		this.texManager = new TextureManager();
		transformStack = new MatrixStack();
		this.projectionMatrix = MatrixMath.createOrthographicMatrix(-1, 1, 1, -1, -1, 1);
		
		transformStack.set(MatrixMath.setScale(Matrix4f.IDENTITY_MATRIX, new Vertex3f(0.25f, 0.25f, 1)));
		transformStack.push();
		loadFont(new Font("Arial", Font.PLAIN), "default");
	}
	
	public void updateProjectionMatrix() {
		program.setMatrix4f("projectionMatrix", projectionMatrix);
	}
	
	public void setRenderMode(int mode) {
		program.setInt("renderMode", mode);
	}
	
	public void drawText(String fontName, String text) {
		transformStack.push();
		transformStack.setScale(new Vertex3f(1 / 32f, 1 / 32f * 4f, 1));
		fontManager.get(fontName).draw(text, transformStack.get(), program, "transformMatrix", "glyphTex");
		transformStack.pop();
	}
	
	public void setRenderPos(Vertex3f pos) {
		transformStack.setTranslation(pos);
		program.setMatrix4f("transformMatrix", transformStack.get());
	}
	
	public void setRenderScale(Vertex3f scale) {
		transformStack.setScale(scale);
		program.setMatrix4f("transformMatrix", transformStack.get());
	}
	
	public void renderScale(Vertex3f scale) {
		transformStack.scale(scale);
		program.setMatrix4f("transformMatrix", transformStack.get());
	}
	
	public void pushMatrix() {
		transformStack.push();
	}
	
	public void popMatrix() {
		transformStack.pop();
	}

	public VBO createBorder(float x, float y, float width, float height, Vertex3f color) {
		float[] vertices = new float[] { 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0 };
		float[] colors = new float[12];
		ArrayUtils.fill(colors, new float[] { color.getR(), color.getG(), color.getB() });
		int[] indices = new int[] { 0, 1, 1, 2, 2, 3, 3, 0 };
		VBO vbo = new VBO(vertices, colors, indices, GL11.GL_LINES);
		vbo.create();
		vbos.add(vbo);
		return vbo;
	}
	
	public VBO createBox(float x, float y, float width, float height, Vertex3f color) {
		float[] vertices = new float[] { 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0 };
		float[] colors = new float[12];
		ArrayUtils.fill(colors, new float[] { color.getR(), color.getG(), color.getB() });
		VBO vbo = new VBO(vertices, colors, null, GL11.GL_QUADS);
		vbo.create();
		vbos.add(vbo);
		return vbo;
	}
	
	public void loadFont(Font font, String name) {
		fontManager.load(font, name);
	}
	
	public void loadModel(File model, String name) {
		modelManager.load(model, name);
	}

	/**
	 * Cleans up all resources used by this UI handle.
	 */
	public void cleanup() {
		fontManager.deleteResources();
		modelManager.deleteResources();
		texManager.deleteResources();
		for (VBO vbo : vbos) {
			vbo.delete();
		}
	}

}
