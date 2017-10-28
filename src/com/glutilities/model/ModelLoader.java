package com.glutilities.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.glutilities.resource.ResourceLoader;

public class ModelLoader extends ResourceLoader<Model, String> {

	public Model load(File file, String key) {
		try {
			Scanner filein = new Scanner(file);
			List<Float> verts = new ArrayList<Float>();
			List<Float> norms = new ArrayList<Float>();
			List<Float> texcs = new ArrayList<Float>();
			List<Integer> drawi = new ArrayList<Integer>();
			while (filein.hasNextLine()) {
				String line = filein.nextLine();
				line = line.trim().replace("  ", " ");
				int spi = line.indexOf(' ');
				String ident;
				if (spi == -1) {
					ident = line;
				} else {
					ident = line.substring(0, spi);
				}
				String[] data = line.split(" ");
				switch (ident) {
				case "v":
					verts.add(Float.parseFloat(data[1]));
					verts.add(Float.parseFloat(data[2]));
					verts.add(Float.parseFloat(data[3]));
					break;
				case "vn":
					norms.add(Float.parseFloat(data[1]));
					norms.add(Float.parseFloat(data[2]));
					norms.add(Float.parseFloat(data[3]));
					break;
				case "f":
					for (int i = 0; i < 3; i++) {
						String[] subData = data[i + 1].split("/");
						if (!subData[0].equals("")) {
							drawi.add(Integer.parseInt(subData[0]) - 1);
						} else {
							drawi.add(-1);
						}
						if (!subData[1].equals("")) {
							drawi.add(Integer.parseInt(subData[1]) - 1);
						} else {
							drawi.add(-1);
						}
						if (!subData[2].equals("")) {
							drawi.add(Integer.parseInt(subData[2]) - 1);
						} else {
							drawi.add(-1);
						}
					}
					break;
				default:
					break;
				}
			}
			filein.close();
			return new Model(key, fromFloat(verts.toArray(new Float[0])), fromFloat(norms.toArray(new Float[0])), fromFloat(texcs.toArray(new Float[0])), fromInteger(drawi.toArray(new Integer[0])), null);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private float[] fromFloat(Float[] f) {
		float[] newArray = new float[f.length];
		for (int i = 0; i < newArray.length; i++) {
			newArray[i] = f[i];
		}
		return newArray;
	}

	private int[] fromInteger(Integer[] f) {
		int[] newArray = new int[f.length];
		for (int i = 0; i < newArray.length; i++) {
			newArray[i] = f[i];
		}
		return newArray;
	}

	@Override
	public boolean keyReferencesResource(Model resource, String key) {
		return resource.getModelName().equals(key);
	}

}
