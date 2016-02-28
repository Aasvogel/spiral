package de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.karte.hex;

import org.lwjgl.util.vector.Vector3f;

import de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.Entity;
import de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.ModelTexture;
import de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.RawModel;
import de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.TexturedModel;
import de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.karte.allgemein.ParzelleDarstellung;
import de.aasvogel.spiele.strategie.darstellung.engine.Loader;
import de.aasvogel.spiele.strategie.unterbau.allgemein.Konstanten;
import de.aasvogel.spiele.strategie.unterbau.hex.PositionHex;

public class MeerDarstellungHex implements ParzelleDarstellung
{
	private final PositionHex mittelpunkt;
	private final float hoehe;
	private final float breite = Konstanten.SEGMENTGROESSE * 20f;

	private Loader loader = Loader.getLoader();

	private final static String textureName = "triangle1";

	public MeerDarstellungHex(float hoehe, PositionHex mittelpunkt)
	{
		this.hoehe = hoehe;
		this.mittelpunkt = mittelpunkt;
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
		float[] vertices = new float[6 * 3];
		float rauf = 0.5f * breite;
		float rueber = (float) Math.sqrt(0.75d) * breite;

		int aktPos = 0;
		vertices[aktPos++] = mittelpunkt.getLaengengrad() + 0;
		vertices[aktPos++] = mittelpunkt.getBreitengrad() + breite;
		vertices[aktPos++] = hoehe;

		vertices[aktPos++] = mittelpunkt.getLaengengrad() + rueber;
		vertices[aktPos++] = mittelpunkt.getBreitengrad() + rauf;
		vertices[aktPos++] = hoehe;

		vertices[aktPos++] = mittelpunkt.getLaengengrad() + rueber;
		vertices[aktPos++] = mittelpunkt.getBreitengrad() - rauf;
		vertices[aktPos++] = hoehe;

		vertices[aktPos++] = mittelpunkt.getLaengengrad() + 0;
		vertices[aktPos++] = mittelpunkt.getBreitengrad() - breite;
		vertices[aktPos++] = hoehe;

		vertices[aktPos++] = mittelpunkt.getLaengengrad() - rueber;
		vertices[aktPos++] = mittelpunkt.getBreitengrad() - rauf;
		vertices[aktPos++] = hoehe;

		vertices[aktPos++] = mittelpunkt.getLaengengrad() - rueber;
		vertices[aktPos++] = mittelpunkt.getBreitengrad() + rauf;
		vertices[aktPos++] = hoehe;

		return vertices;
	}

	private float[] convertTextureCoords()
	{
		float[] textCoords = new float[6 * 2];

		int aktPos = 0;
		textCoords[aktPos++] = 0;
		textCoords[aktPos++] = 0;
		textCoords[aktPos++] = 0;
		textCoords[aktPos++] = 0;
		textCoords[aktPos++] = 0;
		textCoords[aktPos++] = 0;

		textCoords[aktPos++] = 0;
		textCoords[aktPos++] = 0;
		textCoords[aktPos++] = 0;
		textCoords[aktPos++] = 0;
		textCoords[aktPos++] = 0;
		textCoords[aktPos++] = 0;

		return textCoords;
	}

	private int[] convertIndices()
	{
		int[] indices = new int[4 * 3];
		int aktPos = 0;
		indices[aktPos++] = 0;
		indices[aktPos++] = 1;
		indices[aktPos++] = 2;

		indices[aktPos++] = 0;
		indices[aktPos++] = 2;
		indices[aktPos++] = 3;

		indices[aktPos++] = 0;
		indices[aktPos++] = 3;
		indices[aktPos++] = 4;

		indices[aktPos++] = 0;
		indices[aktPos++] = 4;
		indices[aktPos++] = 5;

		return indices;
	}

}
