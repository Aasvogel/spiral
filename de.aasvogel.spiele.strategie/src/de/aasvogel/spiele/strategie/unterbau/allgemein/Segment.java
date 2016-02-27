package de.aasvogel.spiele.strategie.unterbau.allgemein;

import java.util.*;

import de.aasvogel.spiele.strategie.util.GecoRandom;

public class Segment
{
	protected ArrayList<Kreuzung> kreuzungen = new ArrayList<Kreuzung>();
	protected List<Weg> wege = new ArrayList<Weg>();
	protected List<Weg> verbindungsStrassen = new ArrayList<Weg>();
	protected List<Parzelle> parzellen = new ArrayList<Parzelle>();
	protected List<Parzelle> niemandsLand = new ArrayList<Parzelle>();

	private final KartenVerweise kartenVerweise;

	public final SegmentPosition position;
	public final Position mittelpunkt;

	public Segment(SegmentPosition segPos, KartenVerweise verweise)
	{
		this.mittelpunkt = segPos.toPosition();
		this.position = segPos;
		this.kartenVerweise = verweise;
	}

	public void fillRandomly(Segment... nachbarn)
	{
		clear();

		fuelleKreuzungen(Konstanten.VERSUCHE, nachbarn);
		// System.out.println("Kreuzungen:    " + kreuzungen.size());
		fuelleWege(nachbarn);
		// System.out.println("Wege:          " + wege.size());
		// System.out.println("Verbindungen:  " + verbindungsStrassen.size());
		// entferneSackgassen();
		// System.out.println("Wege2:         " + wege.size());
		ermittleParzellen(nachbarn);
		// System.out.println("Parzellen:     " + parzellen.size());
		// System.out.println("Niemandsland:  " + niemandsLand.size());
		ermittleHoehe();
	}

	private void clear()
	{
		clearCollection(niemandsLand);
		clearCollection(parzellen);
		clearCollection(verbindungsStrassen);
		clearCollection(wege);
		clearCollection(kreuzungen);
	}

	private void clearCollection(
			@SuppressWarnings("rawtypes") Collection collection)
	{
		for (Object element : collection)
			kartenVerweise.remove(element);
		collection.clear();
	}

	private void ermittleParzellen(Segment[] nachbarn)
	{
		for (Kreuzung punkt1 : kreuzungen)
		{
			for (Weg weg12 : kartenVerweise.getWegeZuKreuzung(punkt1))
			{
				Kreuzung punkt2 = weg12.getAndereKreuzung(punkt1);

				for (Weg weg23 : kartenVerweise.getWegeZuKreuzung(punkt2))
				{
					Kreuzung punkt3 = weg23.getAndereKreuzung(punkt2);

					for (Weg weg31 : kartenVerweise.getWegeZuKreuzung(punkt3))
					{
						Kreuzung punkt4 = weg31.getAndereKreuzung(punkt3);

						if (punkt4 == punkt1)
						{
							testAndAddParzelle(nachbarn, punkt1, weg12, punkt2,
									weg23, punkt3, weg31);
						}
					}
				}
			}
		}
	}

	private void testAndAddParzelle(Segment[] nachbarn, Kreuzung punkt1,
			Weg weg12, Kreuzung punkt2, Weg weg23, Kreuzung punkt3, Weg weg31)
	{
		if (!befindetSichKreuzungInDreieck(punkt1.getPosition(),
				punkt2.getPosition(), punkt3.getPosition(), nachbarn))
		{
			Parzelle parzelle = new Parzelle(weg12, weg23, weg31);

			if (kreuzungen.contains(punkt1) && kreuzungen.contains(punkt2)
					&& kreuzungen.contains(punkt3))
			{
				if (!parzellen.contains(parzelle))
				{
					parzellen.add(parzelle);
					kartenVerweise.add(parzelle);
				}
			} else
			{
				if (!niemandsLand.contains(parzelle))
				{
					niemandsLand.add(parzelle);
					kartenVerweise.add(parzelle);
				}

			}
		}
	}

