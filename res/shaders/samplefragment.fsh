#version 430

in vec2 tex_Coords;
in vec4 vColor;
in float reflection;

uniform int renderMode;
uniform sampler2D glyphTex;

out vec4 out_Color;

void main() {
	if (renderMode == 0) {
		out_Color = vec4(vColor.rgb * pow(reflection, 4), vColor.a) + vec4(0.2);
	} else if (renderMode == 1) {
		out_Color = vColor * texture(glyphTex, tex_Coords);
	} else if (renderMode == 2) {
		out_Color = texture(glyphTex, tex_Coords);
	} else {
		out_Color = vec4(1, 1, 1, 1);
	}
}