package de.aasvogel.spiele.strategie.unterbau.allgemein;

public interface Kreuzung
{
	public abstract boolean istEntfernungZuKreuzungKleiner(Kreuzung kreuzung,
			float entfernung);

	public abstract float berechneEntfernungZuKreuzung(Kreuzung kreuzung);

	// Nicht static weil static abstract nicht funktioniert.
	public abstract boolean kreuzenSich2Wege(Kreuzung anfangA, Kreuzung endeA,
			Kreuzung anfangB, Kreuzung endeB);

	public abstract boolean istInDreieck(Kreuzung... dreieck);

	public abstract void setHoehe(float hoehe);

	public abstract float getHoehe();
}
