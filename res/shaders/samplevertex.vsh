#version 430

uniform mat4 projectionMatrix;
uniform mat4 transformMatrix;
uniform mat4 viewMatrix;

in layout(location=0) vec3 position;
in layout(location=1) vec3 color;
in layout(location=2) vec3 normal;
in layout(location=3) vec2 texcoords;

out vec2 tex_Coords;
out float reflection;
out float specularReflection;

uniform int renderMode;

vec3 light = normalize(vec3(1, 1, 1));

void main() {
	vec4 worldPos = vec4(position, 1.0);
	tex_Coords = texcoords;
	reflection = dot(light, normal);
	vec3 eye = (transformMatrix * vec4(0)).xyz;
	specularReflection = dot(eye, reflect(-light, normal));
	//if (renderMode >= 0 && renderMode <= 1) {
		gl_Position = projectionMatrix * viewMatrix * transformMatrix * worldPos;
	//} else {
		//gl_Position = worldPos;
	//}
}