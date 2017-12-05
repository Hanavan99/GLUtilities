package com.glutilities.ui.gfx;

import com.glutilities.buffer.VBO;
import com.glutilities.util.Vertex3f;
import com.glutilities.util.Vertex4f;

public class TextBox extends TextElement {

	//private String text = "";
	private VBO border;
	private VBO back;

	public TextBox(Vertex3f pos, Vertex3f size) {
		super(pos, size);
	}
	
	@Override
	public void setup(UIHandle handle) {
		super.setup(handle);
		border = handle.createBorder(0, 0, 1, 1, Vertex4f.DARKGRAY);
		back = handle.createBox(0, 0, 1, 1, Vertex4f.LIGHTGRAY);
	}

	@Override
	public void render(UIHandle handle) {
		handle.setRenderMode(UIHandle.MODE_COLOR_ONLY);
		//handle.setRenderScale(new Vertex3f(1 / 32f, 1 / 32f * 4, 1));
		back.draw();
		border.draw();
		super.render(handle);
	}
	
//	@Override
//	public void keyPressed(int key, int action) {
//		if ((action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT) && key == GLFW.GLFW_KEY_BACKSPACE && text.length() > 0) {
//			text = text.substring(0, text.length() - 1);
//		}
//	}
//	@Override
//	public void textTyped(char c) {
//		text += c;
//	}

}
