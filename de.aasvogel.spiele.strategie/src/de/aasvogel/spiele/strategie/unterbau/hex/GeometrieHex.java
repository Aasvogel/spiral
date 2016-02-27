package de.aasvogel.spiele.strategie.unterbau.hex;

import java.awt.geom.Line2D;

import de.aasvogel.spiele.strategie.unterbau.allgemein.Geometrie;
import de.aasvogel.spiele.strategie.unterbau.allgemein.Position;

public class GeometrieHex extends Geometrie
{
	private float berechneEntfernungHex(PositionHex posA, PositionHex posB)
	{
		// 1. Differenz (normaliesiert sich automatisch):
		PositionHex differenz = new PositionHex(posA.nord - posB.nord, //
				posA.suedWest - posB.suedWest, //
				posA.suedOst - posB.suedOst);

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
	public boolean kreuzenSich2WegeDo(Position anfangA, Position endeA,
			Position anfangB, Position endeB)
	{
		assert (anfangA instanceof PositionHex);
		assert (endeA instanceof PositionHex);
		assert (anfangB instanceof PositionHex);
		assert (endeB instanceof PositionHex);
		return kreuzenSich2HexWege((PositionHex) anfangA, (PositionHex) endeA,
				(PositionHex) anfangB, (PositionHex) endeB);
	}

	private boolean kreuzenSich2HexWege(PositionHex anfangA, PositionHex endeA,
			PositionHex anfangB, PositionHex endeB)
	{
		return Line2D.linesIntersect(anfangA.getBreitengrad(),
				anfangA.getLaengengrad(), endeA.getBreitengrad(),
				endeA.getLaengengrad(), anfangB.getBreitengrad(),
				anfangB.getLaengengrad(), endeB.getBreitengrad(),
				endeB.getLaengengrad());
	}

	@Override
	public float berechneEntfernungDo(Position kreuzungA, Position kreuzungB)
	{
		assert (kreuzungA instanceof PositionHex);
		assert (kreuzungB instanceof PositionHex);

		return berechneEntfernungHex((PositionHex) kreuzungA,
				(PositionHex) kreuzungB);
	}

	@Override
	public boolean istPunktInDreieckDo(Position punkt, Position[] dreieck)
	{
		if (dreieck.length != 3)
			throw new IllegalArgumentException(
					"Das Dreieck muss 3 Seiten haben!");
		PositionHex a = (PositionHex) dreieck[0];
		PositionHex b = (PositionHex) dreieck[1];
		PositionHex c = (PositionHex) dreieck[2];
		PositionHex x = (PositionHex) punkt;

		return istInDreieck(x, a, b, c);
	}

	private boolean istInDreieck(PositionHex x, PositionHex a, PositionHex b,
			PositionHex c)
	{
		// y = länge, x = breite, p1 = a, p2 = b, p3 = c, p = this
		float teiler = ((b.getLaengengrad() - c.getLaengengrad())
				* (a.getBreitengrad() - c.getBreitengrad()) //
		+ (c.getBreitengrad() - b.getBreitengrad())
				* (a.getLaengengrad() - c.getLaengengrad()));

		float alpha = ((b.getLaengengrad() - c.getLaengengrad())
				* (x.getBreitengrad() - c.getBreitengrad()) //
		+ (c.getBreitengrad() - b.getBreitengrad())
				* (x.getLaengengrad() - c.getLaengengrad())) //
				/ teiler;

		if (alpha < 0)
			return false;

		float beta = ((c.getLaengengrad() - a.getLaengengrad())
				* (x.getBreitengrad() - c.getBreitengrad()) //
		+ (a.getBreitengrad() - c.getBreitengrad())
				* (x.getLaengengrad() - c.getLaengengrad())) //
				/ teiler;

		if (beta < 0)
			return false;

		float gamma = 1.0f - alpha - beta;

		if (gamma < 0)
			return false;

		return true;
	}
}
