package com.glutilities.text.texture;

import com.glutilities.util.Vertex4f;

public class TextFormat {

	/**
	 * The scale of the texture. This number is used to calculate the texture
	 * size by this equation: textureSize = 2 ^ textureScale.
	 */
	public int textureScale;
	public Vertex4f bgColor;
	public Vertex4f textColor;
	public float leading;
	public float kerning;

}
