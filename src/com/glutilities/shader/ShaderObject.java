package com.glutilities.shader;

import org.lwjgl.opengl.ARBShaderObjects;

import com.glutilities.core.Reusable;

public class ShaderObject implements Reusable {

	private int shader;
	private int shaderType;
	private String name;
	private String code;

	public ShaderObject(int shaderType, String name, String code) {
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

	@Override
	public void create() {
		this.shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
	}
	
	@Override
	public void delete() {
		ARBShaderObjects.glDeleteObjectARB(shader);
	}

	

}
