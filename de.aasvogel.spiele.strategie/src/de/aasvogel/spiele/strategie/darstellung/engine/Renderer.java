package de.aasvogel.spiele.strategie.darstellung.engine;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.Entity;
import de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.RawModel;
import de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt.TexturedModel;
import de.aasvogel.spiele.strategie.darstellung.shader.StaticShader;

public class Renderer
{
	private static final float FIELDOFVIEW = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;

	private static Matrix4f projectionMatrix = createProjectionMatrix();

	private static Matrix4f createProjectionMatrix()
	{
		// Mathemagie! funktioniert halt so.
		float aspectRatio = (float) Display.getWidth()
				/ (float) Display.getHeight();
		float x_scale = (float) (1f / Math
				.tan(Math.toRadians(FIELDOFVIEW / 2f)));
		float y_scale = x_scale * aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		Matrix4f matrix = new Matrix4f();
		matrix.m00 = x_scale;
		matrix.m11 = y_scale;
		matrix.m22 = -((FAR_PLANE - NEAR_PLANE) / frustum_length);
		matrix.m23 = -1;
		matrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		matrix.m33 = 0;
		return matrix;
	}

	public Renderer(StaticShader... shaders)
	{
		for (StaticShader shader : shaders)
		{
			shader.start();
			shader.loadProjectionMatrix(projectionMatrix);
			shader.stop();
		}
	}

	public void prepare()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0, 0, 1, 1);
	}

	public void render(Entity entity, StaticShader shader)
	{
		TexturedModel texturedModel = entity.getModel();
		RawModel rawModel = texturedModel.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());

		for (Attributes attr : Attributes.values())
		{
			GL20.glEnableVertexAttribArray(attr.number());
		}

		shader.loadTransformationMatrix(entity.createTransformationMatrix());

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture()
				.getID());

		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(),
				GL11.GL_UNSIGNED_INT, 0);

		for (Attributes attr : Attributes.values())
		{
			GL20.glDisableVertexAttribArray(attr.number());
		}

		GL30.glBindVertexArray(0);
	}
}
