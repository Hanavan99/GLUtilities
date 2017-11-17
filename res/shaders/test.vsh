#version 430

in layout(location=0) vec3 position;
in layout(location=1) vec3 color;
in layout(location=3) vec3 texcoords;

out vec2 tex_Coords;
out vec4 vcolor;

void main() {
	//gl_Position = vec4(position.x * 2 - 1, position.y * 2 - 1, 0.0, 1.0);
	gl_Position = vec4(position.xy, 0.0, 1.0);
	vcolor = vec4(color, 1.0);
	tex_Coords = texcoords.xy;
}