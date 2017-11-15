package com.glutilities.text.ttf;

public class HeaderTable extends Table {

	private int majorVersion;
	private int minorVersion;
	private double fontRevision;
	private long checkSumAdjustment;
	private long magicNumber;
	private int flags;
	private int unitsPerEm;
	private long created;
	private long modified;
	private int xMin;
	private int yMin;
	private int xMax;
	private int yMax;
	private int macStyle;
	private int lowestRecPPEM;
	private int fontDirectionHint;
	private int indexToLocFormat;
	private int glyphDataFormat;

	public HeaderTable(int majorVersion, int minorVersion, double fontRevision, long checkSumAdjustment, long magicNumber, int flags, int unitsPerEm, long created, long modified, int xMin, int yMin, int xMax, int yMax, int macStyle,
			int lowestRecPPEM, int fontDirectionHint, int indexToLocFormat, int glyphDataFormat) {
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
		this.fontRevision = fontRevision;
		this.checkSumAdjustment = checkSumAdjustment;
		this.magicNumber = magicNumber;
		this.flags = flags;
		this.unitsPerEm = unitsPerEm;
		this.created = created;
		this.modified = modified;
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
		this.macStyle = macStyle;
		this.lowestRecPPEM = lowestRecPPEM;
		this.fontDirectionHint = fontDirectionHint;
		this.indexToLocFormat = indexToLocFormat;
		this.glyphDataFormat = glyphDataFormat;
	}

	/**
	 * @return the majorVersion
	 */
	public int getMajorVersion() {
		return majorVersion;
	}

	/**
	 * @return the minorVersion
	 */
	public int getMinorVersion() {
		return minorVersion;
	}

	/**
	 * @return the fontRevision
	 */
	public double getFontRevision() {
		return fontRevision;
	}

	/**
	 * @return the checkSumAdjustment
	 */
	public long getCheckSumAdjustment() {
		return checkSumAdjustment;
	}

	/**
	 * @return the magicNumber
	 */
	public long getMagicNumber() {
		return magicNumber;
	}

	/**
	 * @return the flags
	 */
	public int getFlags() {
		return flags;
	}

	/**
	 * @return the unitsPerEm
	 */
	public int getUnitsPerEm() {
		return unitsPerEm;
	}

	/**
	 * @return the created
	 */
	public long getCreated() {
		return created;
	}

	/**
	 * @return the modified
	 */
	public long getModified() {
		return modified;
	}

	/**
	 * @return the xMin
	 */
	public int getxMin() {
		return xMin;
	}

	/**
	 * @return the yMin
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * @return the xMax
	 */
	public int getxMax() {
		return xMax;
	}

	/**
	 * @return the yMax
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * @return the macStyle
	 */
	public int getMacStyle() {
		return macStyle;
	}

	/**
	 * @return the lowestRecPPEM
	 */
	public int getLowestRecPPEM() {
		return lowestRecPPEM;
	}

	/**
	 * @return the fontDirectionHint
	 */
	public int getFontDirectionHint() {
		return fontDirectionHint;
	}

	/**
	 * @return the indexToLocFormat
	 */
	public int getIndexToLocFormat() {
		return indexToLocFormat;
	}

	/**
	 * @return the glyphDataFormat
	 */
	public int getGlyphDataFormat() {
		return glyphDataFormat;
	}

}
