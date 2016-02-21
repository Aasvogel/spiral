package de.aasvogel.spiele.strategie.unterbau.allgemein;

import java.util.ArrayList;
import java.util.List;

public abstract class Karte
{
	private KartenVerweise verweise = new KartenVerweise();
	private List<Segment> segmente = new ArrayList<Segment>();
}
