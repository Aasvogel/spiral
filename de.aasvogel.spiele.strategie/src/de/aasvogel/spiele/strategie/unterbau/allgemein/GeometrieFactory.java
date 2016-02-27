package de.aasvogel.spiele.strategie.unterbau.allgemein;

import de.aasvogel.spiele.strategie.unterbau.hex.GeometrieHex;
import de.aasvogel.spiele.strategie.unterbau.hex.PositionHex;

public class GeometrieFactory
{
	public static Geometrie getGeometrie(Position pos)
	{
		if (pos instanceof PositionHex)
			return new GeometrieHex();

		throw new IllegalArgumentException("Unknown Position Type: " + pos);
	}
}
