package com.glutilities.text.ttf;
//
public class TTFPrimitive {
//
//	
//
//	private final Type dataType;
//	private byte[] rawData;
//
//	public TTFPrimitive(Type dataType, byte[] rawData) {
//		this.dataType = dataType;
//		this.rawData = rawData;
//		validate();
//	}
//
//	public TTFPrimitive(Type dataType, int value) {
//		this.dataType = dataType;
//		this.rawData = new byte[dataType.getTotalBits() / 8];
//		switch (dataType.getTotalBits()) {
//		case 8:
//			rawData[0] = (byte) value;
//			break;
//		case 16:
//			rawData[0] = (byte) (value >> 8);
//			rawData[1] = (byte) value;
//			break;
//		case 32:
//			rawData[0] = (byte) (value >> 24);
//			rawData[1] = (byte) (value >> 16);
//			rawData[2] = (byte) (value >> 8);
//			rawData[3] = (byte) value;
//			break;
//		}
//	}
//
//	private void validate() {
//		if (rawData.length != dataType.getTotalBits() / 8) {
//			throw new IllegalStateException("TTFPrimitive has incorrect size of byte array for type " + dataType.name());
//		}
//	}
//
//	/**
//	 * Gets the value of the raw data and converts it into a double.
//	 *
//	 * @return The converted value as a double
//	 */
//	public double getDoubleValue() {
//		
//	}
//	
//	public int getIntValue() {
//		return (int) getDoubleValue();
//	}
//	
//	public long getLongValue() {
//		return (long) getDoubleValue();
//	}
//	
//	public String getStringValue() {
//		return new String(rawData);
//	}
//
//	private int maskAndShift(byte data, int mask) {
//		return data & mask >> (int) (Math.log(mask) / Math.log(2));
//	}
//
}
