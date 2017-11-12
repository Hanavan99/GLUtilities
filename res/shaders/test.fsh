#version 430

in vec2 tex_Coords;
in vec4 vcolor;

uniform sampler2D fbo;
uniform sampler2D depthfbo;

out vec4 out_Color;

void main()
{
    vec4 sum = vec4(0.0);
	//int blur = int(clamp(abs(texture(depthfbo, tex_Coords).r - 0.9) * 100.0, 0, 5));
	sum = texture(fbo, tex_Coords);
	out_Color = sum;
	//out_Color = vec4(1, 1, 1, 1);
}