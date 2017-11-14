package com.glutilities.text;

import java.awt.Font;

import org.lwjgl.opengl.GL11;

import com.glutilities.resource.ResourceManager;
import com.glutilities.shader.ShaderProgram;
import com.glutilities.util.Vertex3f;
import com.glutilities.util.matrix.Matrix4f;
import com.glutilities.util.matrix.MatrixMath;

public class FontManager extends ResourceManager<Charset, String, Font> {

	public FontManager() {
		super(new FontLoader());
	}

	@Deprecated
	public static void drawString(Charset charset, String text, double x, double y, double size, double aspect, double kerning) {
		char[] data = text.toCharArray();
		double dx = 0;
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, 0);
		GL11.glScaled(size, size * aspect, 1);

		for (char c : data) {
			GL11.glTranslated(dx, 0, 0);
			NewGlyph g = charset.getGlyphForChar(c);
			g.draw();
			// if (c == ' ') {
			// dx = 5;
			// } else {
			dx = g.getWidth() + kerning;
			// }
		}
		GL11.glPopMatrix();
	}

	public static void drawString(Charset charset, String text, Matrix4f transform, float kerning, ShaderProgram program, String tmn) {
		char[] data = text.toCharArray();
		float xoff = 0;
		for (char c : data) {
			program.glUniformMatrix4f(tmn, true, transform);
			NewGlyph g = charset.getGlyphForChar(c);
			g.draw();
			if (c == '\n') {
				transform = MatrixMath.translate(transform, new Vertex3f(-xoff, 0.1f, 0));

			} else {
				xoff += g.getWidth() / 20f;
				transform = MatrixMath.translate(transform, new Vertex3f(g.getWidth() / 20f, 0, 0));
			}
		}

	}

}
