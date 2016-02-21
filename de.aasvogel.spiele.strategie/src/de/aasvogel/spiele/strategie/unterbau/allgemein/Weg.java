package de.aasvogel.spiele.strategie.unterbau.allgemein;

public class Weg
{
	private static int debugcounter = 0;
	private final int debugName = debugcounter++;

	public final Kreuzung m_kreuzungA;
	public final Kreuzung m_kreuzungB;

	public Weg(Kreuzung kreuzungA, Kreuzung kreuzungB)
	{
		if (kreuzungA == kreuzungB)
		{
			throw new IllegalArgumentException(
					"Ein Weg braucht zwei unterschiedliche Endpunkte");
		}

		m_kreuzungA = kreuzungA;
		m_kreuzungB = kreuzungB;
	}

	public boolean istLaengeKleinerAls(float laenge)
	{
		return m_kreuzungA.istEntfernungZuKreuzungKleiner(m_kreuzungB, laenge);
	}

	public float get_laenge()
	{
		return m_kreuzungA.berechneEntfernungZuKreuzung(m_kreuzungB);
	}

	public boolean kreuzenSichDieWege(Weg weg)
	{
		if (weg.m_kreuzungA == this.m_kreuzungA
				|| weg.m_kreuzungB == this.m_kreuzungB
				|| weg.m_kreuzungB == this.m_kreuzungA
				|| weg.m_kreuzungA == this.m_kreuzungB)
			return false;
		return this.m_kreuzungA.kreuzenSich2Wege(this.m_kreuzungA,
				this.m_kreuzungB, weg.m_kreuzungA, weg.m_kreuzungB);
	}

	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof Weg))
			return false;

		Weg weg = (Weg) object;

		if ((weg.m_kreuzungA == this.m_kreuzungA)
				&& (weg.m_kreuzungB == this.m_kreuzungB))
			return true;
		if ((weg.m_kreuzungA == this.m_kreuzungB)
				&& (weg.m_kreuzungB == this.m_kreuzungA))
			return true;

		return false;
	}

	@Override
	public int hashCode()
	{
		// Reihenfolge der Kreuzungen muss egal sein!

		int primeFactor = 29;
		int startwert = 28;
		int hashCode = startwert;

		int hashKleiner = Math.min(m_kreuzungA.hashCode(),
				m_kreuzungB.hashCode());
		int hashGroesser = Math.max(m_kreuzungA.hashCode(),
				m_kreuzungB.hashCode());

		hashCode = hashCode * primeFactor + hashKleiner;
		hashCode = hashCode * primeFactor + hashGroesser;

		return hashCode;
	}

	public Kreuzung getAndereKreuzung(Kreuzung a)
	{
		if (a == m_kreuzungA)
			return m_kreuzungB;
		else if (a == m_kreuzungB)
			return m_kreuzungA;
		else
			throw new IllegalArgumentException("Kreuzung " + a
					+ " ist nicht Teil des Weges " + this + "!");
	}

	@Override
	public String toString()
	{
		return "W: " + debugName + ". [" + m_kreuzungA + " - " + m_kreuzungB
				+ "] ";
	}
}
