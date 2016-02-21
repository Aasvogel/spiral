package de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt;

public class ModelTexture
{
	// hat ja mal garnix. Lasse ich aber doch drin, damit klaar ist, dass
	// verschiedene TexturedModels die gleiche Textur verwenden können.
	private int textureID;

	public ModelTexture(int id)
	{
		this.textureID = id;
	}

	public int getID()
	{
		return textureID;
	}
}
