#version 430

in vec2 tex_Coords;
in vec4 vColor;

uniform int renderMode;
uniform sampler2D glyphTex;

out vec4 out_Color;

void main() {
	if (renderMode == 0) {
		out_Color = vColor;
	} else if (renderMode == 1) {
		out_Color = vColor * texture(glyphTex, tex_Coords);
	} else {
		out_Color = texture(glyphTex, tex_Coords);
	}
}