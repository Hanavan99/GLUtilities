package com.glutilities.text.ttf;

public class MaximumProfileTable extends Table {

	private double version;
	private int numGlyphs;
	private int maxPoints;
	private int maxContours;
	private int maxCompositePoints;
	private int maxCompositeContours;
	private int maxZones;
	private int maxTwilightPoints;
	private int maxStorage;
	private int maxFunctionDefs;
	private int maxInstructionDefs;
	private int maxStackElements;
	private int maxSizeOfInstructions;
	private int maxComponentElements;
	private int maxComponentDepth;

	/**
	 * Barebones constructor for this table
	 * 
	 * @param version The version of the table
	 * @param numGlyphs The number of glyphs in the file
	 */
	public MaximumProfileTable(double version, int numGlyphs) {
		this.version = version;
		this.numGlyphs = numGlyphs;
	}

	/**
	 * @return the version
	 */
	public double getVersion() {
		return version;
	}

	/**
	 * @return the numGlyphs
	 */
	public int getNumGlyphs() {
		return numGlyphs;
	}

	/**
	 * @return the maxPoints
	 */
	public int getMaxPoints() {
		return maxPoints;
	}

	/**
	 * @return the maxContours
	 */
	public int getMaxContours() {
		return maxContours;
	}

	/**
	 * @return the maxCompositePoints
	 */
	public int getMaxCompositePoints() {
		return maxCompositePoints;
	}

	/**
	 * @return the maxCompositeContours
	 */
	public int getMaxCompositeContours() {
		return maxCompositeContours;
	}

	/**
	 * @return the maxZones
	 */
	public int getMaxZones() {
		return maxZones;
	}

	/**
	 * @return the maxTwilightPoints
	 */
	public int getMaxTwilightPoints() {
		return maxTwilightPoints;
	}

	/**
	 * @return the maxStorage
	 */
	public int getMaxStorage() {
		return maxStorage;
	}

	/**
	 * @return the maxFunctionDefs
	 */
	public int getMaxFunctionDefs() {
		return maxFunctionDefs;
	}

	/**
	 * @return the maxInstructionDefs
	 */
	public int getMaxInstructionDefs() {
		return maxInstructionDefs;
	}

	/**
	 * @return the maxStackElements
	 */
	public int getMaxStackElements() {
		return maxStackElements;
	}

	/**
	 * @return the maxSizeOfInstructions
	 */
	public int getMaxSizeOfInstructions() {
		return maxSizeOfInstructions;
	}

	/**
	 * @return the maxComponentElements
	 */
	public int getMaxComponentElements() {
		return maxComponentElements;
	}

	/**
	 * @return the maxComponentDepth
	 */
	public int getMaxComponentDepth() {
		return maxComponentDepth;
	}

}
