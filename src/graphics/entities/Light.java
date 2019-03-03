package graphics.entities;

import components.math.Vector3f;

public class Light {
	public Vector3f position;
	public Vector3f color;

	// some final static helper variables
	public static final Vector3f WHITE = new Vector3f(1, 1, 1);
	public static final Vector3f RED = new Vector3f(1, 0, 0);
	public static final Vector3f GREEN = new Vector3f(0, 1, 0);
	public static final Vector3f BLUE = new Vector3f(0, 0, 1);

	/**
	 * Creates a new Light with the specified Position and Color
	 * 
	 * @param position
	 *            the 3D position of the Light
	 * @param color
	 *            the RGB color value of the light (scale from 0 to 1 not 0 to
	 *            256)
	 */
	public Light(Vector3f position, Vector3f color) {
		this.position = position;
		this.color = color;
	}
}
