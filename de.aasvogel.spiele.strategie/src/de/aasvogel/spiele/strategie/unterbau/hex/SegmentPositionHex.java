package de.aasvogel.spiele.strategie.unterbau.hex;

import java.util.Iterator;

import de.aasvogel.spiele.strategie.unterbau.allgemein.Konstanten;
import de.aasvogel.spiele.strategie.unterbau.allgemein.Position;
import de.aasvogel.spiele.strategie.unterbau.allgemein.SegmentPosition;

public class SegmentPositionHex extends SegmentPosition
{
	public final long east;
	public final long northWest;
	public final long southWest;

	public SegmentPositionHex(long east, long northWest, long southWest)
	{
		long min = Math.min(east, northWest);
		min = Math.min(min, southWest);

		this.east = east - min;
		this.northWest = northWest - min;
		this.southWest = southWest - min;
	}

	@Override
	public long getDistanzZu(SegmentPosition pos)
	{
		return getDistanzZu((SegmentPositionHex) pos);
	}

	private long getDistanzZu(SegmentPositionHex pos)
	{
		SegmentPositionHex diff = new SegmentPositionHex(//
				this.east - pos.east, //
				this.northWest - pos.northWest, //
				this.southWest - pos.southWest);

		long max = Math.max(diff.east, diff.northWest);
		return Math.max(max, diff.southWest);
	}

	public SegmentPositionHex add(long east2, long northWest2, long southWest2)
	{
		return new SegmentPositionHex(//
				this.east + east2, //
				this.northWest + northWest2, //
				this.southWest + southWest2);
	}

	public static class SegPosHexIterator implements
			Iterator<SegmentPositionHex>
	{
		// Die reihenfolge lautet:
		// von innen nach außen. Angenfangen in East.

		private final SegmentPositionHex mittelpunkt;
		private final int radiusBis;

		private int aktRadius;
		private SegmentPositionHex next = null;

		public SegPosHexIterator(SegmentPositionHex mittelpunkt, int radiusVon,
				int radiusBis)
		{
			this.mittelpunkt = mittelpunkt;
			this.radiusBis = radiusBis;

			if (radiusBis >= radiusVon)
			{
				startRundlauf(radiusVon);
			}
		}

		private void startRundlauf(int radius)
		{
			this.next = new SegmentPositionHex(radius, 0, 0);
			aktRadius = radius;
		}

		@Override
		public boolean hasNext()
		{
			return next != null;
		}

		@Override
		public SegmentPositionHex next()
		{
			SegmentPositionHex ausgabe = mittelpunkt.add(next.east,
					next.northWest, next.southWest);

			weiterschieben();

			return ausgabe;
		}

		private void weiterschieben()
		{
			// East -> North-East:
			if (next.southWest == 0 //
					&& next.east == aktRadius //
					&& next.northWest < aktRadius)
				next = next.add(0, 1, 0);
			// North-East -> North-West
			else if (next.southWest == 0 //
					&& next.northWest == aktRadius //
					&& next.east > 0)
				next = next.add(-1, 0, 0);
			// North-West -> West
			else if (next.east == 0 //
					&& next.northWest == aktRadius //
					&& next.southWest < aktRadius)
				next = next.add(0, 0, 1);
			// West -> Sout-West
			else if (next.east == 0 //
					&& next.southWest == aktRadius//
					&& next.northWest > 0)
				next = next.add(0, -1, 0);
			// South-West -> South-East
			else if (next.northWest == 0 //
					&& next.southWest == aktRadius //
					&& next.east < aktRadius)
				next = next.add(1, 0, 0);
			// South-East -> East
			else if (next.northWest == 0 //
					&& next.east == aktRadius //
					&& next.southWest > 0)
				next = next.add(0, 0, -1);

			if (next.east == aktRadius //
					&& next.southWest == 0 //
					&& next.northWest == 0)
			{
				// Wir sind einmal rum.
				if (aktRadius < radiusBis)
					startRundlauf(++aktRadius);
				else
					next = null;
			}
		}
	}

	@Override
	public Position toPosition()
	{
		float radius = Konstanten.SEGMENTGROESSE;
		float nord = northWest * radius - southWest * radius;
		float suedWest = southWest * radius - east * radius;
		float suedOst = east * radius - northWest * radius;

		return new PositionHex(nord, suedWest, suedOst);
	}
}
