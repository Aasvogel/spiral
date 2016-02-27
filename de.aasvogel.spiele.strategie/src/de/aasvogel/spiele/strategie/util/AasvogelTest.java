package de.aasvogel.spiele.strategie.util;

public class AasvogelTest
{
	protected void assertEquals(double soll, double ist, double epsilon)
	{
		if (Math.abs(soll - ist) > epsilon)
		{
			throw new RuntimeException("Soll: " + soll + ", Ist: " + ist + "!");
		}
	}

	protected void assertEquals(Object soll, Object ist)
	{
		if (!soll.equals(ist))
		{
			throw new RuntimeException("Soll: " + soll + ", Ist: " + ist + "!");
		}
	}

	protected void assertEquals(int soll, int ist)
	{
		if (soll != ist)
		{
			throw new RuntimeException("Soll: " + soll + ", Ist: " + ist + "!");
		}
	}

	protected void assertTrue(boolean ist, String text)
	{
		if (!ist)
		{
			throw new RuntimeException(text);
		}
	}

}
