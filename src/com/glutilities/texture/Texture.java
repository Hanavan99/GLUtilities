package com.glutilities.texture;

import org.lwjgl.opengl.GL11;

/**
 * Represents a texture that has been uploaded to VRAM via OpenGL. No texture
 * data is stored inside.
 * 
 * @author Hanavan99
 *
 */
public class Texture {

	private final int id;
	private final int width;
	private final int height;

	/**
	 * Creates a new texture with the OpenGL-assigned ID, and the image's width
	 * and height.
	 * 
	 * @param id the ID
	 * @param width the width of the texture
	 * @param height the height of the texture
	 */
	public Texture(int id, int width, int height) {
		this.id = id;
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the ID
	 */
	public int getID() {
		return id;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Binds the texture to OpenGL so that any subsequent triangles or quads
	 * that are drawn are shaded with the texture.
	 */
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}

}
