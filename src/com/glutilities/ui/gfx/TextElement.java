package com.glutilities.ui.gfx;

import org.lwjgl.glfw.GLFW;

import com.glutilities.buffer.VBO;
import com.glutilities.util.Vertex3f;
import com.glutilities.util.Vertex4f;

public class TextElement extends UIElement {

	private String text = "";
	private int caretPos = 0;
	private VBO caret;

	public TextElement(Vertex3f pos, Vertex3f size) {
		super(pos, size);
	}

	@Override
	public void setup(UIHandle handle) {
		caret = handle.createLine(getPosition().getX(), getPosition().getY(), getPosition().getX(), getPosition().getY() + 20, Vertex4f.BLACK);
	}

	@Override
	public void render(UIHandle handle) {
		// handle.setRenderMode(UIHandle.MODE_COLOR_ONLY);
		// handle.pushMatrix();
		// handle.setRenderPos(handle.getTextOffset("default",
		// text).toVertex3());
		
		// handle.popMatrix();
		handle.setRenderMode(UIHandle.MODE_TEXTURE_ONLY);
		handle.drawText("default", text);
	}

	@Override
	public void keyPressed(int key, int action) {
		if (action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT) {
			switch (key) {
			case GLFW.GLFW_KEY_ENTER:
				insertString("\n");
			case GLFW.GLFW_KEY_LEFT:
				caretPos -= caretPos > 0 ? 1 : 0;
				break;
			case GLFW.GLFW_KEY_RIGHT:
				caretPos += caretPos < text.length() ? 1 : 0;
				break;
			case GLFW.GLFW_KEY_BACKSPACE:
				if (text.length() > 0) {
					deleteChar(caretPos);
					if (caretPos > 0) {
						caretPos--;
					}
				}
				break;
			case GLFW.GLFW_KEY_DELETE:
				if (text.length() > 0 && caretPos < text.length()) {
					deleteChar(caretPos + 1);
				}
				break;
			}
		}
	}

	@Override
	public void textTyped(char c) {
		// text += c;
		insertString(String.valueOf(c));
		caretPos++;
	}

	private void deleteChar(int pos) {
		if (caretPos > 0) {
			if (caretPos == text.length()) {
				text = text.substring(0, caretPos - 1);
			} else {
				text = text.substring(0, caretPos - 1) + text.substring(caretPos);
			}
		}
	}

	private void insertString(String s) {
		text = text.substring(0, caretPos) + s + text.substring(caretPos);
	}

}
