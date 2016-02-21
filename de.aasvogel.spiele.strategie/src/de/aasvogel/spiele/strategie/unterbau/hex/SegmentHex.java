package de.aasvogel.spiele.strategie.unterbau.hex;

import de.aasvogel.spiele.strategie.unterbau.allgemein.KartenVerweise;
import de.aasvogel.spiele.strategie.unterbau.allgemein.Segment;

public class SegmentHex extends Segment
{
	public final SegmentPositionHex position;
	public final PositionHex mittelpunkt;
	public static final float RADIUS = 5f; // eigentlich private...

	public SegmentHex(SegmentPositionHex position, KartenVerweise verweise)
	{
		super(verweise);

		this.position = position;

		this.mittelpunkt = position.toHexKoord(RADIUS);
	}

	@Override
	protected void fuelleKreuzungen(int anzahlVersuche, Segment[] nachbarn)
	{
		int gesamtAnzahl = (int) (3 * RADIUS * RADIUS);
		// System.out.println("Ziel: " + gesamtAnzahl);

		for (int i = 0; i < gesamtAnzahl * anzahlVersuche; i++)
		{
			KreuzungHex kreuzung = KreuzungHex
					.zufallsPunkt(mittelpunkt, RADIUS);

			if (istNeueKreuzungZulaessig(kreuzung, nachbarn))
				kreuzungen.add(kreuzung);
		}
	}

}
