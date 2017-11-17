package com.glutilities.text;

import com.glutilities.buffer.VBO;
import com.glutilities.shader.ShaderProgram;
import com.glutilities.texture.Texture;
import com.glutilities.util.Vertex3f;
import com.glutilities.util.matrix.Matrix4f;
import com.glutilities.util.matrix.MatrixMath;

public class Charset {

	private String name;
	private Font font;
	private Glyph[] glyphs;
	private VBO charVBO;
	private Texture charmap;

	public Charset(String name, Font font, Glyph[] glyphs, VBO charVBO, Texture charmap) {
		this.name = name;
		this.font = font;
		this.glyphs = glyphs;
		this.charVBO = charVBO;
		this.charmap = charmap;
	}

	public String getName() {
		return name;
	}

	public Font getFont() {
		return font;
	}

	public Glyph getGlyphForChar(char c) {
		for (Glyph glyph : glyphs) {
			if (glyph.getCharacter() == c) {
				return glyph;
			}
		}
		return null;
	}

	public void draw(String text, Matrix4f transform, ShaderProgram program, String matTransformName, String texSamplerName) {
		int lines = 0;
		float scale = MatrixMath.getScale(transform).getX() * 20f;
		Matrix4f fork = (Matrix4f) transform.clone();
		fork = MatrixMath.translate(fork, new Vertex3f(0, font.getLeading() * lines, 0));
		charVBO.bind();
		charmap.bind();
		program.setInt(texSamplerName, 0);
		if (text != null) {
			for (char c : text.toCharArray()) {
				Glyph g = getGlyphForChar(c);
				if (g != null) {
					program.setMatrix4f(matTransformName, fork);
					fork = MatrixMath.translate(fork, new Vertex3f((g.getWidth() / 20f + font.getKerning()) * scale, 0, 0));
					int base = c * 4;
					charVBO.draw(base, 4);
				}
				if (c == '\n') {
					lines++;
					fork = (Matrix4f) transform.clone();
					fork = MatrixMath.translate(fork, new Vertex3f(0, font.getLeading() * lines * scale, 0));
				}
				if (c == '\t') {
					float curx = fork.get(0, 3);
					fork = fork.set(0, 3, curx + (0.2f * scale));
				}

			}
		}
		charmap.unbind();
		charVBO.unbind();
	}

}
