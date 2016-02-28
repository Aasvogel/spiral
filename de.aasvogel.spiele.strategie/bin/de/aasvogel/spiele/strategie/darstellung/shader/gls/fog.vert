#version 400 core

in vec3 position;
in vec2 textureCoords;

out vec4 colour;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(void)
{
	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);

	vec4 pos = transformationMatrix * vec4(position, 1.0);

	float alpha = 0.2;
	if (pos.z > -0.0001)
		alpha = 0.6;	

	colour = vec4(0.2, 0.3, 0.8, alpha);
}