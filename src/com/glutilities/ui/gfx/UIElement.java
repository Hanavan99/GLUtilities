package com.glutilities.ui.gfx;

import com.glutilities.util.Vertex3f;

public abstract class UIElement {

	private String font;
	private Vertex3f position;
	private Vertex3f size;

	public UIElement() {
		position = new Vertex3f(0, 0, 0);
		size = new Vertex3f(1, 1, 0);
	}
	
	public UIElement(Vertex3f position, Vertex3f size) {
		this.position = position;
		this.size = size;
	}
	
	public void setup(UIHandle handle) {
		// does nothing by default
	}

	public abstract void render(UIHandle handle);
	
	public void mouseEntered() {
		
	}
	
	public void mouseLeft() {
		
	}
	
	public void selected() {
		
	}
	
	public void deselected() {
		
	}
	
	public void keyPressed(int key, int action) {
		
	}

	public Vertex3f getPosition() {
		return position;
	}
	
	
	public Vertex3f getSize() {
		return size;
	}

}
