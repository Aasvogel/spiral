package de.aasvogel.spiele.strategie;

import java.util.Random;

import de.aasvogel.spiele.strategie.util.GecoRandom;

public class Fehlersucher
{
	private static Random rand = GecoRandom.getRandom();

	// private static final float RADIUS = 3f;

	public static void main(String[] args)
	{
		searchrun();
		single(39);
	}

	private static void searchrun()
	{
		for (int i = 0; i < 1000; i++)
		{
			System.out.println("Seed: " + i + " ---------------------");
			rand.setSeed(i);
			// KarteHex karte = new KarteHex(RADIUS);
			// karte.fillRandoly();
		}

		System.out.println("XXX DONE - Search XXX");
	}

	private static void single(int seed)
	{
		rand.setSeed(seed);

		// KarteHex karte = new KarteHex(RADIUS);
		// karte.fillRandoly();

		System.out.println("XXX DONE - Single XXX");
	}
}
