package com.glutilities.model;

import com.glutilities.buffer.VBO;
import com.glutilities.shader.ShaderProgram;

public class ModelGroup {

	private String name;
	private ModelMaterial material;
	private VBO vbo;

	public ModelGroup(String name, ModelMaterial material, VBO vbo) {
		this.name = name;
		this.material = material;
		this.vbo = vbo;
	}

	public String getName() {
		return name;
	}

	public void draw(ShaderProgram program) {
		if (material != null) {
			material.load(program);
		}
		vbo.draw();
	}

}
