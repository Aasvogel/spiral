package de.aasvogel.spiele.strategie.unterbau.allgemein;

public abstract class Position
{
	protected float hoehe = 0;

	public abstract boolean istInDreieck(Position... dreieck);

	public abstract Position getZufallsPositionImUmkreis(float radius);

	public void setHoehe(float hoehe)
	{
		this.hoehe = hoehe;
	};

	public float getHoehe()
	{
		return this.hoehe;
	};

	public boolean istEntfernungZuPositionKleiner(Position position,
			float entfernung)
	{
		float tatsaechlich = Geometrie.berechneEntfernung(this, position);

		return tatsaechlich < entfernung;
	}
}
