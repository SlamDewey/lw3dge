package game.spacial;

import components.math.Vector3f;

/**
 * 
 * A simple 3D (cubic) Axis Aligned Bounding Box
 * 
 * @author Jared Massa
 */
public class AABB {
	public Vector3f center;
	public Vector3f radii;
	
	/**
	 * Creates a new Axis Aligned Bounding Box with center-point 'center', and
	 * distances to faces stored as the 3D vector 'radii'.
	 * 
	 * @param center The 3D center position of this box
	 * @param radii A 3D vector representing the x,y,z distances from center to faces
	 */
	public AABB(Vector3f center, Vector3f radii) {
		this.center = center;
		this.radii = radii;
	}
	
	/**
	 * Checks if the specified point in 3D space is contained within this AABB.
	 * 
	 * @param point The point we check for containment
	 */
	public boolean containsPoint(Vector3f point) {
		return !(
				point.x > center.x + radii.x ||
				point.x < center.x - radii.x ||
				point.y > center.y + radii.y ||
				point.y < center.y - radii.y ||
				point.z > center.z + radii.z ||
				point.z < center.z - radii.z
				);
	}
	/**
	 * Checks if another AABB intersects with this AABB.
	 * 
	 * @param other The other AABB to check intersection against
	 */
	public boolean intersects(AABB other) {
		return !(
				center.x + radii.x <= other.center.x - other.radii.x ||
				center.x - radii.x >= other.center.x + other.radii.x ||
				center.y + radii.y <= other.center.y - other.radii.y ||
				center.y - radii.y >= other.center.y + other.radii.y ||
				center.z + radii.z <= other.center.z - other.radii.z ||
				center.z - radii.z >= other.center.z + other.radii.z
				);
	}
	
}
