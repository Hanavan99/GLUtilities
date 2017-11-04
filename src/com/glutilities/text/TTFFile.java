package com.glutilities.text;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.glutilities.text.ttf.DataType;

public class TTFFile {

	private int fileSize;
	private ByteBuffer buffer;

	public TTFFile(File file) throws IOException {
		InputStream fileIn = new FileInputStream(file);
		fileSize = (int) file.length();
		byte[] data = new byte[fileSize];
		fileIn.read(data);
		buffer = ByteBuffer.wrap(data);
		fileIn.close();
	}

	/*
	 * double value = 0; double signed = data[0] >> 7; switch (dataType) { case
	 * BYTE: value = data[0] + (data[0] >> 7 * 128); break; case CHAR: break;
	 * case F2DOT14: value = (data[0] >> 7 == 1 ? data[0] >> 6 & 1 : data[0] >>
	 * 6 & 2 * -1 + 1) + maskAndShift(data[0], 0x40) + (((maskAndShift(data[0],
	 * 0x3F) << 8) | (data[1])) / 16384.0); break; case FIXED: value = (data[0]
	 * << 24) + (data[1] << 16) + ((data[2] << 8 + data[3]) / (double) 0xFFFF);
	 * break; case FWORD: break; case LONG: value = data[0] << 24 + data[1] <<
	 * 16 + data[2] << 8 + data[3]; break; case SHORT: break; case UFWORD:
	 * break; case ULONG: break; case USHORT: value = data[0] << 8 + data[1];
	 * break; default: break; } return value;
	 */
	public void setPosition(int pos) {
		buffer.position(pos);
	}

	public String readAsString(int bytes) {
		byte[] data = new byte[bytes];
		buffer.get(data);
		return new String(data);
	}

	// public double readAsF2DOT14() {
	// byte[] data = new byte[DataType.F2DOT14.getBytes()];
	// buffer.get(data);
	// return (data[0] >> 7 == 1 ? data[0] >> 6 & 1 : data[0] >> 6 & 2 * -1 + 1)
	// + (data[0] & 0x40 >> 4) + (((data[0] & 0x3F << 8) | (data[1])) /
	// 16384.0);
	// }

	// damn signed numbers java
	public double readAsDouble(DataType type) {
		double value = 0;
		int[] data = new int[type.getBytes()];
		readBuffer(data);
		// buffer.get(data);
		// for (byte b : data) {
		// System.out.print(Integer.toHexString((int) b) + " ");
		// }
		// System.out.println();
		if (type.signed()) {
			switch (type) {
			case CHAR:
				value = buffer.get();
				break;
			// case F2DOT14:
			// byte[] data = new byte[2];
			// buffer.get(data);
			// int intPart = (data[0] >> 6) *
			// value =
			// break;
			case FIXED:
				value = (data[0] << 8) + data[1] + ((data[2] << 8 + data[3]) / (double) 0xFFFF);
				break;
			case FWORD:
				value = buffer.getChar();
				break;
			case SHORT:
				value = buffer.getChar();
				break;
			case LONG:
				value = buffer.getInt();
				break;
			default:
				break;
			}
		} else {
			double signed = data[0] >> 7;
			switch (type) {
			case BYTE:
				value = data[0] + (data[0] >> 7 * 128);
				break;
			case UFWORD:
				break;
			case ULONG:
				value = (data[0] << 24) + (data[1] << 16) + (data[2] << 8) + data[3];
				break;
			case USHORT:
				value = (data[0] << 8) + data[1];
				break;
			default:
				break;
			}
		}

		return value;
	}

	public long readAsLong(DataType type) {
		// TODO read natively instead of casting
		return (long) readAsDouble(type);
	}

	public int readAsInt(DataType type) {
		return (int) readAsDouble(type);
	}

	/**
	 * Dumps the data stored in this file as to clear up memory.
	 */
	public void dumpData() {
		buffer.clear();
		buffer = null;
	}

	private int maskAndShift(byte data, int mask) {
		return data & mask >> (int) (Math.log(mask) / Math.log(2));
	}

	private void readBuffer(int[] buff) {
		for (int i = 0; i < buff.length; i++) {
			buff[i] = (int) buffer.get() | 0;
		}
	}

}
