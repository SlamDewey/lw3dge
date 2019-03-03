package components.physics;

import components.Updatable;
import components.math.Matrix4f;
import components.math.Vector3f;
import components.math.Vector4f;

/**
 * 
 * This class implements a 3D object transform I am writing this to abstract
 * Transform components from graphical entities, and allow inclusion in all
 * entities.
 * 
 * @author Jared Massa
 */
public class Transform implements Updatable {

	public Vector3f position;
	public Vector3f velocity;

	// to generate an acceleration, add the force.
	private Vector3f acceleration;
	public Vector3f force;

	public Vector3f rotation;
	public Vector3f rotational_velocity;
	// objects with no mass will experience no acceleration
	public float mass = 0;

	public Transform() {
		this.position = new Vector3f(0, 0, 0);
		this.velocity = new Vector3f(0, 0, 0);
		this.acceleration = new Vector3f(0, 0, 0);
		this.force = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		this.rotational_velocity = new Vector3f(0, 0, 0);
	}

	public Transform(Vector3f position, float mass) {
		this.position = position;
		this.velocity = new Vector3f();
		this.acceleration = new Vector3f();
		this.force = new Vector3f();
		this.rotation = new Vector3f();
		this.rotational_velocity = new Vector3f();
		this.mass = mass;
	}

	/**
	 * Translate this transform in 3 dimensions by the specified distances
	 * independent of rotation.
	 * 
	 * @param x
	 *            translation in i
	 * @param y
	 *            translation in j
	 * @param z
	 *            translation in k
	 */
	public void translate(float x, float y, float z) {
		Matrix4f mat = new Matrix4f();
		mat.m00 = 1;
		mat.m11 = 1;
		mat.m22 = 1;
		mat.m33 = 1;
		mat.m30 = x;
		mat.m31 = y;
		mat.m32 = z;
		Vector4f pos = new Vector4f(x, y, z, 1);
		Matrix4f.transform(mat, pos, pos);
		position.x += pos.x;
		position.y += pos.y;
		position.z += pos.z;
	}

	/**
	 * Translates the transform position relative to the rotation
	 * 
	 * @param distance
	 *            the distance to translate this transform by
	 */
	public void move_forward(float distance) {
		translate((float) (-distance * Math.cos(rotation.y + Math.PI / 2)),
				(float) (-distance * Math.cos(rotation.x - Math.PI / 2)),
				(float) (-distance * Math.sin(rotation.y + Math.PI / 2)));
	}

	/**
	 * Translates the transform position relative to the rotation
	 * 
	 * @param distance
	 *            the distance to translate this transform by
	 */
	public void move_backward(float distance) {
		move_forward(-distance);
	}

	/**
	 * Translates the transform position relative to the rotation
	 * 
	 * @param distance
	 *            the distance to translate this transform by
	 */
	public void move_right(float distance) {
		translate((float) (distance * Math.cos(rotation.y)), 0, (float) (distance * Math.sin(rotation.y)));
	}

	/**
	 * Translates the transform position relative to the rotation
	 * 
	 * @param distance
	 *            the distance to translate this transform by
	 */
	public void move_left(float distance) {
		move_right(-distance);
	}

	/**
	 * @see components.Updatable#tick()
	 */
	@Override
	public void tick() {
		Vector3f.add(position, velocity, position);
		if (mass > 0) {
			acceleration.set(force.x / mass, force.y / mass, force.z / mass);
			Vector3f.add(position, velocity, position);
		}
		Vector3f.add(rotation, rotational_velocity, rotation);
		rotation.x %= 2 * Math.PI;
		rotation.y %= 2 * Math.PI;
		rotation.z %= 2 * Math.PI;
	}
}
