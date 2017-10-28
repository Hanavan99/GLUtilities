package com.glutilities.gui;

import org.lwjgl.opengl.GL11;

import com.glutilities.text.FontManager;

public class GLTextArea extends GLTextComponent {

	@Override
	public void render() {
		// TODO Auto-generated method stub
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x + width, y);
		GL11.glVertex2d(x + width, y + height);
		GL11.glVertex2d(x, y + height);
		GL11.glEnd();
		FontManager.drawString(font, "testing", x, y, fontSize, 1);
	}

}
