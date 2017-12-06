package com.glutilities.model;

public class AttributeArray {

	private final String name;
	private final int index;
	private final int size;
	private Object[] data;

	public AttributeArray(String name, Object[] data, int size) {
		this.name = name;
		this.index = -1;
		this.size = size;
		this.data = data;
	}

	public AttributeArray(int index, Object[] data, int size) {
		this.name = null;
		this.index = index;
		this.size = size;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public int getIndex() {
		return index;
	}

	public int getSize() {
		return size;
	}

	public Object[] getData() {
		return data;
	}

}
