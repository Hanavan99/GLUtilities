package com.glutilities.shader;

import org.lwjgl.opengl.ARBShaderObjects;

import com.glutilities.core.Reusable;

/**
 * Represents a shader object.
 * 
 * @author Hanavan99
 *
 */
public abstract class ShaderObject implements Reusable {

	/**
	 * The shader ID.
	 */
	protected int shader;

	/**
	 * The code that is compiled into the shader program.
	 */
	private String code;

	/**
	 * Creates a new shader object with a user-defined name and code/program.
	 * 
	 * @param code the code that is compiled into the shader program
	 */
	public ShaderObject(String code) {
		this.code = code;
	}

	/**
	 * Gets the ID of the shader.
	 * 
	 * @return the ID
	 */
	public int getShaderID() {
		return shader;
	}

	/**
	 * Gets the code that was linked to the shader program.
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code that will be linked to the shader program. If the program
	 * is linked before this field is updated, the program has to be re-linked
	 * to update it.
	 * 
	 * @param code the code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public void delete() {
		ARBShaderObjects.glDeleteObjectARB(shader);
	}

}
