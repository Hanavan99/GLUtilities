#version 430

in vec4 vColor;
in float reflection;

out vec4 out_Color;

void main() {
	
	//out_Color = vec4(vColor.r, 0, vColor.b, 1.0);
	//gl_FragColor = vec4(1, 1, 1, 1);
	gl_FragColor = vec4(reflection, reflection, reflection, 1);
	
}