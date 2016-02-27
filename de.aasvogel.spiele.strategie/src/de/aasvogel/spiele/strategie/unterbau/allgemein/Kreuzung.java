package de.aasvogel.spiele.strategie.unterbau.allgemein;

public class Kreuzung
{
	private Position position;

	public Position getPosition()
	{
		return position;
	}

	public Kreuzung(Position position)
	{
		this.position = position;
	}

	public boolean istEntfernungKleiner(Position position, float entfernung)
	{
		return this.position.istEntfernungZuPositionKleiner(position,
				entfernung);
	};

	public void setHoehe(float hoehe)
	{
		position.setHoehe(hoehe);
	}

	public float getHoehe()
	{
		return position.getHoehe();
	};

	@Override
	public String toString()
	{
		return "Kreuzung bei " + position;
	}
}
