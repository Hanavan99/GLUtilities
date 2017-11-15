package com.glutilities.text;

import com.glutilities.buffer.VBO;

public class NewGlyph {

	private char character;
	private float width;
	private float height;
	private VBO[] shapes;
	public NewGlyph(char character, float width, float height, VBO[] shapes) {
		super();
		this.character = character;
		this.width = width;
		this.height = height;
		this.shapes = shapes;
	}
	
	public void draw() {
		
		for (VBO vbo : shapes) {
			vbo.draw();
		}
		
	}
	
	public float getCharacter() {
		return character;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
}
