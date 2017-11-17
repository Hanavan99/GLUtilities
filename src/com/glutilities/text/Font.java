package com.glutilities.text;

import com.glutilities.util.Vertex4f;

public class Font {

	public static final int PLAIN = java.awt.Font.PLAIN;
	public static final int BOLD = java.awt.Font.BOLD;
	public static final int ITALIC = java.awt.Font.ITALIC;

	public static final int DEFAULT_TEXTURE_SCALE = 6;

	private String name;
	private int style;
	private Vertex4f textColor;
	private Vertex4f backColor;
	private Vertex4f outlineColor;
	private float outline;
	private float leading;
	private float kerning;
	private int textureScale;

	public Font(String name, int style) {
		this(name, style, Vertex4f.WHITE);
	}

	public Font(String name, int style, Vertex4f textColor) {
		this(name, style, textColor, Vertex4f.TRANSPARENT, Vertex4f.BLACK, 0, 0, 0, DEFAULT_TEXTURE_SCALE);
	}

	public Font(String name, int style, Vertex4f textColor, Vertex4f backColor) {
		this(name, style, textColor, backColor, Vertex4f.TRANSPARENT, 0, 0, 0, DEFAULT_TEXTURE_SCALE);
	}

	public Font(String name, int style, Vertex4f textColor, Vertex4f outlineColor, float outline) {
		this(name, style, textColor, Vertex4f.TRANSPARENT, outlineColor, outline, 0, 0, DEFAULT_TEXTURE_SCALE);
	}

	public Font(String name, int style, Vertex4f textColor, Vertex4f backColor, Vertex4f outlineColor, float outline, float leading, float kerning, int textureScale) {
		this.name = name;
		this.style = style;
		this.textColor = textColor;
		this.backColor = backColor;
		this.outlineColor = outlineColor;
		this.outline = outline;
		this.leading = leading;
		this.kerning = kerning;
		this.textureScale = textureScale;
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

	/**
	 * @return the textColor
	 */
	public Vertex4f getTextColor() {
		return textColor;
	}

	/**
	 * @param textColor the textColor to set
	 */
	public void setTextColor(Vertex4f textColor) {
		this.textColor = textColor;
	}

	/**
	 * @return the backColor
	 */
	public Vertex4f getBackColor() {
		return backColor;
	}

	/**
	 * @param backColor the backColor to set
	 */
	public void setBackColor(Vertex4f backColor) {
		this.backColor = backColor;
	}

	/**
	 * @return the outlineColor
	 */
	public Vertex4f getOutlineColor() {
		return outlineColor;
	}

	/**
	 * @param outlineColor the outlineColor to set
	 */
	public void setOutlineColor(Vertex4f outlineColor) {
		this.outlineColor = outlineColor;
	}

	/**
	 * @return the outline
	 */
	public float getOutline() {
		return outline;
	}

	/**
	 * @param outline the outline to set
	 */
	public void setOutline(float outline) {
		this.outline = outline;
	}

	/**
	 * @return the leading
	 */
	public float getLeading() {
		return leading;
	}

	/**
	 * @param leading the leading to set
	 */
	public void setLeading(float leading) {
		this.leading = leading;
	}

	/**
	 * @return the kerning
	 */
	public float getKerning() {
		return kerning;
	}

	/**
	 * @param kerning the kerning to set
	 */
	public void setKerning(float kerning) {
		this.kerning = kerning;
	}

	/**
	 * @return the textureScale
	 */
	public int getTextureScale() {
		return textureScale;
	}

	/**
	 * @param textureScale the textureScale to set
	 */
	public void setTextureScale(int textureScale) {
		this.textureScale = textureScale;
	}

}
