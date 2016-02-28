package de.aasvogel.spiele.strategie.unterbau.allgemein;

import junit.framework.TestCase;
import de.aasvogel.spiele.strategie.unterbau.hex.GeometrieHex;
import de.aasvogel.spiele.strategie.unterbau.hex.PositionHex;

public class TestGeometrie extends TestCase
{
	public void testGetGeometrie()
	{
		Geometrie gotten = Geometrie.getGeometrie(new PositionHex(0, 0, 0));

		assertTrue(gotten instanceof GeometrieHex);
	}
}
