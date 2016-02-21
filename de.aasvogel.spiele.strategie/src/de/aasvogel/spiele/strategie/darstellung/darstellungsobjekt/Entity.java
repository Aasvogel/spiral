package de.aasvogel.spiele.strategie.darstellung.darstellungsobjekt;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Entity
{
	private TexturedModel model;
	private Vector3f position;
	private Vector3f rotation;
	private float scale;

	public Entity(TexturedModel model, Vector3f position, float rotX,
			float rotY, float rotZ, float scale)
	{
		super();
		this.model = model;
		this.position = position;
		this.rotation = new Vector3f();
		this.rotation.x = rotX;
		this.rotation.y = rotY;
		this.rotation.z = rotZ;
		this.scale = scale;
	}

	public void increasePosition(float dx, float dy, float dz)
	{
		position.x += dx;
		position.y += dy;
		position.z += dz;
	}

	public void increaseRotation(float dx, float dy, float dz)
	{
		rotation.x += dx;
		rotation.y += dy;
		rotation.z += dz;
	}

	public TexturedModel getModel()
	{
		return model;
	}

	public void setModel(TexturedModel model)
	{
		this.model = model;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public float getScale()
	{
		return scale;
	}

	public void setScale(float scale)
	{
		this.scale = scale;
	}

	public Matrix4f createTransformationMatrix()
	{
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		matrix.translate(position)//
				.rotate(toRad(rotation.x), new Vector3f(1, 0, 0)) //
				.rotate(toRad(rotation.y), new Vector3f(0, 1, 0)) //
				.rotate(toRad(rotation.z), new Vector3f(0, 0, 1)) //
				.scale(new Vector3f(scale, scale, scale));

		return matrix;
	}

	private static float toRad(float degree)
	{
		return (float) Math.toRadians(degree);
	}

}
