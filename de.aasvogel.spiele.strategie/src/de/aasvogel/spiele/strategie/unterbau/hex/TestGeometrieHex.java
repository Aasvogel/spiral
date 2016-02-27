package de.aasvogel.spiele.strategie.unterbau.hex;

import de.aasvogel.spiele.strategie.unterbau.allgemein.Geometrie;
import de.aasvogel.spiele.strategie.util.AasvogelTest;

public class TestGeometrieHex extends AasvogelTest
{
	public static void main(String[] args)
	{
		TestGeometrieHex tgh = new TestGeometrieHex();

		tgh.testIstInDreieck();
	}

	public void testIstInDreieck()
	{
		PositionHex stdDreieckN = new PositionHex(1, 0, 0);
		PositionHex stdDreieckSW = new PositionHex(0, 1, 0);
		PositionHex stdDreieckSO = new PositionHex(0, 0, 1);

		PositionHex testkandidat = new PositionHex(0, 0, 0);
		assertTrue(Geometrie.istPunktInDreieck(testkandidat, stdDreieckN,
				stdDreieckSO, stdDreieckSW), "Ursprung nicht in stdDreieck1");
		assertTrue(Geometrie.istPunktInDreieck(testkandidat, stdDreieckN,
				stdDreieckSW, stdDreieckSO), "Ursprung nicht in stdDreieck2");
		assertTrue(Geometrie.istPunktInDreieck(testkandidat, stdDreieckSO,
				stdDreieckN, stdDreieckSW), "Ursprung nicht in stdDreieck3");
		assertTrue(Geometrie.istPunktInDreieck(testkandidat, stdDreieckSO,
				stdDreieckSW, stdDreieckN), "Ursprung nicht in stdDreieck4");
		assertTrue(Geometrie.istPunktInDreieck(testkandidat, stdDreieckSW,
				stdDreieckN, stdDreieckSO), "Ursprung nicht in stdDreieck5");
		assertTrue(Geometrie.istPunktInDreieck(testkandidat, stdDreieckSW,
				stdDreieckSO, stdDreieckN), "Ursprung nicht in stdDreieck6");

		testkandidat = new PositionHex(1, 1, 0);
		assertTrue(!Geometrie.istPunktInDreieck(testkandidat, stdDreieckN,
				stdDreieckSO, stdDreieckSW), "Au�enpunkt in stdDreieck1");
		assertTrue(!Geometrie.istPunktInDreieck(testkandidat, stdDreieckN,
				stdDreieckSW, stdDreieckSO), "Au�enpunkt in stdDreieck2");
		assertTrue(!Geometrie.istPunktInDreieck(testkandidat, stdDreieckSO,
				stdDreieckN, stdDreieckSW), "Au�enpunkt in stdDreieck3");
		assertTrue(!Geometrie.istPunktInDreieck(testkandidat, stdDreieckSO,
				stdDreieckSW, stdDreieckN), "Au�enpunkt in stdDreieck4");
		assertTrue(!Geometrie.istPunktInDreieck(testkandidat, stdDreieckSW,
				stdDreieckN, stdDreieckSO), "Au�enpunkt in stdDreieck5");
		assertTrue(!Geometrie.istPunktInDreieck(testkandidat, stdDreieckSW,
				stdDreieckSO, stdDreieckN), "Au�enpunkt in stdDreieck6");

		System.out.println("IstInDreieck - OK");
	}
}
