package com.glutilities.text;

import java.awt.Font;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.glutilities.buffer.VBO;
import com.glutilities.resource.ResourceLoader;
import com.glutilities.util.ArrayUtils;
import com.glutilities.util.EarClipper;
import com.glutilities.util.GLMath;

public class FontLoader extends ResourceLoader<Charset, String, Font> {

	private static final AffineTransform transform = new AffineTransform(1, 0, 0, 1, 0, 0);
	
	@Override
	public Charset load(Font src, String key) throws IOException {
		FontRenderContext context = new FontRenderContext(transform, true, true);
		List<NewGlyph> glyphs = new ArrayList<NewGlyph>();
		for (char c = (char) 0; c < (char) 256; c++) {
			GlyphVector vector = src.createGlyphVector(context, new char[] { c });
			Shape glyph = vector.getOutline(0, 0);
			PathIterator pathIterator = glyph.getPathIterator(transform);
			float width = (float) vector.getLogicalBounds().getWidth();
			float height = (float) vector.getLogicalBounds().getHeight();
			float[] xcoords = new float[1024];
			float[] ycoords = new float[1024];
			int[] flags = new int[1024];
			int i = 0;
			while (!pathIterator.isDone()) {
				float[] data = new float[6];
				int code = pathIterator.currentSegment(data);
				pathIterator.next();
				flags[i] = code;
				switch (code) {
				case PathIterator.SEG_CLOSE:
					xcoords[i] = data[0];
					ycoords[i] = data[1];
					i++;
					break;
				case PathIterator.SEG_CUBICTO:
					xcoords[i] = data[0];
					ycoords[i] = data[1];
					xcoords[i + 1] = data[2];
					ycoords[i + 1] = data[3];
					xcoords[i + 2] = data[4];
					ycoords[i + 2] = data[5];
					i += 3;
					break;
				case PathIterator.SEG_LINETO:
					xcoords[i] = data[0];
					ycoords[i] = data[1];
					i++;
					break;
				case PathIterator.SEG_MOVETO:
					xcoords[i] = data[0];
					ycoords[i] = data[1];
					i++;
					break;
				case PathIterator.SEG_QUADTO:
					xcoords[i] = data[0];
					ycoords[i] = data[1];
					xcoords[i + 1] = data[2];
					ycoords[i + 1] = data[3];
					i += 2;
					break;
				}
			}
			glyphs.add(buildGlyph(c, width, height, xcoords, ycoords, flags));
		}
		return new Charset(key, glyphs.toArray(new NewGlyph[0]));
	}
	
	private NewGlyph buildGlyph(char c, float width, float height, float[] xPoints, float[] yPoints, int[] flags) {
		List<VBO> vbos = new ArrayList<VBO>();
		List<Float> abscoords = new ArrayList<Float>();
		float dx = 0;
		float dy = 0;
		float lastx = 0;
		float lasty = 0;
		int i = 0;
		float zoff = 0;
		while (i < xPoints.length) {
			switch (flags[i]) {
			case PathIterator.SEG_CLOSE:
				abscoords.add(lastx);
				abscoords.add(lasty);
				abscoords.add(zoff);
				abscoords.add(dx);
				abscoords.add(dy);
				abscoords.add(zoff);
				VBO vbo = new VBO(buildMesh(ArrayUtils.toPrimitiveArray(abscoords.toArray(new Float[0]))), null, null, null, null, GL11.GL_TRIANGLES);
				vbo.create();
				vbos.add(vbo);
				abscoords.clear();
				i++;
				break;
			case PathIterator.SEG_CUBICTO:
				abscoords.add(lastx);
				abscoords.add(lasty);
				abscoords.add(zoff);
				abscoords.add(lastx = xPoints[i + 2]);
				abscoords.add(lasty = yPoints[i + 2]);
				abscoords.add(zoff);
				i += 3;
				break;
			case PathIterator.SEG_LINETO:
				abscoords.add(lastx);
				abscoords.add(lasty);
				abscoords.add(zoff);
				abscoords.add(lastx = xPoints[i]);
				abscoords.add(lasty = yPoints[i]);
				abscoords.add(zoff);
				i++;
				break;
			case PathIterator.SEG_MOVETO:
				dx = lastx = xPoints[i];
				dy = lasty = yPoints[i];
				i++;
				break;
			case PathIterator.SEG_QUADTO:
				float _x = lastx;
				float _y = lasty;
				for (float t = 0; t <= 1; t += 0.1) {
					float[] b = GLMath.quadBezier(_x, _y, xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1], t);
					abscoords.add(lastx);
					abscoords.add(lasty);
					abscoords.add(zoff);
					abscoords.add(lastx = b[0]);
					abscoords.add(lasty = b[1]);
					abscoords.add(zoff);
				}
				i += 2;
				break;
			}
		}
		return new NewGlyph(c, width, height, vbos.toArray(new VBO[0]));
	}
	
	private float[] buildMesh(float[] vertices) {
//		List<Float> mesh = new ArrayList<Float>();
//		for (int i = 0; i < vertices.length; i++) {
//			mesh.add(vertices[i]);
//		}
//		return ArrayUtils.toPrimitiveArray(mesh.toArray(new Float[0]));
		return EarClipper.clip(vertices, 5000, 0.5f);
	}

	@Override
	public boolean keyReferencesResource(Charset resource, String key) {
		return resource.getName().equals(key);
	}

	@Override
	public String getKeyFromResource(Charset resource) {
		return resource.getName();
	}

}
