#version 400 core

in vec4 pass_colour;
in vec2 pass_textureCoords;

out vec4 out_Color;
uniform sampler2D textureSampler;

void main(void)
{
	vec4 color_texture = texture(textureSampler,pass_textureCoords);

	out_Color = color_texture * pass_colour;

//	if (pass_textureCoords.x < 0.03 || pass_textureCoords.y > 0.97 || (pass_textureCoords.y - pass_textureCoords.x )< 0.03)
//		out_Color = vec4(0.1, 0.1, 0.1, 1.0);
//	else
//		out_Color = pass_colour;
	
}