package de.aasvogel.spiele.strategie.unterbau.allgemein;

public abstract class SegmentPosition
{
	public abstract long getDistanzZu(SegmentPosition pos);

	public abstract Position toPosition();
}
