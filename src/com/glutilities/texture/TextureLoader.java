package com.glutilities.texture;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import com.glutilities.resource.ResourceLoader;

public class TextureLoader extends ResourceLoader<Texture, String, File> {

	@Override
	public Texture load(File src, String key) throws IOException {
		BufferedImage img = ImageIO.read(src);
		int tex = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
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
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, img.getWidth(), img.getHeight(), 0, GL11.GL_RGB,
					GL11.GL_FLOAT, data);
			t = new Texture(tex, key, img.getWidth(), img.getHeight());

		}
		return t;
	}

	@Override
	public boolean keyReferencesResource(Texture resource, String key) {
		return resource.getName().equals(key);
	}

	@Override
	public String getKeyFromResource(Texture resource) {
		return resource.getName();
	}

}
