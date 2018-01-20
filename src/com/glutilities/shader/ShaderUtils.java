package com.glutilities.shader;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ShaderUtils {

	public static ShaderProgram loadShaderProgram(File directory, String baseName) {
		VertexShader vertexShader = null;
		FragmentShader fragmentShader = null;
		File vertexFile = new File(directory, baseName + ".vsh");
		if (vertexFile.exists()) {
			vertexShader = new VertexShader(loadFile(vertexFile));
			vertexShader.create();
		}
		File fragmentFile = new File(directory, baseName + ".fsh");
		if (fragmentFile.exists()) {
			fragmentShader = new FragmentShader(loadFile(fragmentFile));
			fragmentShader.create();
		}
		ShaderProgram program = new ShaderProgram(vertexShader, fragmentShader);
		program.create();
		program.link();
		return program;
	}
	
	private static String loadFile(File file) {
		try {
			Scanner s = new Scanner(file);
			String result = "";
			while (s.hasNext()) {
				result += s.nextLine() + "\n";
			}
			s.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
