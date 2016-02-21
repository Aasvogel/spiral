package de.aasvogel.spiele.strategie.unterbau.hex;

public class PositionHex
{
	protected final float nord; // Nach oben
	protected final float suedWest; // Nach unten links
	protected final float suedOst; // Nach unten rechts

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
}
