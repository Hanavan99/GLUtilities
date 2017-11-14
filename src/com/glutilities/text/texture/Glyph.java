package com.glutilities.text.texture;

import com.glutilities.util.Rectangle;

public class Glyph {

	private char character;
	private Rectangle texCoords;
	private float width;

	public Glyph(char character, Rectangle texCoords, float width) {
		this.character = character;
		this.texCoords = texCoords;
		this.width = width;
	}

	public char getCharacter() {
		return character;
	}

	public Rectangle getTexCoords() {
		return texCoords;
	}
	
	public float getWidth() {
		return width;
	}

}
