package com.glutilities.gui.fbo;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

public class Framebuffer {

	private int framebuffer;
	private int framebuffertex;

	public Framebuffer(int width, int height) {
		createFramebuffer();
		createTextureAttachment(width, height);
	}

	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebuffertex);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebuffer);
	}
	
	public void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}
	
	public void createFramebuffer() {
		framebuffer = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebuffer);
		GL11.glDrawBuffer(framebuffer);
	}

	public void createTextureAttachment(int width, int height) {
		framebuffertex = GL11.glGenTextures();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebuffer);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebuffertex);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE,
				(ByteBuffer) null);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, framebuffertex, 0);
	}
	
	public void renderToQuad() {
		GL11.glColor3d(1, 1, 1);
		//GL11.glEnable(GL11.GL_TEXTURE_2D);
		//GL13.glActiveTexture(GL13.GL_TEXTURE0);
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebuffertex);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2d(0, 0);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2d(0, 1);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2d(1, 1);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2d(1, 0);
		GL11.glEnd();
		//GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public void renderToScreen(int width, int height) {
		GL30.glBlitFramebuffer(0, 0, width, height, 0, 0, width, height, GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, GL11.GL_NEAREST);
	}

}
