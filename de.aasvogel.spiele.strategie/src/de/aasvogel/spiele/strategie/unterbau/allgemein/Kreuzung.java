package de.aasvogel.spiele.strategie.unterbau.allgemein;

public interface Kreuzung
{
	public abstract boolean istEntfernungZuKreuzungKleiner(Kreuzung kreuzung,
			float entfernung);

	public abstract boolean istInDreieck(Kreuzung... dreieck);

	public abstract void setHoehe(float hoehe);

	public abstract float getHoehe();
}
