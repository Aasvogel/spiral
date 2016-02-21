package de.aasvogel.spiele.strategie.util;

import java.util.Random;

public class GecoRandom
{
	private static Random random = DebugSchalter.RandomFest ? new Random(1)
			: new Random();

	public static Random getRandom()
	{
		return random;
	}
}
