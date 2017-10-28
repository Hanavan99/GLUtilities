package com.glutilities.text;

import java.awt.Font;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.HashMap;
import java.util.Map;

public class FontManager {

	private static Map<String, Glyph[]> fonts = new HashMap<String, Glyph[]>();

	public static void loadFont(String name, int type) {
		AffineTransform trans = new AffineTransform(1, 0, 0, 1, 0, 0);
		Font f = new Font(name, Font.PLAIN, 12);
		FontRenderContext context = new FontRenderContext(trans, true, true);
		Glyph[] glyphs = new Glyph[128];
		for (int glyphNum = 0; glyphNum < 128; glyphNum++) {
			GlyphVector v = f.createGlyphVector(context, new String(new char[] { (char) glyphNum }));
			Shape s = v.getOutline(0, 0);
			PathIterator it = s.getPathIterator(trans);
			double[] xPoints = new double[1024];
			double[] yPoints = new double[1024];
			int[] flags = new int[1024];
			int i = 0;
			while (!it.isDone()) {
				double[] data = new double[6];
				int code = it.currentSegment(data);
				it.next();
				flags[i] = code;
				switch (code) {
				case PathIterator.SEG_CLOSE:
					xPoints[i] = data[0];
					yPoints[i] = data[1];
					i++;
					break;
				case PathIterator.SEG_CUBICTO:
					xPoints[i] = data[0];
					yPoints[i] = data[1];
					xPoints[i + 1] = data[2];
					yPoints[i + 1] = data[3];
					xPoints[i + 2] = data[4];
					yPoints[i + 2] = data[5];
					i += 3;
					break;
				case PathIterator.SEG_LINETO:
					xPoints[i] = data[0];
					yPoints[i] = data[1];
					i++;
					break;
				case PathIterator.SEG_MOVETO:
					xPoints[i] = data[0];
					yPoints[i] = data[1];
					i++;
					break;
				case PathIterator.SEG_QUADTO:
					xPoints[i] = data[0];
					yPoints[i] = data[1];
					xPoints[i + 1] = data[2];
					yPoints[i + 1] = data[3];
					i += 2;
					break;
				}
			}
			glyphs[i] = new Glyph(xPoints, yPoints, s.getBounds().getWidth(), s.getBounds().getHeight(), flags);
		}
		fonts.put(name.toLowerCase(), glyphs);
	}

	public static void drawString(String font, String text, double x, double y, double size, double aspect) {
		char[] data = text.toCharArray();
		double dx = 0;
		Glyph[] glyphs = fonts.get(font.toLowerCase());
		for (char c : data) {
			Glyph g = glyphs[(int) c];
			g.draw(x + dx, y);
			dx += g.getWidth() + 0.1;
		}
	}

}
