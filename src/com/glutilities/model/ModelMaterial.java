package com.glutilities.model;

import com.glutilities.shader.ShaderProgram;
import com.glutilities.util.Vertex4f;

public class ModelMaterial {

	private final String name;
	private final Vertex4f ambient; // (Ka) base color of model
	private final Vertex4f diffuse; // (Kd) color when lit
	private final Vertex4f specular; // (Ks) color of light
	private final Vertex4f transmissionFilter; // (Tf) filters light passing
												// through it
	private final float specularExponent; // (Ns) relates to specular
	private final float dissolve; // (d) essentially transparency
	private final int illumModel; // (illum) which lighting mode to use
	private final float indexOfRefraction; // (Ni)

	public ModelMaterial(String name, Vertex4f ambient, Vertex4f diffuse, Vertex4f specular, Vertex4f transmissionFilter, float specularExponent, float dissolve, int illumModel, float indexOfRefraction) {
		this.name = name;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.transmissionFilter = transmissionFilter;
		this.specularExponent = specularExponent;
		this.dissolve = dissolve;
		this.illumModel = illumModel;
		this.indexOfRefraction = indexOfRefraction;
	}

	public void load(ShaderProgram program) {
		program.setVertex4f("Ka", ambient);
		program.setVertex4f("Kd", diffuse);
		program.setVertex4f("Ks", specular);
		program.setVertex4f("Tf", transmissionFilter);
		program.setFloat("Ns", specularExponent);
		program.setFloat("d", dissolve);
		program.setInt("illum", illumModel);
		program.setFloat("Ni", indexOfRefraction);
	}

	public String getName() {
		return name;
	}

}
