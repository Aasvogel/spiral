package de.aasvogel.spiele.strategie.darstellung;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Camera
{
	private static final float HEIGHTMIN = 1f;
	private static final float HEIGHTMAX = 10f;
	private static final float PITCH_TOP = -10;
	private static final float PITCH_BOTTOM = -80;

	private static final float HEIGHTSTEP = 0.03f;

	private Vector3f position = new Vector3f(0, 0, HEIGHTMAX);
	private float pitch = PITCH_TOP;
	private float yaw = 0;
	private float roll = 0;

	public Camera()
	{
	}

	public void move()
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_D)
				|| Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
		{
			position.x += 0.05f;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A)
				|| Keyboard.isKeyDown(Keyboard.KEY_LEFT))
		{
			position.x -= 0.05f;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_W)
				|| Keyboard.isKeyDown(Keyboard.KEY_UP))
		{
			position.y += 0.05f;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S)
				|| Keyboard.isKeyDown(Keyboard.KEY_DOWN))
		{
			position.y -= 0.05f;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)
				|| Keyboard.isKeyDown(Keyboard.KEY_F))
		{
			increaseHeight();
			// position.z += 0.05f;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_ADD)
				|| Keyboard.isKeyDown(Keyboard.KEY_R))
		{
			decreaseHeight();
			// position.z -= 0.05f;
		}

		// if (Keyboard.isKeyDown(Keyboard.KEY_T)
		// || Keyboard.isKeyDown(Keyboard.KEY_T))
		// {
		// pitch -= 0.2f;
		// }
		//
		// if (Keyboard.isKeyDown(Keyboard.KEY_G)
		// || Keyboard.isKeyDown(Keyboard.KEY_G))
		// {
		// pitch += 0.2f;
		// }
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public float getPitch()
	{
		return pitch;
	}

	public float getYaw()
	{
		return yaw;
	}

	public float getRoll()
	{
		return roll;
	}

	public Matrix4f createViewMatrix()
	{
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();

		Vector3f negCameraPos = new Vector3f(-getPosition().x, //
				-getPosition().y, //
				-getPosition().z);

		viewMatrix //
				.rotate(toRad(getPitch()), new Vector3f(1, 0, 0)) //
				.rotate(toRad(getYaw()), new Vector3f(0, 1, 0)) //
				.rotate(toRad(getRoll()), new Vector3f(0, 0, 1)) //
				.translate(negCameraPos);

		return viewMatrix;
	}

	private static float toRad(float degree)
	{
		return (float) Math.toRadians(degree);
	}

	private void calculatePitch()
	{
		float maxHoehenDiff = HEIGHTMAX - HEIGHTMIN;
		float aktHoehenDiff = position.z - HEIGHTMIN;
		float faktor = aktHoehenDiff / maxHoehenDiff;

		pitch = PITCH_BOTTOM + faktor * (PITCH_TOP - PITCH_BOTTOM);
	}

	private void decreaseHeight()
	{
		decreaseHeight(HEIGHTSTEP);
	}

	private void decreaseHeight(float betrag)
	{
		position.z -= betrag;
		if (position.z < HEIGHTMIN)
			position.z = HEIGHTMIN;
		calculatePitch();
	}

	private void increaseHeight()
	{
		increaseHeight(HEIGHTSTEP);
	}

	private void increaseHeight(float betrag)
	{
		position.z += betrag;
		if (position.z > HEIGHTMAX)
			position.z = HEIGHTMAX;
		calculatePitch();
	}
}
