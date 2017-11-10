package com.glutilities.shader;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBGeometryShader4;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBTessellationShader;
import org.lwjgl.opengl.ARBVertexShader;

import com.glutilities.core.Reusable;

/**
 * Represents a shader object.
 * 
 * @author Hanavan99
 *
 */
public class ShaderObject implements Reusable {

	/**
	 * The shader ID.
	 */
	private int shader;

	/**
	 * The type of the shader.
	 */
	private int shaderType;

	/**
	 * The user-defined name of the shader.
	 */
	private String name;

	/**
	 * The code that is compiled into the shader program.
	 */
	private String code;

	/**
	 * Creates a new shader object with the specific shader type, name, and
	 * code/program.
	 * 
	 * @param shaderType the type of shader
	 * @param name the user-defined name of the shader; optional
	 * @param code the code that is compiled into the shader program
	 */
	public ShaderObject(int shaderType, String name, String code) {
		if (shaderType != ARBVertexShader.GL_VERTEX_SHADER_ARB && shaderType != ARBFragmentShader.GL_FRAGMENT_SHADER_ARB && shaderType != ARBGeometryShader4.GL_GEOMETRY_SHADER_ARB && shaderType != ARBTessellationShader.GL_TESS_CONTROL_SHADER
				&& shaderType != ARBTessellationShader.GL_TESS_EVALUATION_SHADER) {
			throw new IllegalArgumentException("Invalid shader type");
		}
		this.shaderType = shaderType;
		this.name = name;
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
	 * Gets the type of shader.
	 * 
	 * @return the type of shader
	 */
	public int getShaderType() {
		return shaderType;
	}

	/**
	 * Gets the user-defined name of this shader; allowed to be null
	 * 
	 * @return the name of the shader
	 */
	public String getName() {
		return name;
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
	public void create() {
		this.shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
	}

	@Override
	public void delete() {
		ARBShaderObjects.glDeleteObjectARB(shader);
	}

}
