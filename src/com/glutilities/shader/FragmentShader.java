package com.glutilities.shader;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;

public class FragmentShader extends ShaderObject {

	public FragmentShader(String code) {
		super(code);
	}

	@Override
	public void create() {
		shader = ARBShaderObjects.glCreateShaderObjectARB(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
	}

}
