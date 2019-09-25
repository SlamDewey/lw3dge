package lw3dge.components;

import lw3dge.components.math.Vector3f;

public class CubicBounds {
	public Vector3f center;
	public float width;
	private float xMin, xMax, yMin, yMax, zMin, zMax;

	public CubicBounds(Vector3f center, float width) {
		this.center = center;
		this.width = width;
		xMin = center.x - width;
		xMax = center.x + width;
		yMin = center.y - width;
		yMax = center.y + width;
		zMin = center.z - width;
		zMax = center.z + width;
	}

	public boolean contains(Vector3f point) {
		return (xMin <= point.x && xMax >= point.x && yMin <= point.y && yMax >= point.y && zMin <= point.z
				&& zMax >= point.z);
	}

	public boolean intersects(CubicBounds other) {
		Vector3f centerDistance = Vector3f.sub(other.center, center, null);
		return (centerDistance.length() <= width + other.width);
	}
}