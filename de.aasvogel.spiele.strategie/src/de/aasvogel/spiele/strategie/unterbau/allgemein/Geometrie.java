package de.aasvogel.spiele.strategie.unterbau.allgemein;

public abstract class Geometrie
{
	private static Geometrie geo;

	public static void setGeometrie(Geometrie geometrie)
	{
		if (geo != null)
			throw new IllegalStateException("Geometry is already set.");
		geo = geometrie;
	}

	public static Geometrie getGeometrie()
	{
		if (geo == null)
			throw new IllegalStateException("Geometry not jet set.");

		return geo;
	}

	public abstract boolean kreuzenSich2Wege(Kreuzung anfangA, Kreuzung endeA,
			Kreuzung anfangB, Kreuzung endeB);

	public abstract float berechneEntfernungZwischenKreuzungen(
			Kreuzung kreuzungA, Kreuzung kreuzungB);

}
