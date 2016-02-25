package de.aasvogel.spiele.strategie.darstellung.shader;

import org.lwjgl.util.vector.Matrix4f;

import de.aasvogel.spiele.strategie.darstellung.Camera;
import de.aasvogel.spiele.strategie.darstellung.engine.Attributes;

public class StaticShader extends ShaderProgram
{
	private static final String FOLDER = "src/de/aasvogel/spiele/strategie/darstellung/shader/gls/";
	// Nachdehm die einzelnen Grenzen zwischen den "Höhen-Farben" scharf sein
	// sollen und nur von der Höhe abhängig... Müssen wir die Höhenfarbe
	// tatsächlich im Fragment-Shader ermitteln..
	// private static final String VERTEX_FILE = FOLDER + "height_sea.vert";
	// private static final String FRAGMENT_FILE = FOLDER + "border.frag";
	private static final String VERTEX_FILE = FOLDER + "height.vert";
	private static final String FRAGMENT_FILE = FOLDER + "height_sea.frag";
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;

	public StaticShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	public StaticShader(String vertex_file, String fragment_file)
	{
		super(vertex_file, fragment_file);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(Attributes.Positions.number(), "position");
		super.bindAttribute(Attributes.TextureCoords.number(), "textureCoords");
	}

	@Override
	protected void getAllUniformLocations()
	{
		location_transformationMatrix = getUniformLocation("transformationMatrix");
		location_projectionMatrix = getUniformLocation("projectionMatrix");
		location_viewMatrix = getUniformLocation("viewMatrix");
	}

	public void loadTransformationMatrix(Matrix4f matrix)
	{
		loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadProjectionMatrix(Matrix4f matrix)
	{
		loadMatrix(location_projectionMatrix, matrix);
	}

	public void loadViewMatrix(Camera camera)
	{
		Matrix4f viewMatrix = camera.createViewMatrix();
		loadMatrix(location_viewMatrix, viewMatrix);
	}

}
