package de.aasvogel.spiele.strategie.unterbau.hex;

import de.aasvogel.spiele.strategie.unterbau.allgemein.Geometrie;
import de.aasvogel.spiele.strategie.unterbau.allgemein.Kreuzung;

public class GeometrieHex extends Geometrie
{
	static
	{
		Geometrie.setGeometrie(new GeometrieHex());
	}

	@Override
	public boolean kreuzenSich2Wege(Kreuzung anfangA, Kreuzung endeA,
			Kreuzung anfangB, Kreuzung endeB)
	{
		assert (anfangA instanceof KreuzungHex);
		assert (endeA instanceof KreuzungHex);
		assert (anfangB instanceof KreuzungHex);
		assert (endeB instanceof KreuzungHex);
		return kreuzenSich2Wege((KreuzungHex) anfangA, (KreuzungHex) endeA,
				(KreuzungHex) anfangB, (KreuzungHex) endeB);
	}

	private float berechneEntfernungZwischenKreuzungen(KreuzungHex kreuzungA,
			KreuzungHex kreuzungB)
	{
		// 1. Differenz (normaliesiert sich automatisch):
		KreuzungHex differenz = new KreuzungHex(
				kreuzungA.nord - kreuzungB.nord, //
				kreuzungA.suedWest - kreuzungB.suedWest, //
				kreuzungA.suedOst - kreuzungB.suedOst);

		// 2. Entfernung...
		// Der Innenwinkel zwischen Haupt- und Nebenrichtung
		// ist immer 60°. cos(60°) = 1/2
		return (float) Math.sqrt( //
				differenz.getHauptEntfernung() * differenz.getHauptEntfernung()
						+ differenz.getAbweichung() * differenz.getAbweichung()
						- differenz.getAbweichung()
						* differenz.getHauptEntfernung());
	}

	@Override
	public float berechneEntfernungZwischenKreuzungen(Kreuzung kreuzungA,
			Kreuzung kreuzungB)
	{
		assert (kreuzungA instanceof KreuzungHex);
		assert (kreuzungB instanceof KreuzungHex);

		return berechneEntfernungZwischenKreuzungen((KreuzungHex) kreuzungA,
				(KreuzungHex) kreuzungB);
	}

}
