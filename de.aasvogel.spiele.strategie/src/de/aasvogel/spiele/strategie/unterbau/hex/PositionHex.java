package de.aasvogel.spiele.strategie.unterbau.hex;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import de.aasvogel.spiele.strategie.unterbau.allgemein.Position;
import de.aasvogel.spiele.strategie.util.DebugSchalter;
import de.aasvogel.spiele.strategie.util.GecoRandom;

public class PositionHex extends Position
{
	private static int smallscaleDebugCounter = 0;
	private final int SMALLSCALEDEBUGNAME = smallscaleDebugCounter++;

	final float nord; // Nach oben
	final float suedWest; // Nach unten links
	final float suedOst; // Nach unten rechts

	private float hauptEntfernung;
	private float abweichung;

	private static final Random rand = GecoRandom.getRandom();

	// Einheitsvektoren:
	private static final Vector3f NORD = new Vector3f(1f, 0f, 0f);
	private static final Vector3f SUEDWEST = new Vector3f(0f, 1f, 0f);
	private static final Vector3f SUEDOST = new Vector3f(0f, 0f, 1f);

	public PositionHex(float nord, float suedWest, float suedOst)
	{
		float min = Math.min(nord, suedWest);
		min = Math.min(min, suedOst);
		this.nord = nord - min;
		this.suedWest = suedWest - min;
		this.suedOst = suedOst - min;
	}

	public float getBreitengrad()
	{
		return nord - 0.5f * (suedWest + suedOst);
	}

	public float getLaengengrad()
	{
		return (float) Math.sqrt(0.75) * (suedWest - suedOst);
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
	public String toString()
	{
		if (DebugSchalter.SMALLSCALEDEBUG)
			return "K: " + SMALLSCALEDEBUGNAME;
		else
			return "w: " + nord + ", x: " + suedWest + ", y: " + suedOst
					+ ", hoehe: " + getHoehe();
	}

	@Override
	public PositionHex getZufallsPositionImUmkreis(float radius)
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

		return createVerschoben(//
				hauptrichtung.x * entfernung + nebenrichtung.x * abweichung, //
				hauptrichtung.y * entfernung + nebenrichtung.y * abweichung, //
				hauptrichtung.z * entfernung + nebenrichtung.z * abweichung);
	}

	private PositionHex createVerschoben(float nord, float suedwest,
			float suedost)
	{
		return new PositionHex(this.nord + nord, //
				this.suedWest + suedwest, //
				this.suedOst + suedost);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(nord);
		result = prime * result + Float.floatToIntBits(suedOst);
		result = prime * result + Float.floatToIntBits(suedWest);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PositionHex))
			return false;
		PositionHex other = (PositionHex) obj;
		if (Float.floatToIntBits(nord) != Float.floatToIntBits(other.nord))
			return false;
		if (Float.floatToIntBits(suedOst) != Float
				.floatToIntBits(other.suedOst))
			return false;
		if (Float.floatToIntBits(suedWest) != Float
				.floatToIntBits(other.suedWest))
			return false;
		return true;
	}
}
