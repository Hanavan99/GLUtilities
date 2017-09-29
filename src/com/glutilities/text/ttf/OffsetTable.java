package com.glutilities.text.ttf;

public class OffsetTable extends Table {

	private double sfntVersion;
	private int numTables;
	private int searchRange;
	private int entrySelector;
	private int rangeShift;

	private OffsetTableEntry[] entries;

	public OffsetTable(double sfntVersion, int numTables, int searchRange, int entrySelector, int rangeShift, OffsetTableEntry[] entries) {
		this.sfntVersion = sfntVersion;
		this.numTables = numTables;
		this.searchRange = searchRange;
		this.entrySelector = entrySelector;
		this.rangeShift = rangeShift;
		this.entries = entries;
	}

	/**
	 * @return the sfntVersion
	 */
	public double getSfntVersion() {
		return sfntVersion;
	}

	/**
	 * @return the numTables
	 */
	public int getNumTables() {
		return numTables;
	}

	/**
	 * @return the searchRange
	 */
	public int getSearchRange() {
		return searchRange;
	}

	/**
	 * @return the entrySelector
	 */
	public int getEntrySelector() {
		return entrySelector;
	}

	/**
	 * @return the rangeShift
	 */
	public int getRangeShift() {
		return rangeShift;
	}

	/**
	 * @return the entries
	 */
	public OffsetTableEntry[] getEntries() {
		return entries;
	}

}