	private boolean befindetSichKreuzungInDreieck(Position a, Position b,
			Position c, Segment[] nachbarn)
	{
		for (Kreuzung kreuzung : kreuzungen)
		{
			Position punkt = kreuzung.getPosition();
			if (punkt != a && punkt != b && punkt != c
					&& Geometrie.istPunktInDreieck(punkt, a, b, c))
			{
				return true;
			}
		}
		for (Segment nachbar : nachbarn)
		{
			for (Kreuzung kreuzung : nachbar.kreuzungen)
			{
				Position punkt = kreuzung.getPosition();
				if (punkt != a && punkt != b && punkt != c
						&& Geometrie.istPunktInDreieck(punkt, a, b, c))
				{
					return true;
				}
			}
		}
		return false;
	}

	protected void fuelleKreuzungen(int anzahlVersuche, Segment[] nachbarn)
	{
		int gesamtAnzahl = (int) (3 * Konstanten.SEGMENTGROESSE * Konstanten.SEGMENTGROESSE);
		// System.out.println("Ziel: " + gesamtAnzahl);

		for (int i = 0; i < gesamtAnzahl * anzahlVersuche; i++)
		{
			Position pos = mittelpunkt
					.getZufallsPositionImUmkreis(Konstanten.SEGMENTGROESSE);

			if (istNeueKreuzungZulaessig(pos, nachbarn))
				kreuzungen.add(new Kreuzung(pos));
		}
	}

	protected boolean istNeueKreuzungZulaessig(Position position,
			Segment[] nachbarn)
	{
		for (Kreuzung kreuzung : kreuzungen)
		{
			if (kreuzung.istEntfernungKleiner(position,
					Konstanten.MIN_ENTFERNUNG_ZWISCHEN_PUNKTEN))
				return false;
		}

		for (Segment nachbar : nachbarn)
		{
			for (Kreuzung kreuzung : nachbar.kreuzungen)
			{
				if (kreuzung.istEntfernungKleiner(position,
						Konstanten.MIN_ENTFERNUNG_ZWISCHEN_PUNKTEN))
					return false;
			}
		}
		return true;
	}

	public void fuelleWege(Segment... nachbarn)
	{
		Weg neuerWeg = null;
		do
		{
			neuerWeg = findShortestAdmissibleWeg(nachbarn);
			if (neuerWeg != null)
			{
				if (kreuzungen.contains(neuerWeg.m_kreuzungA)
						&& kreuzungen.contains(neuerWeg.m_kreuzungB))
				{
					wege.add(neuerWeg);
				} else
				{
					verbindungsStrassen.add(neuerWeg);
				}
				kartenVerweise.add(neuerWeg);
			}

		} while (neuerWeg != null);
	}

	// private void entferneSackgassen()
	// {
	// Kreuzung zuEntfernen;
	// do
	// {
	// zuEntfernen = findeKreuzungZuEntfernen();
	//
	// if (zuEntfernen != null)
	// {
	// Set<Weg> wegeZuKreuzung = kartenVerweise
	// .getWegeZuKreuzung(zuEntfernen);
	//
	// for (Weg weg : wegeZuKreuzung)
	// {
	// wege.remove(weg);
	// kartenVerweise.remove(weg);
	// }
	// kreuzungen.remove(zuEntfernen);
	// kartenVerweise.remove(zuEntfernen);
	// }
	// } while (zuEntfernen != null);
	// }

	// private Kreuzung findeKreuzungZuEntfernen()
	// {
	// for (Kreuzung kreuzung : kreuzungen)
	// {
	// if (kartenVerweise.getWegeZuKreuzung(kreuzung).size() < 2)
	// return kreuzung;
	// }
	// return null;
	// }

	private boolean istWegZulaessigUndNeu(Weg weg, Segment... nachbarn)
	{
		if (wege.contains(weg) || verbindungsStrassen.contains(weg))
			return false;

		if (kreuzungInSegment(weg, this))
			return false;

		for (Segment nachbar : nachbarn)
		{
			if (kreuzungInSegment(weg, nachbar))
				return false;
		}

		return true;
	}

