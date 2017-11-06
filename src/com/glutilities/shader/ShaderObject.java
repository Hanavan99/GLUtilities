package com.glutilities.shader;

public class ShaderObject {

	private int shader;
	private int shaderType;
	private String name;
	private String code;

	public ShaderObject(int shader, int shaderType, String name, String code) {
		this.shader = shader;
		this.shaderType = shaderType;
		this.name = name;
		this.code = code;
	}

	public int getShaderID() {
		return shader;
	}

	public int getShaderType() {
		return shaderType;
	}

	public String getName() {
		return name;
	}

	public String getShader() {
		return code;
	}

}
