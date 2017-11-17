#version 430

uniform mat4 projectionMatrix;
uniform mat4 transformMatrix;

in layout(location=0) vec3 position;
in layout(location=1) vec3 color;
in layout(location=2) vec3 normal;
in layout(location=3) vec2 texcoords;

out vec2 tex_Coords;
out vec4 vColor;
out float reflection;

uniform vec4 plane = vec4(0, 0, -1, 1);

void main() {
	
	
	//gl_Position = vec4(position.xy, 0.0, 1.0);
	vec4 worldPos = vec4(position, 1.0);
	
	//gl_ClipDistance[0] = dot(worldPos, plane);
	
	gl_Position = projectionMatrix * transformMatrix * worldPos;
	
	
	
	tex_Coords = texcoords;
	vColor = vec4(color, 1.0);
	reflection = dot(vec3(1, 1, 1), normal);
}