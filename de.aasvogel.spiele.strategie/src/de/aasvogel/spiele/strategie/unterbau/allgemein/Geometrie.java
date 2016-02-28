package de.aasvogel.spiele.strategie.unterbau.allgemein;

public abstract class Geometrie
{
	private static Geometrie geo;

	static Geometrie getGeometrie(Position pos)
	{
		if (geo == null)
			geo = GeometrieFactory.getGeometrie(pos);

		return geo;
	}

	public static boolean kreuzenSich2Wege(Position anfangA, Position endeA,
			Position anfangB, Position endeB)
	{
		return getGeometrie(anfangA).kreuzenSich2WegeDo(anfangA, endeA,
				anfangB, endeB);
	};

	protected abstract boolean kreuzenSich2WegeDo(Position anfangA,
			Position endeA, Position anfangB, Position endeB);

	public static float berechneEntfernung(Position posA, Position posB)
	{
		return getGeometrie(posA).berechneEntfernungDo(posA, posB);
	};

	protected abstract float berechneEntfernungDo(Position posA, Position posB);

	public static boolean istPunktInDreieck(Position punkt, Position... dreieck)
	{
		return getGeometrie(punkt).istPunktInDreieckDo(punkt, dreieck);
	}

	protected abstract boolean istPunktInDreieckDo(Position punkt,
			Position[] dreieck);
}
