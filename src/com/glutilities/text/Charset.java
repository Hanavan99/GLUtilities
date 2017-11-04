package com.glutilities.text;

public class Charset {

	private String name;
	private Glyph[] glyphs;

	public Charset(String name, Glyph[] glyphs) {
		this.name = name;
		this.glyphs = glyphs;
	}

	public String getName() {
		return name;
	}

	public Glyph[] getGlyphs() {
		return glyphs;
	}

	public Glyph getGlyphForChar(char c) {
		for (Glyph glyph : glyphs) {
			if (glyph.getCharacter() == c) {
				return glyph;
			}
		}
		return null;
	}

}
