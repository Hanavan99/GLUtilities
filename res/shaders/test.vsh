#version 430

in layout(location=0) vec3 position;
in layout(location=3) vec3 texcoords;

out vec2 tex_Coords;

void main() {
	gl_Position = vec4(position, 1.0);
	tex_Coords = texcoords.xy;
}