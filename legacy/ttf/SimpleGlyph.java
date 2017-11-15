package com.glutilities.text.ttf;

import com.glutilities.util.Rectangle;

public class SimpleGlyph extends Glyph {

	private int[] endPtsOfContours;
	private int instructionLength;
	private int[] instructions;
	private int[] flags;
	// defined in EmUnits
	private int[] xCoordinates;
	private int[] yCoordinates;

	private SimpleGlyph(int numberOfContours, Rectangle boundary, int[] endPtsOfContours, int instructionLength, int[] instructions, int[] flags, int[] xCoordinates, int[] yCoordinates) {
		super(numberOfContours, boundary);
		this.endPtsOfContours = endPtsOfContours;
		this.instructionLength = instructionLength;
		this.instructions = instructions;
		this.flags = flags;
		this.xCoordinates = xCoordinates;
		this.yCoordinates = yCoordinates;
	}

	/**
	 * @return the endPtsOfContours
	 */
	public int[] getEndPtsOfContours() {
		return endPtsOfContours;
	}

	/**
	 * @return the instructionLength
	 */
	public int getInstructionLength() {
		return instructionLength;
	}

	/**
	 * @return the instructions
	 */
	public int[] getInstructions() {
		return instructions;
	}

	/**
	 * @return the flags
	 */
	public int[] getFlags() {
		return flags;
	}

	/**
	 * @return the xCoordinates
	 */
	public int[] getXCoordinates() {
		return xCoordinates;
	}

	/**
	 * @return the yCoordinates
	 */
	public int[] getYCoordinates() {
		return yCoordinates;
	}

}
