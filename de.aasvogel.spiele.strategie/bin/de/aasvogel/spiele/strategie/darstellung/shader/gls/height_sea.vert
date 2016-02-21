#version 400 core

in vec3 position;
in vec2 textureCoords;

out vec3 colour;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void)
{
	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);

	vec4 pos = transformationMatrix * vec4(position, 1.0);

	vec3 wald = vec3(0.1, 0.4, 0.1);
	vec3 berg = vec3(0.4, 0.4, 0.4);
	vec3 meer = vec3(0.2, 0.3, 0.8);
	
	if (pos.z > 0) 
	{
		colour = berg * pos.z + wald * (1- pos.z);
	} else {
		colour = meer;
	}
}