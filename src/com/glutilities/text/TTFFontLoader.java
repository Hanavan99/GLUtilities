package com.glutilities.text;

import com.glutilities.text.ttf.DataType;
import com.glutilities.text.ttf.GlyphTable;
import com.glutilities.text.ttf.HeaderTable;
import com.glutilities.text.ttf.IndexLocationTable;
import com.glutilities.text.ttf.MaximumProfileTable;
import com.glutilities.text.ttf.OffsetTable;
import com.glutilities.text.ttf.OffsetTableEntry;

public class TTFFontLoader {

	private TTFFile file;
	OffsetTableEntry[] entries;
	private OffsetTable offsetTable;
	private HeaderTable headerTable;
	private MaximumProfileTable maximumProfileTable;
	private IndexLocationTable indexLocationTable;
	private GlyphTable glyphTable;
	private TTFFontDescriptor font = new TTFFontDescriptor();

	public TTFFontLoader(TTFFile file) {
		this.file = file;
	}

	public TTFFontDescriptor load() {
		readFileHeader();
		buildTables();
		return font;
	}

	private void readFileHeader() {
		double sfntVersion = file.readAsDouble(DataType.FIXED);
		int numTables = file.readAsInt(DataType.USHORT);
		int searchRange = file.readAsInt(DataType.USHORT);
		int entrySelector = file.readAsInt(DataType.USHORT);
		int rangeShift = file.readAsInt(DataType.USHORT);
		entries = new OffsetTableEntry[numTables];
		for (int i = 0; i < entries.length; i++) {
			String tag = file.readAsString(4);
			
			long checkSum = file.readAsLong(DataType.ULONG);
			long offset = file.readAsLong(DataType.ULONG);
			System.out.println(tag + ": Offset - " + Long.toHexString(offset));
			long length = file.readAsLong(DataType.ULONG);
			entries[i] = new OffsetTableEntry(tag, checkSum, offset, length);
		}
		offsetTable = new OffsetTable(sfntVersion, numTables, searchRange, entrySelector, rangeShift, entries);
		font.add(offsetTable);
	}

	private void buildTables() {
		// for (OffsetTableEntry entry : offsetTable.getEntries()) {
		// file.setPosition((int) entry.getOffset());
		// switch (entry.getTag()) {
		// case "glyf":
		// readGlyphTable();
		// break;
		// case "maxp":
		//
		// }
		// }
		readHeaderTable();
		readMaximumProfileTable();
		readIndexLocationTable();
		readGlyphTable();
		font.add(headerTable);
		font.add(maximumProfileTable);
		font.add(indexLocationTable);
		font.add(glyphTable);
	}

	private void readHeaderTable() {
		file.setPosition((int) offsetTable.getEntryByTag("head").getOffset());
		int majorVersion = file.readAsInt(DataType.USHORT);
		int minorVersion = file.readAsInt(DataType.USHORT);
		double fontRevision = file.readAsDouble(DataType.FIXED);
		long checkSumAdjustment = file.readAsLong(DataType.ULONG);
		long magicNumber = file.readAsLong(DataType.ULONG);
		int flags = file.readAsInt(DataType.USHORT);
		int unitsPerEm = file.readAsInt(DataType.USHORT);
		long created = file.readAsLong(DataType.ULONG);
		long modified = file.readAsLong(DataType.ULONG);
		int xMin = file.readAsInt(DataType.SHORT);
		int yMin = file.readAsInt(DataType.SHORT);
		int xMax = file.readAsInt(DataType.SHORT);
		int yMax = file.readAsInt(DataType.SHORT);
		int macStyle = file.readAsInt(DataType.USHORT);
		int lowestRecPPEM = file.readAsInt(DataType.USHORT);
		int fontDirectionHint = file.readAsInt(DataType.SHORT);
		int indexToLocFormat = file.readAsInt(DataType.SHORT);
		int glyphDataFormat = file.readAsInt(DataType.SHORT);

		headerTable = new HeaderTable(majorVersion, minorVersion, fontRevision, checkSumAdjustment, magicNumber, flags, unitsPerEm, created, modified, xMin, yMin, xMax, yMax, macStyle, lowestRecPPEM, fontDirectionHint, indexToLocFormat,
				glyphDataFormat);
	}

	private void readMaximumProfileTable() {

	}

	private void readIndexLocationTable() {

	}

	private void readGlyphTable() {
		int baseOffset = (int) offsetTable.getEntryByTag("glyf").getOffset();
		int[] offsets = indexLocationTable.getOffsets();
		for (int offset : offsets) {
			file.setPosition(baseOffset + offset);
			int numberOfContours = file.readAsInt(DataType.SHORT);
			int xMin = file.readAsInt(DataType.SHORT);
			int xMax = file.readAsInt(DataType.SHORT);
			int yMin = file.readAsInt(DataType.SHORT);
			int yMax = file.readAsInt(DataType.SHORT);
			System.out.println(numberOfContours);
		}
		
		
	}

}
