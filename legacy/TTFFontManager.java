package com.glutilities.text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TTFFontManager {

	private static List<TTFFontDescriptor> loadedFonts = new ArrayList<TTFFontDescriptor>();

	public static void loadFont(String name) {
		try {
			load(new File(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static TTFFontDescriptor getFont(String name) {
		return loadedFonts.get(0);
	}

	private static void load(File file) throws IOException {
		//List<Table> tables = new ArrayList<Table>();
		
		TTFFontLoader loader = new TTFFontLoader(new TTFFile(file));

		loadedFonts.add(loader.load());
	}

}
