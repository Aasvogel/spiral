#version 400 core

in float height;
in vec2 pass_textureCoords;

out vec4 out_Color;
uniform sampler2D textureSampler;

void main(void)
{
	float kueste = 0.0;
	float waldUnten = 0.3;
	float waldOben = 0.6;

	vec4 wald = vec4(0.1, 0.4, 0.1, 1.0);
	vec4 berg = vec4(0.4, 0.4, 0.4, 1.0);
	vec4 meer = vec4(0.2, 0.3, 0.8, 1.0);
	
	vec4 color_height;
	vec4 color_texture;
	
	if (height < kueste) 
	{
		color_height = meer;
	} else if (height < waldUnten) {
		color_height = wald;
	} else if (height < waldOben) {
		float bergfaktor = (height - waldUnten) / (waldOben - waldUnten);
		color_height = berg * bergfaktor + wald * (1- bergfaktor);
	} else {
		color_height = berg;
	}
	
	color_texture = texture(textureSampler,pass_textureCoords);
	out_Color = color_height * color_texture;
//	if (pass_textureCoords.x < 0.03 || pass_textureCoords.y > 0.97 || (pass_textureCoords.y - pass_textureCoords.x )< 0.03)
//		out_Color = vec4(0.1, 0.1, 0.1, 1.0);
//	else
//		out_Color = color_height;
	
}