package com.glutilities.test;

import java.io.File;
import java.io.IOException;

import com.glutilities.text.TTFFile;
import com.glutilities.text.ttf.DataType;

public class Test {

	// https://www.microsoft.com/typography/otspec/glyf.htm
	
	/*
	 * Features: JavaScript rendering TTF Font support Texture Loading Dynamic
	 * Shaders Maybe VBOs AWT-Like rendering system Input handling (Keyboard,
	 * Mouse, Controller)
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TTFPrimitive test = new TTFPrimitive(TTFPrimitive.Type.ULONG,
		// 0x676C7966);
		// System.out.println(test.getStringValue());
		try {
			TTFFile file = new TTFFile(new File("C:/Users/Hanavan/Desktop/times.ttf"));
			System.out.println(file.readAsDouble(DataType.FIXED));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// TTFFontManager.loadFont("C:/Users/Hanavan/Desktop/times.ttf");
		// TTFFont font = TTFFontManager.getFont(null);
		// OffsetTable ot = (OffsetTable) font.getTable(OffsetTable.class);
		// System.out.println(ot.getSfntVersion());

	}

}
