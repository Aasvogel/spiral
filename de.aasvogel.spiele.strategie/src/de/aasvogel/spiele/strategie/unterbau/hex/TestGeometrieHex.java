package de.aasvogel.spiele.strategie.unterbau.hex;

import junit.framework.TestCase;

import org.junit.Test;

import de.aasvogel.spiele.strategie.unterbau.allgemein.Geometrie;

public class TestGeometrieHex extends TestCase
{
	public static void main(String[] args)
	{
		TestGeometrieHex tgh = new TestGeometrieHex();

		tgh.testIstInDreieck();
	}

	@Test
	public void testIstInDreieck()
	{
		PositionHex stdDreieckN = new PositionHex(1, 0, 0);
		PositionHex stdDreieckSW = new PositionHex(0, 1, 0);
		PositionHex stdDreieckSO = new PositionHex(0, 0, 1);

		PositionHex testkandidat = new PositionHex(0, 0, 0);
		assertTrue("Ursprung nicht in stdDreieck1",
				Geometrie.istPunktInDreieck(testkandidat, stdDreieckN,
						stdDreieckSO, stdDreieckSW));
		assertTrue("Ursprung nicht in stdDreieck2",
				Geometrie.istPunktInDreieck(testkandidat, stdDreieckN,
						stdDreieckSW, stdDreieckSO));
		assertTrue("Ursprung nicht in stdDreieck3",
				Geometrie.istPunktInDreieck(testkandidat, stdDreieckSO,
						stdDreieckN, stdDreieckSW));
		assertTrue("Ursprung nicht in stdDreieck4",
				Geometrie.istPunktInDreieck(testkandidat, stdDreieckSO,
						stdDreieckSW, stdDreieckN));
		assertTrue("Ursprung nicht in stdDreieck5",
				Geometrie.istPunktInDreieck(testkandidat, stdDreieckSW,
						stdDreieckN, stdDreieckSO));
		assertTrue("Ursprung nicht in stdDreieck6",
				Geometrie.istPunktInDreieck(testkandidat, stdDreieckSW,
						stdDreieckSO, stdDreieckN));

		testkandidat = new PositionHex(1, 1, 0);
		assertFalse("Auﬂenpunkt in stdDreieck1", Geometrie.istPunktInDreieck(
				testkandidat, stdDreieckN, stdDreieckSO, stdDreieckSW));
		assertFalse("Auﬂenpunkt in stdDreieck2", Geometrie.istPunktInDreieck(
				testkandidat, stdDreieckN, stdDreieckSW, stdDreieckSO));
		assertFalse("Auﬂenpunkt in stdDreieck3", Geometrie.istPunktInDreieck(
				testkandidat, stdDreieckSO, stdDreieckN, stdDreieckSW));
		assertFalse("Auﬂenpunkt in stdDreieck4", Geometrie.istPunktInDreieck(
				testkandidat, stdDreieckSO, stdDreieckSW, stdDreieckN));
		assertFalse("Auﬂenpunkt in stdDreieck5", Geometrie.istPunktInDreieck(
				testkandidat, stdDreieckSW, stdDreieckN, stdDreieckSO));
		assertFalse("Auﬂenpunkt in stdDreieck6", Geometrie.istPunktInDreieck(
				testkandidat, stdDreieckSW, stdDreieckSO, stdDreieckN));
	}
}
