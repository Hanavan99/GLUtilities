#version 430

in vec2 tex_Coords;
in vec4 vcolor;

uniform sampler2D colorfbo;
uniform sampler2D depthfbo;

uniform float time;

out vec4 out_Color;

vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main()
{
	out_Color = texture(colorfbo, tex_Coords);
}