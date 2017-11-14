package com.glutilities.shader;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;

public class VertexShader extends ShaderObject {

	public VertexShader(String name, String code) {
		super(name, code);
	}

	@Override
	public void create() {
		shader = ARBShaderObjects.glCreateShaderObjectARB(ARBVertexShader.GL_VERTEX_SHADER_ARB);
	}

}
