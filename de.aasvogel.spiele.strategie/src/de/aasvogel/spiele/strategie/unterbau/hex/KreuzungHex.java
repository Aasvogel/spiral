package de.aasvogel.spiele.strategie.unterbau.hex;

import java.awt.geom.Line2D;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import de.aasvogel.spiele.strategie.unterbau.allgemein.Geometrie;
import de.aasvogel.spiele.strategie.unterbau.allgemein.Kreuzung;
import de.aasvogel.spiele.strategie.util.DebugSchalter;
import de.aasvogel.spiele.strategie.util.GecoRandom;

public class KreuzungHex extends PositionHex implements Kreuzung
{
	private static int smallscaleDebugCounter = 0;
	private final int SMALLSCALEDEBUGNAME = smallscaleDebugCounter++;

	private static final Random rand = GecoRandom.getRandom();

	private float m_hoehe = 0; // die wird erst später angepasst...

	private float hauptEntfernung;

	private float abweichung;

	// Einheitsvektoren:
	private static final Vector3f NORD = new Vector3f(1f, 0f, 0f);
	private static final Vector3f SUEDWEST = new Vector3f(0f, 1f, 0f);
	private static final Vector3f SUEDOST = new Vector3f(0f, 0f, 1f);

	public KreuzungHex(float nord, float suedWest, float suedOst)
	{
		super(nord, suedWest, suedOst);
	}

	@Override
	public boolean istEntfernungZuKreuzungKleiner(Kreuzung kreuzung,
			float entfernung)
	{
		assert (kreuzung instanceof KreuzungHex);

		return istEntfernungZuKreuzungKleiner((KreuzungHex) kreuzung,
				entfernung);
	}

	private boolean istEntfernungZuKreuzungKleiner(KreuzungHex kreuzung,
			float max)
	{
		// evtl: diff Hauptentfernung > (?) 1,5*max
		// evtl: unterschiedliche Hauptrichtungen + Hauptentfernung > max
		// if (Math.abs(this.m_breite - kreuzung.m_breite) > entfernung)
		// return false;
		// if (Math.abs(this.m_laenge - kreuzung.m_laenge) > entfernung)
		// return false;
		return Geometrie.getGeometrie().berechneEntfernungZwischenKreuzungen(
				this, kreuzung) < max;
	}

	public boolean kreuzenSich2Wege(KreuzungHex anfangA, KreuzungHex endeA,
			KreuzungHex anfangB, KreuzungHex endeB)
	{
		return Line2D.linesIntersect(anfangA.getBreitengrad(),
				anfangA.getLaengengrad(), endeA.getBreitengrad(),
				endeA.getLaengengrad(), anfangB.getBreitengrad(),
				anfangB.getLaengengrad(), endeB.getBreitengrad(),
				endeB.getLaengengrad());
	}

	@Override
	public String toString()
	{
		if (DebugSchalter.SMALLSCALEDEBUG)
			return "K: " + SMALLSCALEDEBUGNAME;
		else
			return "w: " + nord + ", x: " + suedWest + ", y: " + suedOst
					+ ", hoehe: " + getHoehe();
	}

	@Override
	public float getHoehe()
	{
		return m_hoehe;
	}

	@Override
	public void setHoehe(float hoehe)
	{
		this.m_hoehe = hoehe;
	}

	public static KreuzungHex zufallsPunkt(PositionHex mittelpunkt, float radius)
	{
		int hr = rand.nextInt(3);
		boolean nrUhr = rand.nextBoolean();

		Vector3f hauptrichtung;
		Vector3f nebenrichtung;
		switch (hr)
		{
		case 0:
			hauptrichtung = NORD;
			nebenrichtung = nrUhr ? SUEDWEST : SUEDOST;
			break;
		case 1:
			hauptrichtung = SUEDWEST;
			nebenrichtung = nrUhr ? SUEDOST : NORD;
			break;
		case 2:
			hauptrichtung = SUEDOST;
			nebenrichtung = nrUhr ? NORD : SUEDWEST;
			break;
		default:
			throw new RuntimeException("can't happen");
		}

		// Wir wollen "gleichverteilung" in dem durch hauptrichtung und
		// nebenrichtung definierten Dreieck.
		// => näher am Ursprung muss unwahrscheinlicher sein.
		float entfernung = (float) (Math.sqrt(rand.nextDouble()) * radius);
		float abweichung = rand.nextFloat() * entfernung;

		return createVerschoben(mittelpunkt,//
				hauptrichtung.x * entfernung + nebenrichtung.x * abweichung, //
				hauptrichtung.y * entfernung + nebenrichtung.y * abweichung, //
				hauptrichtung.z * entfernung + nebenrichtung.z * abweichung);
	}

	private static KreuzungHex createVerschoben(PositionHex zentrum,
			float nord, float suedwest, float suedost)
	{
		return new KreuzungHex(zentrum.nord + nord, //
				zentrum.suedWest + suedwest, //
				zentrum.suedOst + suedost);
	}

	float getHauptEntfernung()
	{
		fillHauptEntfernungUndAbweichung();
		return hauptEntfernung;
	}

	private void fillHauptEntfernungUndAbweichung()
	{
		if (nord >= suedWest && nord >= suedOst)
		{
			hauptEntfernung = nord;
			abweichung = suedOst >= suedWest ? suedOst : suedWest;
		} else if (suedWest >= nord && suedWest >= suedOst)
		{
			hauptEntfernung = suedWest;
			abweichung = nord >= suedOst ? nord : suedOst;
		} else if (suedOst >= suedWest && suedOst >= nord)
		{
			hauptEntfernung = suedOst;
			abweichung = nord >= suedWest ? nord : suedWest;
		}
	}

	float getAbweichung()
	{
		fillHauptEntfernungUndAbweichung();
		return abweichung;
	}

	@Override
	public boolean istInDreieck(Kreuzung... dreieck)
	{
		if (dreieck.length != 3)
			throw new IllegalArgumentException(
					"Das Dreieck muss 3 Seiten haben!");
		KreuzungHex a = (KreuzungHex) dreieck[0];
		KreuzungHex b = (KreuzungHex) dreieck[1];
		KreuzungHex c = (KreuzungHex) dreieck[2];

		return istInDreieck(a, b, c);
	}

	private boolean istInDreieck(KreuzungHex a, KreuzungHex b, KreuzungHex c)
	{
		// y = länge, x = breite, p1 = a, p2 = b, p3 = c, p = this
		float teiler = ((b.getLaengengrad() - c.getLaengengrad())
				* (a.getBreitengrad() - c.getBreitengrad()) //
		+ (c.getBreitengrad() - b.getBreitengrad())
				* (a.getLaengengrad() - c.getLaengengrad()));

		float alpha = ((b.getLaengengrad() - c.getLaengengrad())
				* (this.getBreitengrad() - c.getBreitengrad()) //
		+ (c.getBreitengrad() - b.getBreitengrad())
				* (this.getLaengengrad() - c.getLaengengrad())) //
				/ teiler;
		float beta = ((c.getLaengengrad() - a.getLaengengrad())
				* (this.getBreitengrad() - c.getBreitengrad()) //
		+ (a.getBreitengrad() - c.getBreitengrad())
				* (this.getLaengengrad() - c.getLaengengrad())) //
				/ teiler;
		float gamma = 1.0f - alpha - beta;

		return alpha >= 0 && beta >= 0 && gamma >= 0;
		// return false;
	}
}
