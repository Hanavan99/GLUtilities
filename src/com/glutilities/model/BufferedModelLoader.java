package com.glutilities.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.opengl.GL11;

import com.glutilities.resource.ResourceLoader;

public class BufferedModelLoader extends ResourceLoader<BufferedModel, String, File> {

	public BufferedModel load(File src, String key) {
		try {
			Scanner filein = new Scanner(src);
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
				case "vt":
					texcs.add(Float.parseFloat(data[1]));
					texcs.add(Float.parseFloat(data[2]));
					texcs.add(Float.parseFloat(data[3]));
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
			int length = drawi.size();
			int vertsPerShape = 3;
			float[] vertices = new float[length];
			float[] colors = new float[length];
			float[] normals = new float[length];
			float[] texcoords = new float[length];
			for (int i = 0; i < length; i += 3) {
				int vi = drawi.get(i) * vertsPerShape;
				int ti = drawi.get(i + 1) * 1;
				int ni = drawi.get(i + 2) * vertsPerShape;
				vertices[i] = verts.get(vi);
				vertices[i + 1] = verts.get(vi + 1);
				vertices[i + 2] = verts.get(vi + 2);
				colors[i] = 1;
				colors[i + 1] = 1;
				colors[i + 2] = 1;
				normals[i] = norms.get(ni);
				normals[i + 1] = norms.get(ni + 1);
				normals[i + 2] = norms.get(ni + 2);
				texcoords[i] = texcs.get(ti);
				texcoords[i + 1] = texcs.get(ti + 1);
				texcoords[i + 2] = texcs.get(ti + 2);
			}
			return new BufferedModel(key, vertices, colors, normals, texcoords, null, GL11.GL_TRIANGLES);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean keyReferencesResource(BufferedModel resource, String key) {
		return resource.getModelName().equals(key);
	}

	@Override
	public String getKeyFromResource(BufferedModel resource) {
		return resource.getModelName();
	}

}
