package com.glutilities.text;

import com.glutilities.resource.ResourceManager;

public class FontManager extends ResourceManager<Charset, String, String> {

	public FontManager() {
		super(new FontLoader());
	}

	public static void drawString(Charset charset, String text, double x, double y, double size, double aspect) {
		char[] data = text.toCharArray();
		double dx = 0;
		Glyph[] glyphs = charset.getGlyphs();
		for (char c : data) {
			Glyph g = glyphs[(int) c];
			g.draw(x + dx, y);
			dx += g.getWidth() + 0.1;
		}
	}

}
