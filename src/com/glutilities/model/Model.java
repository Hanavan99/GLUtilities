package com.glutilities.model;

import java.util.Arrays;

import com.glutilities.shader.ShaderProgram;

public class Model {

	private String name;
	private ModelGroup[] groups;
	private boolean[] states;

	public Model(String name, ModelGroup[] groups) {
		this.name = name;
		this.groups = groups;
		states = new boolean[groups.length];
		Arrays.fill(states, true);
	}

	public String getName() {
		return name;
	}

	public ModelGroup[] getModelGroups() {
		return groups;
	}

	public void setState(int index, boolean enabled) {
		if (index >= 0 && index < states.length) {
			states[index] = enabled;
		}
	}

	/**
	 * Draws the model using the specified shader program. Only the enabled
	 * model groups will be drawn.
	 * 
	 * @param program the shader program
	 */
	public void draw(ShaderProgram program) {
		for (int i = 0; i < groups.length; i++) {
			if (states[i]) {
				groups[i].draw(program);
			}
		}
	}

}
