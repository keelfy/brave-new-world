package com.remote.remote2d.engine.logic;

public class Collision {

	public double lengthSquared = -1.0d;
	public Vector2 correction = new Vector2(0, 0);
	public boolean collides = false;
	public boolean finishedCalculating = false;

	public int min = -1;
	public int max = -1;

	public boolean isCollides() {
		return collides;
	}

	/**
	 * Returns absolute value of correction X
	 */
	public float getCorrectionX() {
		return correction.abs().x;
	}

	/**
	 * Returns absolute value of correction Y
	 */
	public float getCorrectionY() {
		return correction.abs().y;
	}

}
