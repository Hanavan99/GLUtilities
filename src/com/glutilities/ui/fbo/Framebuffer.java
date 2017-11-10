package com.glutilities.ui.fbo;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.glutilities.buffer.VBO;
import com.glutilities.core.Bindable;
import com.glutilities.core.Reusable;
import com.glutilities.shader.ShaderProgram;

public class Framebuffer implements Bindable, Reusable {

	private int width;
	private int height;

	private int framebuffer;
	private int framebuffertex;
	private int depthbuffertex;
	//private int renderbuffer;

	public Framebuffer(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void bind() {
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebuffertex);
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthbuffertex);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebuffer);
		//GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, renderbuffer);
	}

	@Override
	public void unbind() {
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		//GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, 0);
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

		// create renderbuffer attachment
		//renderbuffer = GL30.glGenRenderbuffers();
		//GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, renderbuffer);

		//GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL30.GL_DEPTH_COMPONENT32F, width, height);

		//GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, renderbuffer);
		
	}

	@Override
	public void delete() {
		// delete framebuffer
		GL30.glDeleteFramebuffers(framebuffer);

		// delete texture attachments
		GL11.glDeleteTextures(framebuffertex);
		GL11.glDeleteTextures(depthbuffertex);

		// delete renderbuffer attachment
		//GL30.glDeleteRenderbuffers(renderbuffer);
	}

	public void clearFramebuffer() {
		GL30.glClearBufferfi(GL30.GL_DEPTH_STENCIL, framebuffer, 0, 0);
	}

	public void renderToQuad() {
		renderToQuad(0, 0, 0, 1, 1, 1, 1, 0);
	}
	
	public void renderToQuad(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		GL11.glColor3d(1, 1, 1);
		//GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebuffertex);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2d(0, 0);
		GL11.glVertex2d(x1, y1);
		GL11.glTexCoord2d(0, 1);
		GL11.glVertex2d(x2, y2);
		GL11.glTexCoord2d(1, 1);
		GL11.glVertex2d(x3, y3);
		GL11.glTexCoord2d(1, 0);
		GL11.glVertex2d(x4, y4);
		GL11.glEnd();
		//GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	public void renderToVBO(VBO vbo, ShaderProgram program) {
		GL11.glColor3d(1, 1, 1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebuffertex);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthbuffertex);
		
		vbo.draw();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public void renderToScreen(int width, int height) {
		GL30.glBlitFramebuffer(0, 0, width, height, 0, 0, width, height, GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, GL11.GL_NEAREST);
	}

}
