package com.glutilities.ui.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.glutilities.ui.MasterRenderer;
import com.glutilities.ui.RenderContext;
import com.glutilities.ui.fbo.FBO;

public final class JGLComponent extends JComponent {

	private static final long serialVersionUID = -8947228093467930990L;

	private final RenderContext context;

	private FBO fbo;

	private MasterRenderer renderer;

	public JGLComponent() {
		GL.createCapabilitiesWGL();

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SPECULAR, new float[] { 1, 1, 1, 1 });
		GL11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SHININESS, new float[] { 50, 0, 0, 0 });
		GL11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, new float[] { 15, 15, 15, 0 });
		GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE);
		
		context = new RenderContext() {

			@Override
			public int getWidth() {
				return JGLComponent.this.getWidth();
			}

			@Override
			public int getHeight() {
				return JGLComponent.this.getHeight();
			}

		};
		fbo = new FBO(getWidth(), getHeight());
		
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				renderer.keyPressed(context, arg0.getKeyCode(), GLFW.GLFW_PRESS);
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				renderer.keyPressed(context, arg0.getKeyCode(), GLFW.GLFW_RELEASE);
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
		});
		
	}

	public void setMasterRenderer(MasterRenderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public void paint(Graphics g) {
		
		checkGL();
		
		fbo.bind();
		
		renderer.render(context);
		
		fbo.unbind();

		int[] data = fbo.readPixels();
		for (int i = 0; i < data.length; i += 3) {
			int x = (i / 3) % fbo.getWidth();
			int y = (i / 3) / fbo.getWidth();
			g.setColor(new Color(data[i], data[i + 1], data[i + 2]));
			g.drawRect(x, y, 1, 1);
		}
	}
	
	private void checkGL() {
		
	}

}
