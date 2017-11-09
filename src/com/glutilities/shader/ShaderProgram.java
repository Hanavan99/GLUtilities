package com.glutilities.shader;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBGeometryShader4;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBTessellationShader;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.glutilities.core.Reusable;

/**
 * Represents a shader program which consists of any combination of a vertex
 * shader, fragment shader, geometry shader, tessellation control shader, or
 * tessellation evaluation shader.
 * 
 * @author Hanavan99
 *
 */
public class ShaderProgram implements Reusable {

	/**
	 * The program ID of this program.
	 */
	private int program;

	/**
	 * The vertex shader object.
	 */
	private ShaderObject vertexShader;

	/**
	 * The fragment shader object.
	 */
	private ShaderObject fragmentShader;

	/**
	 * The geometry shader object.
	 */
	private ShaderObject geometryShader;

	/**
	 * The tessellation control shader.
	 */
	private ShaderObject tessControlShader;

	/**
	 * The tessellation evaluation shader.
	 */
	private ShaderObject tessEvalShader;

	/**
	 * When linked, this string will be the output of the linking process. If there
	 * is no error, this string will become null. If there is an error, this string
	 * contains the error information provided by OpenGL.
	 */
	private String error;

	/**
	 * Creates a new shader program. A shader program can only contain one of each
	 * type of shader. If you are not using a certain type of shader, set it to
	 * null.
	 * 
	 * @param vertexShader
	 *            the vertex shader
	 * @param fragmentShader
	 *            the fragment shader
	 * @param geometryShader
	 *            the geometry shader
	 * @param tessControlShader
	 *            the tessellation control shader
	 * @param tessEvalShader
	 *            the tessellation evaluation shader
	 */
	public ShaderProgram(ShaderObject vertexShader, ShaderObject fragmentShader, ShaderObject geometryShader,
			ShaderObject tessControlShader, ShaderObject tessEvalShader) {
		this.vertexShader = vertexShader;
		this.fragmentShader = fragmentShader;
		this.geometryShader = geometryShader;
		this.tessControlShader = tessControlShader;
		this.tessEvalShader = tessEvalShader;
	}

	/**
	 * Returns if the program is linked. Shader programs are not linked until all of
	 * the shader objects are compiled and attached to the main program.
	 * 
	 * @return if the program is linked
	 */
	public boolean isLinked() {
		return ARBShaderObjects.glGetObjectParameteriARB(program,
				ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_TRUE;
	}

	/**
	 * Gets the error text resulting from the linking process.
	 * 
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * Links the shader program. This process automatically compiles and attaches
	 * all of the shader objects to the shader program, and then the program is
	 * linked.
	 */
	public void link() {
		ShaderObject[] shaderObjects = new ShaderObject[] { vertexShader, fragmentShader, geometryShader,
				tessControlShader, tessEvalShader };
		int[] shaderTypes = new int[] { ARBVertexShader.GL_VERTEX_SHADER_ARB, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB,
				ARBGeometryShader4.GL_GEOMETRY_SHADER_ARB, ARBTessellationShader.GL_TESS_CONTROL_SHADER,
				ARBTessellationShader.GL_TESS_EVALUATION_SHADER };

		for (int i = 0; i < shaderObjects.length; i++) {
			ShaderObject object = shaderObjects[i];
			if (object != null) {
				if (object.getShaderType() != shaderTypes[i])
				ARBShaderObjects.glShaderSourceARB(object.getShaderID(), object.getShader());
				ARBShaderObjects.glCompileShaderARB(object.getShaderID());
				ARBShaderObjects.glAttachObjectARB(program, object.getShaderID());
			}
		}

		ARBShaderObjects.glLinkProgramARB(program);
		error = ARBShaderObjects.glGetInfoLogARB(program);
	}

	/**
	 * Enables the shader program. Any subsequent OpenGL calls will be affected by
	 * the relevant shader objects. This process continues until {@code disable()}
	 * is called.
	 */
	public void enable() {
		GL20.glUseProgram(program);
	}

	/**
	 * Disables the shader program. Any subsequent OpenGL calls will operate as set
	 * up before the shader was enabled.
	 */
	public void disable() {
		GL20.glUseProgram(0);
	}

	@Override
	public void create() {
		program = ARBShaderObjects.glCreateProgramObjectARB();
	}

	@Override
	public void delete() {
		GL20.glDeleteProgram(program);
	}

}
