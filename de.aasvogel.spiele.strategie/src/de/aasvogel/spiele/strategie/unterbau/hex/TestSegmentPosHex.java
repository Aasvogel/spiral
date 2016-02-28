package de.aasvogel.spiele.strategie.unterbau.hex;

import junit.framework.TestCase;
import de.aasvogel.spiele.strategie.unterbau.allgemein.Konstanten;
import de.aasvogel.spiele.strategie.unterbau.allgemein.SegmentPosition;

public class TestSegmentPosHex extends TestCase
{
	public void testNormalisierung()
	{
		SegmentPosition testkandidat = new SegmentPositionHex(1, 1, 1);
		assertEquals(new SegmentPositionHex(0, 0, 0), testkandidat);

		testkandidat = new SegmentPositionHex(-1, 0, 1);
		assertEquals(new SegmentPositionHex(0, 1, 2), testkandidat);

		testkandidat = new SegmentPositionHex(28, -2, 2016);
		assertEquals(new SegmentPositionHex(30, 0, 2018), testkandidat);
	}

	public void testToPosition()
	{
		SegmentPositionHex testkandidat = new SegmentPositionHex(0, 0, 0);
		PositionHex soll = new PositionHex(0, 0, 0);
		assertEquals(soll, testkandidat.toPosition());

		testkandidat = new SegmentPositionHex(1, 0, 0);
		soll = new PositionHex(Konstanten.SEGMENTGROESSE, 0,
				2 * Konstanten.SEGMENTGROESSE);
		assertEquals(soll, testkandidat.toPosition());

		int nw = 21;
		int sw = 5;
		testkandidat = new SegmentPositionHex(0, nw, sw);
		soll = new PositionHex((2 * nw - sw) * Konstanten.SEGMENTGROESSE,
				(nw + sw) * Konstanten.SEGMENTGROESSE, 0f);
		assertEquals(soll, testkandidat.toPosition());
	}

	public void testAdd()
	{
		SegmentPositionHex testkandidat = new SegmentPositionHex(0, 0, 0);
		testkandidat.add(1, 2, 3);
		assertEquals(new SegmentPositionHex(0, 0, 0), testkandidat);

		testkandidat = new SegmentPositionHex(0, 0, 0);
		testkandidat = testkandidat.add(1, 2, 3);
		assertEquals(new SegmentPositionHex(1, 2, 3), testkandidat);
	}

	public void testGetDistanzZu()
	{
		SegmentPositionHex testkandidat = new SegmentPositionHex(0, 0, 0);
		assertEquals(0, testkandidat.getDistanzZu(testkandidat));

		SegmentPositionHex east = new SegmentPositionHex(1, 0, 0);
		SegmentPositionHex ursprung = new SegmentPositionHex(0, 0, 0);
		SegmentPositionHex somewhereSouth = new SegmentPositionHex(3, 0, 12);
		assertEquals(1, east.getDistanzZu(ursprung));
		assertEquals(1, ursprung.getDistanzZu(east));
		assertEquals(12, somewhereSouth.getDistanzZu(ursprung));
		assertEquals(12, somewhereSouth.getDistanzZu(east));
	}
}
