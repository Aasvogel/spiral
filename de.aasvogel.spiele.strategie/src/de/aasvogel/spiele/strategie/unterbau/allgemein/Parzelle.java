package de.aasvogel.spiele.strategie.unterbau.allgemein;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class Parzelle
{
	private static int debugCounter = 0;
	private final int debugName = debugCounter++;

	private final List<Weg> m_grenzen;

	public Parzelle(Weg... grenzen)
	{
		if (grenzen.length != 3)
		{
			throw new IllegalArgumentException(
					"Eine Parzelle muss 3 Grenzen haben!");
		}

		if ((grenzen[0] == grenzen[1]) || (grenzen[1] == grenzen[2])
				|| (grenzen[2] == grenzen[0]))
		{
			throw new IllegalArgumentException(
					"Eine Parzelle muss 3 UNTERSCHIEDLICHE Grenzen haben.");
		}

		m_grenzen = new CopyOnWriteArrayList<Weg>(grenzen);
	}

	public Collection<Weg> getGrenzen()
	{
		return m_grenzen;
	}

	public Set<Kreuzung> getEcken()
	{
		HashSet<Kreuzung> ecken = new HashSet<Kreuzung>();

		for (Weg grenze : m_grenzen)
		{
			ecken.add(grenze.m_kreuzungA);
			ecken.add(grenze.m_kreuzungB);
		}

		return ecken;
	}

	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof Parzelle))
			return false;

		Parzelle fremd = (Parzelle) object;

		for (Weg grenzeFremd : fremd.m_grenzen)
		{
			if (!this.m_grenzen.contains(grenzeFremd))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		// Reihenfolge der Wege muss egal sein!
		int startwert = 28;
		int hashCode = startwert;

		for (Weg grenze : m_grenzen)
		{
			hashCode = hashCode + grenze.hashCode();
		}

		return hashCode;
	}

	@Override
	public String toString()
	{
		return "P: " + debugName;

	}
}
