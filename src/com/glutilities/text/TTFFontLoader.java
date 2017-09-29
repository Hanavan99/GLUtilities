package com.glutilities.text;

import com.glutilities.text.ttf.DataType;
import com.glutilities.text.ttf.OffsetTable;
import com.glutilities.text.ttf.OffsetTableEntry;

public class TTFFontLoader {

	private TTFFile file;
	OffsetTableEntry[] entries;
	private OffsetTable offsetTable;
	private TTFFont font = new TTFFont();

	public TTFFontLoader(TTFFile file) {
		this.file = file;
	}

	public TTFFont load() {
		readFileHeader();
		buildTableIndex();
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
			long length = file.readAsLong(DataType.ULONG);
			entries[i] = new OffsetTableEntry(tag, checkSum, offset, length);
		}
		offsetTable = new OffsetTable(sfntVersion, numTables, searchRange, entrySelector, rangeShift, entries);
		font.add(offsetTable);
	}

	private void buildTableIndex() {
		for (OffsetTableEntry entry : offsetTable.getEntries()) {
			file.setPosition((int) entry.getOffset());
			switch (entry.getTag()) {
			case "glyf":
				readGlyphs();
				break;
			}
		}
	}

	private void readGlyphs() {
		
	}

}
