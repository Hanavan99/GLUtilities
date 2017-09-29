package com.glutilities.text.ttf;

public class OffsetTableEntry {

	private String tag;
	private long checkSum;
	private long offset;
	private long length;

	public OffsetTableEntry(String tag, long checkSum, long offset, long length) {
		this.tag = tag;
		this.checkSum = checkSum;
		this.offset = offset;
		this.length = length;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @return the checkSum
	 */
	public long getCheckSum() {
		return checkSum;
	}

	/**
	 * @return the offset
	 */
	public long getOffset() {
		return offset;
	}

	/**
	 * @return the length
	 */
	public long getLength() {
		return length;
	}

}
