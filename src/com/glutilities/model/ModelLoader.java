package com.glutilities.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.opengl.GL11;

import com.glutilities.buffer.VBO;
import com.glutilities.resource.ResourceLoader;
import com.glutilities.util.Vertex4f;

public class ModelLoader extends ResourceLoader<Model, String, File> {

	public Model load(File src, String key) {
		try {
			Scanner filein = new Scanner(src);
			List<Float> verts = new ArrayList<Float>();
			List<Float> norms = new ArrayList<Float>();
			List<Float> texcs = new ArrayList<Float>();
			List<Integer> drawi = new ArrayList<Integer>();
			List<ModelMaterial> materials = new ArrayList<ModelMaterial>();
			List<ModelGroup> groups = new ArrayList<ModelGroup>();
			String activeMaterial = "";
			String groupName = null;
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
				case "mtllib":
					materials.addAll(loadMaterials(new File(src.getParentFile(), data[1])));
					break;
				case "usemtl":
					activeMaterial = data[1];
					break;
				case "g":
					if (groupName != null) {
						groups.add(buildGroup(groupName, getMaterialByName(activeMaterial, materials), verts, norms, texcs, drawi));
						drawi.clear();
					}
					groupName = data[1];
					break;
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

			return new Model(key, groups.toArray(new ModelGroup[0]));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<ModelMaterial> loadMaterials(File src) throws FileNotFoundException {
		List<ModelMaterial> materials = new ArrayList<ModelMaterial>();
		Scanner filein = new Scanner(src);
		String name = null;
		Vertex4f ambient = null; // (Ka) base color of model
		Vertex4f diffuse = null; // (Kd) color when lit
		Vertex4f specular = null; // (Ks) color of light
		Vertex4f transmissionFilter = null; // (Tf) filters light passing
		// through it
		float specularExponent = 0; // (Ns) relates to specular
		float dissolve = 0; // (d) essentially transparency
		int illumModel = 0; // (illum) which lighting mode to use
		float indexOfRefraction = 1; // (Ni)
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
			case "newmtl":
				if (name != null) {
					materials.add(new ModelMaterial(name, ambient, diffuse, specular, transmissionFilter, specularExponent, dissolve, illumModel, indexOfRefraction));
				}
				name = data[1];
				break;
			case "Ka":
				ambient = new Vertex4f(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]), 1);
				break;
			case "Kd":
				diffuse = new Vertex4f(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]), 1);
				break;
			case "Ks":
				specular = new Vertex4f(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]), 1);
				break;
			case "Tf":
				transmissionFilter = new Vertex4f(Float.parseFloat(data[1]), Float.parseFloat(data[2]), Float.parseFloat(data[3]), 1);
				break;
			case "Ns":
				specularExponent = Float.parseFloat(data[1]);
				break;
			case "d":
				dissolve = Float.parseFloat(data[1]);
				break;
			case "illum":
				illumModel = Integer.parseInt(data[1]);
				break;
			case "Ni":
				indexOfRefraction = Float.parseFloat(data[1]);
				break;
			}
		}
		filein.close();
		return materials;
	}

	private ModelGroup buildGroup(String groupName, ModelMaterial material, List<Float> verts, List<Float> norms, List<Float> texcs, List<Integer> drawi) {
		int length = drawi.size();
		int vertsPerShape = 3;
		Float[] vertices = new Float[length];
		Float[] normals = new Float[length];
		Float[] texcoords = new Float[length];
		for (int i = 0; i < length; i += 3) {
			int vi = drawi.get(i) * vertsPerShape;
			int ti = drawi.get(i + 1) * 1;
			int ni = drawi.get(i + 2) * vertsPerShape;
			if (vi != -1) {
				vertices[i] = verts.get(vi);
				vertices[i + 1] = verts.get(vi + 1);
				vertices[i + 2] = verts.get(vi + 2);
			} else {
				vertices[i] = 0f;
				vertices[i + 1] = 0f;
				vertices[i + 2] = 0f;
			}
			if (ni != -1) {
				normals[i] = norms.get(ni);
				normals[i + 1] = norms.get(ni + 1);
				normals[i + 2] = norms.get(ni + 2);
			} else {
				normals[i] = 0f;
				normals[i + 1] = 0f;
				normals[i + 2] = 0f;
			}
			if (ti != -1) {
				texcoords[i] = texcs.get(ti);
				texcoords[i + 1] = texcs.get(ti + 1);
				texcoords[i + 2] = texcs.get(ti + 2);
			} else {
				texcoords[i] = 0f;
				texcoords[i + 1] = 0f;
				texcoords[i + 2] = 0f;
			}
		}
		AttributeArray vertattrib = new AttributeArray(0, vertices, 3);
		AttributeArray normattrib = new AttributeArray(2, normals, 3);
		AttributeArray texcattrib = new AttributeArray(3, texcoords, 3);
		VBO vbo = new VBO(new AttributeArray[] { vertattrib, normattrib, texcattrib }, vertices.length / 3, GL11.GL_TRIANGLES);
		vbo.create();
		return new ModelGroup(groupName, material, vbo);
	}

	private ModelMaterial getMaterialByName(String name, List<ModelMaterial> materials) {
		for (ModelMaterial mat : materials) {
			if (mat.getName().equals(name)) {
				return mat;
			}
		}
		return null;
	}

	@Override
	public boolean keyReferencesResource(Model resource, String key) {
		return resource.getName().equals(key);
	}

	@Override
	public String getKeyFromResource(Model resource) {
		return resource.getName();
	}

}
