#version 430

in vec2 tex_Coords;
in float reflection;
in float specularReflection;

uniform int renderMode;
uniform sampler2D glyphTex;

uniform vec4 Ka; // ambient color
uniform vec4 Ks; // specular color
uniform vec4 Kd; // diffuse color
uniform float d; // dissolve (transparency)
uniform float Ns; // illumination

out vec4 out_Color;


void main() {
	vec4 ambient = vec4(Ka.rgb, d);
	vec4 diffuse = vec4(Kd.rgb * reflection, 0);
	vec4 specular = vec4(Ks.rgb * pow(reflection, 8) * Ns, 0);
	if (renderMode == 0) {
		out_Color = ambient + diffuse;
	} else if (renderMode == 1) {
		out_Color = texture(glyphTex, tex_Coords);
	} else if (renderMode == 2) {
		out_Color = vec4(texture(glyphTex, tex_Coords).rgb, 1);
	} else {
		out_Color = vec4(1, 1, 1, 1);
	} 
}