package com.glutilities.text.texture;

public class Font {

	public static final int PLAIN = java.awt.Font.PLAIN;
	public static final int BOLD = java.awt.Font.BOLD;
	public static final int ITALIC = java.awt.Font.ITALIC;

	private String name;
	private int style;

	public Font(String name, int style) {
		this.name = name;
		this.style = style;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the style
	 */
	public int getStyle() {
		return style;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(int style) {
		this.style = style;
	}

}
