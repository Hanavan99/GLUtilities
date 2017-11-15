package com.glutilities.text.ttf;

public class IndexLocationTable extends Table {

	private int[] offsets;

	private IndexLocationTable(int[] offsets) {
		this.offsets = offsets;
	}

	/**
	 * @return the offsets
	 */
	public int[] getOffsets() {
		return offsets;
	}

}
