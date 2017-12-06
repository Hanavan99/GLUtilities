#version 430

in vec2 tex_Coords;
in vec4 vcolor;

uniform sampler2D colorfbo;
uniform sampler2D depthfbo;

out vec4 out_Color;

void main()
{
    out_Color = vec4(tex_Coords.xy, 1, 1);
}