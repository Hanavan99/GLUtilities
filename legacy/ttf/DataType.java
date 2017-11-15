package com.glutilities.text.ttf;

public enum DataType {

	/**
	 * Represents an 8-bit unsigned integer.
	 */
	BYTE(8, 0, false),
	/**
	 * Represents an 8-bit signed integer.
	 */
	CHAR(8, 0, true),
	/**
	 * Represents a 16-bit unsigned integer.
	 */
	USHORT(16, 0, false),
	/**
	 * Represents a 16-bit signed integer.
	 */
	SHORT(16, 0, true),
	/**
	 * Represents a 32-bit unsigned integer.
	 */
	ULONG(32, 0, false),
	/**
	 * Represents a 32-bit signed integer.
	 */
	LONG(32, 0, true),
	/**
	 * Represents a 32-bit signed fixed-point number. (16.16)
	 */
	FIXED(16, 16, false),
	/**
	 * Represents a 16-bit signed integer that describes a quantity in FUnits.
	 */
	FWORD(16, 0, true),
	/**
	 * Represents a 16-bit unsigned integer that describes a quantity in FUnits.
	 */
	UFWORD(16, 0, false),
	/**
	 * Represents a 16-bit signed fixed number with the low 14 bits of fraction.
	 * (2.14)
	 */
	F2DOT14(2, 14, true);

	/**
	 * Represents the smallest measurable distance in the em space.
	 */
	public static final double FUNIT = Double.MIN_NORMAL;

	private int integerBits;
	private int fractionBits;
	private boolean signed;

	private DataType(int integerBits, int fractionBits, boolean signed) {
		this.integerBits = integerBits;
		this.fractionBits = fractionBits;
		this.signed = signed;
	}
	
	public int getBytes() {
		return getTotalBits() / 8;
	}

	public int getTotalBits() {
		return integerBits + fractionBits;
	}

	public int getIntegerBits() {
		return integerBits;
	}

	public int getFractionBits() {
		return fractionBits;
	}

	public boolean signed() {
		return signed;
	}

}