	private static boolean kreuzungInSegment(Weg weg, Segment segment)
	{
		for (Weg bestehend : segment.wege)
		{
			if (bestehend.kreuzenSichDieWege(weg))
				return true;
		}

		for (Weg bestehend : segment.verbindungsStrassen)
		{
			if (bestehend.kreuzenSichDieWege(weg))
				return true;
		}

		return false;
	}

	private Weg findShortestAdmissibleWeg(Segment[] nachbarn)
	{
		Wegmerker merker = new Wegmerker();
		merker.laenge = Konstanten.MAX_LAENGE_WEG;

		for (Kreuzung start : kreuzungen)
		{
			findeNaehesteKreuzungInSegment(start, this, merker, nachbarn);

			for (Segment nachbar : nachbarn)
			{
				findeNaehesteKreuzungInSegment(start, nachbar, merker, nachbarn);
			}
		}

		return merker.gemerkt;
	}

	private void findeNaehesteKreuzungInSegment(Kreuzung start,
			Segment segment, Wegmerker merker, Segment... nachbarn)
	{
		for (Kreuzung ende : segment.kreuzungen)
		{
			if (start != ende)
			{
				if (start.istEntfernungKleiner(ende.getPosition(),
						merker.laenge))
				{
					Weg weg = new Weg(start, ende);

					if (istWegZulaessigUndNeu(weg, nachbarn))
					{
						merker.gemerkt = weg;
						merker.laenge = weg.get_laenge();
					}
				}
			}
		}
	}

	protected void ermittleHoehe()
	{
		Set<Kreuzung> done = new HashSet<Kreuzung>();

		for (Kreuzung akt : kreuzungen)
		{
			if (kartenVerweise.gehoertKreuzungZuAusengrenze(akt))
			{
				akt.setHoehe(Konstanten.HOEHE_MARIANNENGRABEN);
				done.add(akt);
			}
		}

		ermittleHoehe_Gebirge(done);

		ermittleHoehe_Ausgleich(done);
	}

	private void ermittleHoehe_Ausgleich(Set<Kreuzung> done)
	{
		for (int i = 0; i < 10; i++)
		{
			for (Kreuzung kreuzung : kreuzungen)
			{
				if (!done.contains(kreuzung))
					doHoehenausgleich(kreuzung);
			}
		}
	}

	private void doHoehenausgleich(Kreuzung kreuzung)
	{
		float hoeheSumme = 0;
		for (Weg weg : kartenVerweise.getWegeZuKreuzung(kreuzung))
		{
			hoeheSumme += weg.getAndereKreuzung(kreuzung).getHoehe();
		}

		float hoeheUmgebung = hoeheSumme
				/ kartenVerweise.getWegeZuKreuzung(kreuzung).size();

		float hoeheSelbst = kreuzung.getHoehe();

		float hoeheZufall = GecoRandom.getRandom().nextFloat()
				* (Konstanten.HOEHE_EVEREST - Konstanten.HOEHE_MARIANNENGRABEN)
				+ Konstanten.HOEHE_MARIANNENGRABEN;

		kreuzung.setHoehe(0.4f * hoeheSelbst + 0.4f * hoeheUmgebung + 0.2f
				* hoeheZufall);
	}

	private void ermittleHoehe_Gebirge(Set<Kreuzung> done)
	{
		Iterator<Kreuzung> iterator = kreuzungen.iterator();

		Kreuzung gipfel = iterator.next();
		while (done.contains(gipfel) && iterator.hasNext())
			gipfel = iterator.next();
		gipfel.setHoehe(Konstanten.HOEHE_EVEREST);
		done.add(gipfel);
	}

	public List<Weg> getWege()
	{
		return wege;
	}

	private static class Wegmerker
	{
		public Weg gemerkt;
		public float laenge;
	}

	public List<Parzelle> getParzellen()
	{
		return parzellen;
	}

	public List<Parzelle> getNiemandsLand()
	{
		return niemandsLand;
	}
}
