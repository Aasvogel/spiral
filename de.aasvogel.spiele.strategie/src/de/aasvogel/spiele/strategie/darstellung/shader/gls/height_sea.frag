#version 400 core

in float height;
in vec2 pass_textureCoords;

out vec4 out_Color;
uniform sampler2D textureSampler;

void main(void)
{
	float meer_h = 0.0;
	float strand_h = 0.03;
	float tiefebene_h = 0.15;
	float hochebene_h = 0.27;
	float wald_h = 0.45;
	float stein_h = 0.7;

	vec4 meer = vec4(0.2, 0.3, 0.8, 1.0);
	
	vec4 strand = vec4(0.87, 0.88, 0.28, 1.0);
	vec4 tiefebene = vec4(0.55, 0.96, 0.25, 1.0);
	vec4 hochebene = vec4(0.21, 0.76, 0.04, 1.0);
	
	vec4 wald = vec4(0.1, 0.4, 0.1, 1.0);
	vec4 stein = vec4(0.4, 0.4, 0.4, 1.0);
	vec4 schnee = vec4(0.9, 0.9, 0.9, 1.0);
	
	vec4 color_height;
	vec4 color_texture;
	
	if (height < meer_h) 
		color_height = meer;
	else if (height < strand_h) 
		color_height = strand;
	else if (height < tiefebene_h) 
		color_height = tiefebene;
	else if (height < hochebene_h) 
		color_height = hochebene;
	else if (height < wald_h) 
		color_height = wald;
	else if (height < stein_h) 
		color_height = stein;
	else
		color_height = schnee;
	
	color_texture = texture(textureSampler,pass_textureCoords);
	out_Color = color_height * color_texture;
//	if (pass_textureCoords.x < 0.03 || pass_textureCoords.y > 0.97 || (pass_textureCoords.y - pass_textureCoords.x )< 0.03)
//		out_Color = vec4(0.1, 0.1, 0.1, 1.0);
//	else
//		out_Color = color_height;
	
}