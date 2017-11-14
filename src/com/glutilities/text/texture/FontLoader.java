package com.glutilities.text.texture;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import com.glutilities.buffer.VBO;
import com.glutilities.resource.ResourceLoader;
import com.glutilities.texture.Texture;
import com.glutilities.util.ArrayUtils;
import com.glutilities.util.Rectangle;
import com.glutilities.util.Vertex2f;

public class FontLoader extends ResourceLoader<Charset, String, com.glutilities.text.texture.Font> {

	private static final int GLYPHS = 16;

	private TextFormat format;

	@Override
	public Charset load(com.glutilities.text.texture.Font src, String key) throws IOException {
		Glyph[] glyphs = new Glyph[128];
		final int glyphSize = (int) Math.pow(2, format.textureScale);
		final BufferedImage image = new BufferedImage(glyphSize * FontLoader.GLYPHS, glyphSize * FontLoader.GLYPHS * 2, BufferedImage.TYPE_INT_ARGB);
		Font internalFont = new Font(src.getName(), src.getStyle(), glyphSize);
		Graphics g = image.getGraphics();
		FontMetrics metrics = g.getFontMetrics();
		g.setFont(internalFont);
		g.setColor(Color.RED);
		float[] vboVerts = new float[GLYPHS * GLYPHS * 4 * 3];
		float[] vboTexCoords = new float[GLYPHS * GLYPHS * 4 * 3];
		int textHeight = internalFont.getSize() * (metrics.getAscent() + metrics.getDescent()) / metrics.getAscent();
		for (char c = 0; c < glyphs.length; c++) {
			int x = c % GLYPHS;
			int y = c / GLYPHS;
			int imgx = x * glyphSize;
			int imgy = y * glyphSize * 2;
			int textWidth = g.getFontMetrics().charWidth(c);
			float texx = imgx / (float) (GLYPHS * glyphSize);
			float texy = imgy / (float) (GLYPHS * glyphSize) / 2;
			float texy1 = (imgy + glyphSize) / (float) (GLYPHS * glyphSize) / 2;
			Rectangle texCoords = new Rectangle(texx, texy, texx + (textWidth / (float) (GLYPHS * glyphSize)), texy1);
			g.drawString(String.valueOf(c), imgx, imgy + (int) (glyphSize * 0.78f));
			float width = textWidth / 20f * (float) Math.pow(2, 6 - format.textureScale);
			//float texWidth = textWidth / (float) glyphSize;
			float[] averts = new float[] { 0, 0, 0, 0, 1, 0, width, 1, 0, width, 0, 0 };
			Vertex2f a = texCoords.getMin();
			Vertex2f b = texCoords.getMax();
			float[] atexCoords = new float[] { a.getX(), a.getY(), 0, a.getX(), b.getY(), 0, b.getX(), b.getY(), 0, b.getX(), a.getY(), 0 };
			System.arraycopy(averts, 0, vboVerts, (int) c * 12, averts.length);
			System.arraycopy(atexCoords, 0, vboTexCoords, (int) c * 12, atexCoords.length);
			glyphs[(int) c] = new Glyph(c, texCoords, width);
		}
		VBO vbo = new VBO(vboVerts, null, null, vboTexCoords, null, GL11.GL_QUADS);
		vbo.create();
		Texture charmap = build(image, "a");
		//ImageIO.write(image, "PNG", new File("res/test.png"));
		return new Charset(key, glyphs, vbo, charmap);
	}

	public Texture build(BufferedImage img, String key) throws IOException {
		int tex = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		Texture t = null;
		float[] data = new float[img.getWidth() * img.getHeight() * 3];
		for (int i = 0; i < img.getWidth() * img.getHeight(); i++) {
			int x = i % img.getWidth();
			int y = i / img.getWidth();
			Color c = new Color(img.getRGB(x, y));
			data[i * 3 + 0] = (float) c.getRed() / 255;
			data[i * 3 + 1] = (float) c.getGreen() / 255;
			data[i * 3 + 2] = (float) c.getBlue() / 255;

		}
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, img.getWidth(), img.getHeight(), 0, GL11.GL_RGB, GL11.GL_FLOAT, data);
		t = new Texture(tex, key, img.getWidth(), img.getHeight());
		return t;
	}

	@Override
	public boolean keyReferencesResource(Charset resource, String key) {
		return resource.getName().equals(key);
	}

	@Override
	public String getKeyFromResource(Charset resource) {
		return resource.getName();
	}

	

	public void setTextFormat(TextFormat format) {
		this.format = format;
	}

}