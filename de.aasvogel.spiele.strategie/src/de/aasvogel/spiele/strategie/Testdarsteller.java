package de.aasvogel.spiele.strategie;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import de.aasvogel.spiele.strategie.darstellung.Camera;
import de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.Entity;
import de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.karte.hex.MeerDarstellungHex;
import de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.karte.hex.ParzelleDarstellungHex;
import de.aasvogel.spiele.strategie.darstellung.engine.DisplayManager;
import de.aasvogel.spiele.strategie.darstellung.engine.Loader;
import de.aasvogel.spiele.strategie.darstellung.engine.Renderer;
import de.aasvogel.spiele.strategie.darstellung.shader.FogShader;
import de.aasvogel.spiele.strategie.darstellung.shader.StaticShader;
import de.aasvogel.spiele.strategie.unterbau.allgemein.KartenVerweise;
import de.aasvogel.spiele.strategie.unterbau.allgemein.Parzelle;
import de.aasvogel.spiele.strategie.unterbau.hex.SegmentHex;
import de.aasvogel.spiele.strategie.unterbau.hex.SegmentPositionHex;

public class Testdarsteller
{
	private static StaticShader shader;
	private static FogShader fogshader;
	private static Renderer renderer;
	private static Camera camera;

	private static List<Entity> entitaeten = new ArrayList<Entity>();
	private static List<Entity> meer = new ArrayList<Entity>();

	public static void main(String[] args)
	{
		List<SegmentHex> segmente = karteVorbereiten();

		DisplayManager.createDisplay();

		for (SegmentHex segment : segmente)
		{
			for (Parzelle parzelle : segment.getParzellen())
			{
				entitaeten.add(new ParzelleDarstellungHex(parzelle).load());
			}

			for (Parzelle parzelle : segment.getNiemandsLand())
			{
				entitaeten.add(new ParzelleDarstellungHex(parzelle).load());
			}
			for (float i = -0.6f; i <= 0.05; i = i + 0.05f)
			{
				meer.add(new MeerDarstellungHex(i, segment.mittelpunkt).load());
			}
		}

		shader = new StaticShader();
		fogshader = new FogShader();

		renderer = new Renderer(shader, fogshader);
		camera = new Camera();

		try
		{
			while (!Display.isCloseRequested())
			{
				displayUpdaten();
			}
		} finally
		{
			aufraeumen(shader);
		}
	}

	private static List<SegmentHex> karteVorbereiten()
	{
		KartenVerweise verweise = new KartenVerweise();
		List<SegmentHex> segmente = new ArrayList<SegmentHex>();
		SegmentHex seg1 = new SegmentHex(new SegmentPositionHex(0, 0, 0),
				verweise);
		seg1.fillRandomly();
		segmente.add(seg1);

		SegmentHex seg2 = new SegmentHex(new SegmentPositionHex(1, 0, 0),
				verweise);
		seg2.fillRandomly(seg1);
		segmente.add(seg2);
		SegmentHex seg3 = new SegmentHex(new SegmentPositionHex(0, 1, 0),
				verweise);
		seg3.fillRandomly(seg1, seg2);
		segmente.add(seg3);
		return segmente;
	}

	private static void displayUpdaten()
	{
		camera.move();
		renderer.prepare();
		shader.start();
		shader.loadViewMatrix(camera);
		for (Entity entity : entitaeten)
		{
			renderer.render(entity, shader);
		}
		shader.stop();

		fogshader.start();
		fogshader.loadViewMatrix(camera);
		for (Entity entity : meer)
		{
			renderer.render(entity, fogshader);
		}

		fogshader.stop();

		DisplayManager.updateDisplay();
	}

	// private static KarteHex karteVorbereiten()
	// {
	// KarteHex karte = new KarteHex(RADIUS);
	//
	// karte.fillRandoly();
	//
	// return karte;
	// }

	private static void aufraeumen(StaticShader shader)
	{
		shader.cleanUp();
		Loader.getLoader().cleanUp();
		DisplayManager.closeDisplay();
	}
}