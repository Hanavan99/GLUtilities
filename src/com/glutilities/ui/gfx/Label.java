package com.glutilities.ui.gfx;

import com.glutilities.buffer.VBO;
import com.glutilities.util.Vertex3f;
import com.glutilities.util.Vertex4f;

public class Label extends UIElement {

	private String text = "";
	private VBO back;

	public Label(Vertex3f pos, Vertex3f size) {
		super(pos, size);
	}
	
	@Override
	public void setup(UIHandle handle) {
		back = handle.createBox(0, 0, 1, 1, Vertex4f.LIGHTGRAY);
	}

	@Override
	public void render(UIHandle handle) {
		handle.setRenderMode(UIHandle.MODE_COLOR_ONLY);
		back.draw();
		handle.setRenderMode(UIHandle.MODE_TEXTURE_ONLY);
		handle.drawText("default", text);
	}
	
}
