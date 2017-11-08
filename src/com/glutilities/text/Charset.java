package com.glutilities.text;

public class Charset {

	private String name;
	private NewGlyph[] glyphs;

	public Charset(String name, NewGlyph[] glyphs) {
		this.name = name;
		this.glyphs = glyphs;
	}

	public String getName() {
		return name;
	}

	public NewGlyph[] getNewGlyphs() {
		return glyphs;
	}

	public NewGlyph getGlyphForChar(char c) {
		for (NewGlyph glyph : glyphs) {
			if (glyph.getCharacter() == c) {
				return glyph;
			}
		}
		return null;
	}

}
