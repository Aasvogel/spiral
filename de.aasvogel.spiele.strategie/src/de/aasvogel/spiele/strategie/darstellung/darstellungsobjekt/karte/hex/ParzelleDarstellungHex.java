package de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.karte.hex;

import org.lwjgl.util.vector.Vector3f;

import de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.Entity;
import de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.ModelTexture;
import de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.RawModel;
import de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.TexturedModel;
import de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.karte.allgemein.ParzelleDarstellung;
import de.aasvogel.spiele.strategie.darstellung.engine.Loader;
import de.aasvogel.spiele.strategie.unterbau.allgemein.Kreuzung;
import de.aasvogel.spiele.strategie.unterbau.allgemein.Parzelle;
import de.aasvogel.spiele.strategie.unterbau.hex.KreuzungHex;

public class ParzelleDarstellungHex implements ParzelleDarstellung
{
	private final Parzelle parzelle;

	private Loader loader = Loader.getLoader();

	private final static String textureName = "triangle1";

	public ParzelleDarstellungHex(Parzelle parzelle)
	{
		Kreuzung kreuzung = parzelle.getEcken().iterator().next();
		if (!(kreuzung instanceof KreuzungHex))
		{
			throw new IllegalArgumentException("Hex-Kreuzung erwartet!");
		}

		this.parzelle = parzelle;
	}

	@Override
	public Entity load()
	{

		float[] vertices = convertVertices();
		float[] textureCoords = convertTextureCoords();
		int[] indices = convertIndices();

		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture(textureName));
		TexturedModel staticModel = new TexturedModel(model, texture);

		Entity entity = new Entity(staticModel, new Vector3f(0, 0, 0), 0, 0, 0,
				1);

		return entity;
	}

	private float[] convertVertices()
	{
		float[] vertices = new float[parzelle.getEcken().size() * 3];
		int aktPos = 0;
		for (Kreuzung kreuzung : parzelle.getEcken())
		{
			KreuzungHex kHex = (KreuzungHex) kreuzung;
			vertices[aktPos++] = kHex.getLaengengrad();
			vertices[aktPos++] = kHex.getBreitengrad();
			vertices[aktPos++] = kHex.getHoehe();
		}

		return vertices;
	}

	private float[] convertTextureCoords()
	{
		float[] textCoords = new float[parzelle.getEcken().size() * 2];

		textCoords[0] = 0;
		textCoords[1] = 0;

		textCoords[2] = 0;
		textCoords[3] = 1;

		textCoords[4] = 1;
		textCoords[5] = 1;
		return textCoords;
	}

	private int[] convertIndices()
	{
		int[] indices = new int[3];
		indices[0] = 0;
		indices[1] = 1;
		indices[2] = 2;

		return indices;
	}

}
