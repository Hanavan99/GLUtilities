package com.glutilities.text;

import org.lwjgl.opengl.GL11;

import com.glutilities.resource.ResourceManager;

public class FontManager extends ResourceManager<Charset, String, String> {

	public FontManager() {
		super(new FontLoader());
	}

	public static void drawString(Charset charset, String text, double x, double y, double size, double aspect, double kerning) {
		char[] data = text.toCharArray();
		double dx = 0;
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, 0);
		GL11.glScaled(size, size * aspect, 1);
		
		for (char c : data) {
			GL11.glTranslated(dx, 0, 0);
			Glyph g = charset.getGlyphForChar(c);
			g.draw();
			dx = g.getWidth() + kerning;
		}
		GL11.glPopMatrix();
	}

}
