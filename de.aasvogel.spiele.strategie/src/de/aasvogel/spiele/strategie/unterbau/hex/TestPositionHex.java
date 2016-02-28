package de.aasvogel.spiele.strategie.unterbau.hex;

import junit.framework.TestCase;

import org.junit.Test;

public class TestPositionHex extends TestCase
{

	@Test
	public void testLaengenUndBreitenGrad()
	{
		double epsilon = 0.0001;

		PositionHex testkandidat = new PositionHex(1, 0, 0);
		assertEquals(1, testkandidat.getBreitengrad(), epsilon);
		assertEquals(0, testkandidat.getLaengengrad(), epsilon);
		testkandidat = new PositionHex(0, 1, 0);
		assertEquals(-0.5f, testkandidat.getBreitengrad(), epsilon);
		assertEquals(Math.sqrt(0.75), testkandidat.getLaengengrad(), epsilon);
		testkandidat = new PositionHex(0, 0, 1);
		assertEquals(-0.5f, testkandidat.getBreitengrad(), epsilon);
		assertEquals(-Math.sqrt(0.75), testkandidat.getLaengengrad(), epsilon);

		testkandidat = new PositionHex(0, 1, 1);
		assertEquals(-1, testkandidat.getBreitengrad(), epsilon);
		assertEquals(0, testkandidat.getLaengengrad(), epsilon);
		testkandidat = new PositionHex(1, 0, 1);
		assertEquals(0.5f, testkandidat.getBreitengrad(), epsilon);
		assertEquals(-Math.sqrt(0.75), testkandidat.getLaengengrad(), epsilon);
		testkandidat = new PositionHex(1, 1, 0);
		assertEquals(0.5f, testkandidat.getBreitengrad(), epsilon);
		assertEquals(Math.sqrt(0.75), testkandidat.getLaengengrad(), epsilon);
	}

	@Test
	public void testNormalisierung()
	{
		PositionHex testkandidat = new PositionHex(1, 1, 1);
		PositionHex tkNormal = new PositionHex(0, 0, 0);
		assertEquals(tkNormal, testkandidat);
		assertEquals(tkNormal.hashCode(), testkandidat.hashCode());

		testkandidat = new PositionHex(-1, 0, 1);
		tkNormal = new PositionHex(0, 1, 2);
		assertEquals(tkNormal, testkandidat);
		assertEquals(tkNormal.hashCode(), testkandidat.hashCode());

		testkandidat = new PositionHex(27, -2, 2016);
		tkNormal = new PositionHex(29, 0, 2018);
		assertEquals(tkNormal, testkandidat);
		assertEquals(tkNormal.hashCode(), testkandidat.hashCode());
	}

	@Test
	public void testZufallsPosition()
	{
		PositionHex ausgangspunkt = new PositionHex(0, 0, 0);
		for (int i = 0; i < 50; i++)
		{
			PositionHex zufall = ausgangspunkt.getZufallsPositionImUmkreis(1);

			assertTrue("Zu viel Nord: " + zufall.nord, zufall.nord < 1);
			assertTrue("Zu viel SuedWese: " + zufall.suedWest,
					zufall.suedWest < 1);
			assertTrue("Zu viel SuedOst: " + zufall.suedOst, zufall.suedOst < 1);
		}
	}

}
