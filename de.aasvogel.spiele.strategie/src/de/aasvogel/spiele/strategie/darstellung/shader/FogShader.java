package de.aasvogel.spiele.strategie.darstellung.shader;

public class FogShader extends StaticShader
{
	private static final String FOLDER = "src/de/aasvogel/spiele/strategie/darstellung/shader/gls/";
	private static final String VERTEX_FILE = FOLDER + "fog.vert";
	private static final String FRAGMENT_FILE = FOLDER + "direct.frag";

	public FogShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
}
