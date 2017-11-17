package com.glutilities.ui.gfx;

import org.lwjgl.glfw.GLFW;

import com.glutilities.buffer.VBO;
import com.glutilities.util.Vertex3f;
import com.glutilities.util.Vertex4f;

public class TextBox extends UIElement {

	private String text;
	private VBO border;
	private VBO back;

	public TextBox(Vertex3f pos, Vertex3f size) {
		super(pos, size);
	}
	
	@Override
	public void setup(UIHandle handle) {
		border = handle.createBorder(0, 0, 1, 1, Vertex4f.BLUE);
		back = handle.createBox(0, 0, 1, 1, Vertex4f.WHITE);
	}

	@Override
	public void render(UIHandle handle) {
		handle.setRenderMode(UIHandle.MODE_COLOR_ONLY);
		//handle.setRenderScale(new Vertex3f(1 / 32f, 1 / 32f * 4, 1));
		back.draw();
		border.draw();
		handle.setRenderMode(UIHandle.MODE_TEXTURE_ONLY);
		handle.drawText("default", text);
	}
	
	@Override
	public void keyPressed(int key, int action) {
		if (action == GLFW.GLFW_PRESS && key == GLFW.GLFW_KEY_A) {
			text += "a";
		}
	}

}
