package com.glutilities.shader;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.glutilities.core.Deletable;

public class ShaderProgram implements Deletable {

	private final int program;
	private final List<ShaderObject> shaderObjects = new ArrayList<ShaderObject>();
	private String error;
	
	public ShaderProgram() {
		program = ARBShaderObjects.glCreateProgramObjectARB();
	}
	
	public void addShaderObject(ShaderObject object) {
		shaderObjects.add(object);
	}
	
	public boolean isLinked() {
		return ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_TRUE;
	}
	
	public String getError() {
		return error;
	}
	
	public void compile() {
		
		for (ShaderObject object : shaderObjects) {
			ARBShaderObjects.glShaderSourceARB(object.getShaderID(), object.getShader());
			ARBShaderObjects.glCompileShaderARB(object.getShaderID());
			ARBShaderObjects.glAttachObjectARB(program, object.getShaderID());
		}
		

//		fshader = ARBShaderObjects.glCreateShaderObjectARB(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
//		ARBShaderObjects.glShaderSourceARB(fshader,
//				getResourceAsString(new File("src/main/resources/shaders/bloom.fsh")));
//		ARBShaderObjects.glCompileShaderARB(fshader);
//		ARBShaderObjects.glAttachObjectARB(program, fshader);

		// tshader =
		// ARBShaderObjects.glCreateShaderObjectARB(ARBTessellationShader.GL_TESS_EVALUATION_SHADER);
		// ARBShaderObjects.glShaderSourceARB(tshader,
		// getResourceAsString("/com/fractal/shader/tessellation_shader.txt"));
		// ARBShaderObjects.glCompileShaderARB(tshader);
		// ARBShaderObjects.glAttachObjectARB(program, tshader);
		ARBShaderObjects.glLinkProgramARB(program);
		
		error = ARBShaderObjects.glGetInfoLogARB(program);
	}
	
	public void enable() {
		GL20.glUseProgram(program);
	}
	
	public void disable() {
		GL20.glUseProgram(0);
	}

	@Override
	public void delete() {
		GL20.glDeleteProgram(program);
	}
	
}
