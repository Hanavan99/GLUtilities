package com.glutilities.buffer;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import com.glutilities.core.Bindable;
import com.glutilities.core.Reusable;
import com.glutilities.util.ArrayUtils;

/**
 * Represents an alternate Frame Buffer Object that OpenGL can draw onto.
 * 
 * @author Hanavan99
 *
 */
public class FBO implements Bindable, Reusable {

	/**
	 * The width of the attached textures; in pixels
	 */
	private int width;

	/**
	 * The height of the attached textures; in pixels
	 */
	private int height;

	/**
	 * The ID of this Frame Buffer Object
	 */
	private int framebuffer;

	/**
	 * The ID of the color texture
	 */
	private int framebuffertex;

	/**
	 * The ID of the depth texture
	 */
	private int depthbuffertex;

	/**
	 * Creates a new Frame Buffer Object with the given width and height.
	 * 
	 * @param width the width of the attached textures in pixels
	 * @param height the height of the attached textures in pixels
	 */
	public FBO(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Sets the size of the attached textures, but this does not immediately
	 * change their size. This FBO has to be deleted and created after.
	 * 
	 * @param width the new width of the attached textures in pixels
	 * @param height the new height of the attached textures in pixels
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	@Override
	public void bind() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebuffer);
	}

	@Override
	public void unbind() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}

	@Override
	public void create() {
		framebuffer = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebuffer);
		GL11.glDrawBuffer(framebuffer);

		// create texture attachments
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		framebuffertex = GL11.glGenTextures();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebuffertex);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);

		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, framebuffertex, 0);

		depthbuffertex = GL11.glGenTextures();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthbuffertex);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_DEPTH_COMPONENT, width, height, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer) null);

		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, depthbuffertex, 0);
	}

	@Override
	public void delete() {
		// delete framebuffer
		GL30.glDeleteFramebuffers(framebuffer);

		// delete texture attachments
		GL11.glDeleteTextures(framebuffertex);
		GL11.glDeleteTextures(depthbuffertex);
	}

	/**
	 * Not sure what this does
	 */
	public void clearFramebuffer() {
		GL30.glClearBufferfi(GL30.GL_DEPTH_STENCIL, framebuffer, 0, 0);
	}

	/**
	 * Takes the color buffer and bindes it to {@code GL_TEXURE0}, and binds the
	 * depth buffer to {@code GL_TEXTURE1}. These can then be sampled from a
	 * fragment shader to draw onto the given VBO.
	 * 
	 * @param vbo the VBO to draw onto
	 */
	public void renderToVBO(VBO vbo) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebuffertex);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthbuffertex);
		vbo.draw();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	/**
	 * Directly renders the color buffer onto the given VBO.
	 * 
	 * @param vbo the VBO to draw onto
	 */
	public void renderColorToVBO(VBO vbo) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebuffertex);
		vbo.draw();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	/**
	 * Not sure what this does either
	 * 
	 * @param width the width in pixels
	 * @param height the height in pixels
	 */
	@Deprecated
	public void renderToScreen(int width, int height) {
		GL30.glBlitFramebuffer(0, 0, width, height, 0, 0, width, height, GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, GL11.GL_NEAREST);
	}
	
	public int[] readPixels() {
		GL11.glReadBuffer(GL30.GL_COLOR_ATTACHMENT0);
		byte[] pixels = new byte[width * height * 3];
		GL11.glReadPixels(0, 0, width, height, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, ByteBuffer.wrap(pixels));
		int[] finalData = ArrayUtils.convert(pixels);
		return finalData;
	}

}
