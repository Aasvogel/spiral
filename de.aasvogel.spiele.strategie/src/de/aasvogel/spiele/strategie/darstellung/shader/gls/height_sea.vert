#version 400 core

in vec3 position;
in vec2 textureCoords;

out vec4 pass_colour;
out vec2 pass_textureCoords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void)
{
	pass_textureCoords = textureCoords;
	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);

	vec4 pos = transformationMatrix * vec4(position, 1.0);

	vec4 wald = vec4(0.1, 0.4, 0.1, 1.0);
	vec4 berg = vec4(0.4, 0.4, 0.4, 1.0);
	vec4 meer = vec4(0.2, 0.3, 0.8, 1.0);
	
	if (pos.z > 0) 
	{
		pass_colour = berg * pos.z + wald * (1- pos.z);
	} else {
		pass_colour = meer;
	}
}