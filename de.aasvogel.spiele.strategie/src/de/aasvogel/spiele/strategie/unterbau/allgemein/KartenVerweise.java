package de.aasvogel.spiele.strategie.unterbau.allgemein;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KartenVerweise
{
	private Map<Kreuzung, Set<Weg>> wegeZuKreuzungen = new HashMap<Kreuzung, Set<Weg>>();
	private Map<Weg, Set<Parzelle>> parzellenZuWegen = new HashMap<Weg, Set<Parzelle>>();

	public void add(Weg weg)
	{
		addWeg2Kreuzung(weg, weg.m_kreuzungA);
		addWeg2Kreuzung(weg, weg.m_kreuzungB);
	}

	private void addWeg2Kreuzung(Weg weg, Kreuzung kreuzung)
	{
		if (!wegeZuKreuzungen.containsKey(kreuzung))
		{
			wegeZuKreuzungen.put(kreuzung, new HashSet<Weg>());
		}
		wegeZuKreuzungen.get(kreuzung).add(weg);
		// keine Prüfung weil von einer Kreuzung beliebig viele Wege abgehen
		// dürfen.
	}

	public void add(Parzelle parzelle)
	{
		for (Weg grenze : parzelle.getGrenzen())
		{
			if (!parzellenZuWegen.containsKey(grenze))
			{
				parzellenZuWegen.put(grenze, new HashSet<Parzelle>());
			}
			parzellenZuWegen.get(grenze).add(parzelle);
			// Es kann pro Weg maximal zwei Parzellen geben: // TODO
			if (parzellenZuWegen.get(grenze).size() > 2)
			{
				throw new RuntimeException(
						"Ein Weg kann nur zwei angrenzende Parzellen haben! "
								+ "Weg: " + grenze + "  Parzellen: "
								+ parzellenZuWegen.get(grenze));
			}
		}
	}

	public void remove(Object object)
	{
		if (object instanceof Kreuzung)
			remove((Kreuzung) object);
		else if (object instanceof Weg)
			remove((Weg) object);
		else if (object instanceof Parzelle)
			remove((Parzelle) object);
		else
			throw new IllegalArgumentException(
					"Es sind nur objekte folgender Klassen erlaubt: "
							+ Kreuzung.class + ", " + Weg.class + ", "
							+ Parzelle.class + ".");
	}

	public void remove(Kreuzung kreuzung)
	{
		wegeZuKreuzungen.remove(kreuzung);
	}

	public void remove(Weg weg)
	{
		for (Set<Weg> wege : wegeZuKreuzungen.values())
		{
			wege.remove(weg);
		}

		parzellenZuWegen.remove(weg);
	}

	public void remove(Parzelle parzelle)
	{
		for (Set<Parzelle> parzellen : parzellenZuWegen.values())
		{
			parzellen.remove(parzelle);
		}
	}

	public Set<Weg> getWegeZuKreuzung(Kreuzung kreuzung)
	{
		return wegeZuKreuzungen.get(kreuzung);
	}

	public Set<Parzelle> getParzellenZuWeg(Weg weg)
	{
		return parzellenZuWegen.get(weg);
	}

	public boolean gehoertKreuzungZuAusengrenze(Kreuzung kreuzung)
	{
		for (Weg weg : getWegeZuKreuzung(kreuzung))
		{
			if (istWegAusengrenze(weg))
				return true;
		}
		return false;
	}

	public boolean istWegAusengrenze(Weg weg)
	{
		Set<Parzelle> parzellen = getParzellenZuWeg(weg);
		return parzellen == null || parzellen.size() < 2;
	}

	public boolean liegtParzelleAnAusengrenze(Parzelle parzelle)
	{
		for (Weg grenze : parzelle.getGrenzen())
		{
			if (istWegAusengrenze(grenze))
				return true;
		}
		return false;
	}

}
