#version 430

in vec4 vColor;
in float reflection;

out vec4 out_Color;

void main() {
	
	//out_Color = vec4(vColor.r, 0, vColor.b, 1.0);
	//gl_FragColor = vec4(1, 1, 1, 1);
	float ref = pow(reflection, 3);
	out_Color = (vec4(ref / 2, ref / 2, ref / 2, 1) + vColor) / 2;
	//out_Color = vColor;
	
}