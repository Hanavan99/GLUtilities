package com.glutilities.text.texture;

import com.glutilities.buffer.VBO;
import com.glutilities.shader.ShaderProgram;
import com.glutilities.texture.Texture;
import com.glutilities.util.Vertex3f;
import com.glutilities.util.matrix.Matrix4f;
import com.glutilities.util.matrix.MatrixMath;

public class Charset {

	private String name;
	private Glyph[] glyphs;
	private VBO charVBO;
	private Texture charmap;

	public Charset(String name, Glyph[] glyphs, VBO charVBO, Texture charmap) {
		this.name = name;
		this.glyphs = glyphs;
		this.charVBO = charVBO;
		this.charmap = charmap;
	}

	public String getName() {
		return name;
	}

	public Glyph getGlyphForChar(char c) {
		for (Glyph glyph : glyphs) {
			if (glyph.getCharacter() == c) {
				return glyph;
			}
		}
		return null;
	}

	public void draw(String text, TextFormat format, Matrix4f transform, ShaderProgram program, String matTransformName, String texSamplerName) {
		int lines = 0;
		float scale = MatrixMath.getScale(transform).getX() * 20f;
		Matrix4f fork = (Matrix4f) transform.clone();
		fork = MatrixMath.translate(fork, new Vertex3f(0, format.leading * lines, 0));
		// charVBO.draw();
		charVBO.bind();
		charmap.bind();
		program.glUniform1i(texSamplerName, 0);
		for (char c : text.toCharArray()) {
			Glyph g = getGlyphForChar(c);
			if (g != null) {
				program.glUniformMatrix4f(matTransformName, true, fork);
				// g.draw(program, texSamplerName);
				fork = MatrixMath.translate(fork, new Vertex3f((g.getWidth() / 20f + format.kerning) * scale, 0, 0));
				int base = c * 4;
				charVBO.draw(base, 4);
			}
			if (c == '\n') {
				lines++;
				fork = (Matrix4f) transform.clone();
				fork = MatrixMath.translate(fork, new Vertex3f(0, format.leading * lines * scale, 0));
			}
			if (c == '\t') {
				float curx = fork.get(0, 3);
				fork = fork.set(0, 3, curx + (0.2f * scale));
			}

		}
		charmap.unbind();
		charVBO.unbind();
	}

}
