package de.aasvogel.spiele.strategie.unterbau.allgemein;

import de.aasvogel.spiele.strategie.unterbau.hex.KreuzungHex;
import de.aasvogel.spiele.strategie.unterbau.hex.SegmentHex;
import de.aasvogel.spiele.strategie.unterbau.hex.SegmentPositionHex;

public class TestSegmentHex
{
	static KreuzungHex nord = new KreuzungHex(0.99f, 0, 0); // 0
	static KreuzungHex sued = new KreuzungHex(-1, 0, 0); // 1
	static KreuzungHex west = new KreuzungHex(0, 0.49f, -0.5f); // 2
	static KreuzungHex ost = new KreuzungHex(0, -0.1f, 0.1f); // 3

	// (0-3) Nord-Ost: 1.005
	// (1-3) Sued-Ost: 1.014
	// (2-3) West-Ost: 1.030 <- diagonale
	// (0-2) Nord-West: 1.305
	// (1-2) Sued-West: 1.321

	// (0-1) Sued-Nord: 1.99

	public static void main(String[] args)
	{
		testWegfinder();
	}

	public static void testWegfinder()
	{
		doit(nord, sued, west, ost);
		doit(nord, sued, ost, west);
		doit(nord, west, sued, ost);
		doit(nord, west, ost, sued);
		doit(nord, ost, sued, west);
		doit(nord, ost, west, sued);

		doit(west, sued, nord, ost);
		doit(west, sued, ost, nord);
		doit(west, nord, sued, ost);
		doit(west, nord, ost, sued);
		doit(west, ost, sued, nord);
		doit(west, ost, nord, sued);

		doit(ost, sued, nord, west);
		doit(ost, sued, west, nord);
		doit(ost, nord, sued, west);
		doit(ost, nord, west, sued);
		doit(ost, west, sued, nord);
		doit(ost, west, nord, sued);

		doit(sued, ost, nord, west);
		doit(sued, ost, west, nord);
		doit(sued, nord, ost, west);
		doit(sued, nord, west, ost);
		doit(sued, west, ost, nord);
		doit(sued, west, nord, ost);

	}

	private static void doit(Kreuzung a, Kreuzung b, Kreuzung c, Kreuzung d)
	{
		SegmentHex segment = new SegmentHex(new SegmentPositionHex(0, 0, 0),
				new KartenVerweise());

		segment.kreuzungen.add(a);
		segment.kreuzungen.add(b);
		segment.kreuzungen.add(c);
		segment.kreuzungen.add(d);

		segment.fuelleWege();
		System.out.println(segment.wege);

	}
}
