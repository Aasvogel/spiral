package de.aasvogel.spiele.strategie.unterbau.hex;

import junit.framework.TestCase;

import org.junit.Test;

import de.aasvogel.spiele.strategie.unterbau.allgemein.Geometrie;

public class TestGeometrieHex extends TestCase
{
	@Test
	public void testIstInDreieck()
	{
		PositionHex stdDreieckN = new PositionHex(1, 0, 0);
		PositionHex stdDreieckSW = new PositionHex(0, 1, 0);
		PositionHex stdDreieckSO = new PositionHex(0, 0, 1);

		PositionHex punktInnen = new PositionHex(0, 0, 0);
		assertTrue("Ursprung nicht in stdDreieck1",
				Geometrie.istPunktInDreieck(punktInnen, stdDreieckN,
						stdDreieckSO, stdDreieckSW));
		assertTrue("Ursprung nicht in stdDreieck2",
				Geometrie.istPunktInDreieck(punktInnen, stdDreieckN,
						stdDreieckSW, stdDreieckSO));
		assertTrue("Ursprung nicht in stdDreieck3",
				Geometrie.istPunktInDreieck(punktInnen, stdDreieckSO,
						stdDreieckN, stdDreieckSW));
		assertTrue("Ursprung nicht in stdDreieck4",
				Geometrie.istPunktInDreieck(punktInnen, stdDreieckSO,
						stdDreieckSW, stdDreieckN));
		assertTrue("Ursprung nicht in stdDreieck5",
				Geometrie.istPunktInDreieck(punktInnen, stdDreieckSW,
						stdDreieckN, stdDreieckSO));
		assertTrue("Ursprung nicht in stdDreieck6",
				Geometrie.istPunktInDreieck(punktInnen, stdDreieckSW,
						stdDreieckSO, stdDreieckN));

		PositionHex punktAussen = new PositionHex(1, 1, 0);
		assertFalse("Auﬂenpunkt in stdDreieck1", Geometrie.istPunktInDreieck(
				punktAussen, stdDreieckN, stdDreieckSO, stdDreieckSW));
		assertFalse("Auﬂenpunkt in stdDreieck2", Geometrie.istPunktInDreieck(
				punktAussen, stdDreieckN, stdDreieckSW, stdDreieckSO));
		assertFalse("Auﬂenpunkt in stdDreieck3", Geometrie.istPunktInDreieck(
				punktAussen, stdDreieckSO, stdDreieckN, stdDreieckSW));
		assertFalse("Auﬂenpunkt in stdDreieck4", Geometrie.istPunktInDreieck(
				punktAussen, stdDreieckSO, stdDreieckSW, stdDreieckN));
		assertFalse("Auﬂenpunkt in stdDreieck5", Geometrie.istPunktInDreieck(
				punktAussen, stdDreieckSW, stdDreieckN, stdDreieckSO));
		assertFalse("Auﬂenpunkt in stdDreieck6", Geometrie.istPunktInDreieck(
				punktAussen, stdDreieckSW, stdDreieckSO, stdDreieckN));
	}

	public void testBerechneEntfernung()
	{
		float epsilon = 0.001f;
		PositionHex stdHexN = new PositionHex(1, 0, 0);
		PositionHex stdHexNW = new PositionHex(1, 1, 0);
		PositionHex ursprung = new PositionHex(0, 0, 0);

		assertEquals(0, Geometrie.berechneEntfernung(stdHexN, stdHexN), epsilon);
		assertEquals(1, Geometrie.berechneEntfernung(stdHexN, ursprung),
				epsilon);
		assertEquals(1, Geometrie.berechneEntfernung(stdHexN, stdHexNW),
				epsilon);

		PositionHex somewhereSW = new PositionHex(1.2f, 3.6f, 0);
		assertEquals(2.506f,
				Geometrie.berechneEntfernung(stdHexNW, somewhereSW), epsilon);
	}

	public void testKreuzenSichZweiWege()
	{
		PositionHex stdHexNW = new PositionHex(1, 1, 0);
		PositionHex stdHexSW = new PositionHex(0, 1, 0);
		PositionHex stdHexNO = new PositionHex(1, 0, 1);
		PositionHex stdHexSO = new PositionHex(0, 0, 1);

		assertFalse("Die Wege sollten sich nicht Kreuzen!",
				Geometrie.kreuzenSich2Wege(//
						stdHexNW, stdHexSW, stdHexNO, stdHexSO));

		assertTrue("Die Wege sollten sich Kreuzen!",
				Geometrie.kreuzenSich2Wege(//
						stdHexNW, stdHexSO, stdHexSW, stdHexNO));
	}
}
