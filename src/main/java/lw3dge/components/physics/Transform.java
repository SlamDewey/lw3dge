package lw3dge.components.physics;

import lw3dge.components.Updatable;
import lw3dge.components.math.Matrix4f;
import lw3dge.components.math.Quaternion;
import lw3dge.components.math.Vector3f;
import lw3dge.engine.Log;

/**
 * 
 * This class implements a 3D object transform I am writing this to abstract
 * Transform components from graphical entities, and allow inclusion in all
 * entities.
 * 
 * @author Jared Massa
 */
public class Transform implements Updatable {

	public static final Vector3f X_AXIS = new Vector3f(1f, 0f, 0f);
	public static final Vector3f Y_AXIS = new Vector3f(0f, 1f, 0f);
	public static final Vector3f Z_AXIS = new Vector3f(0f, 0f, 1f);

	
	public Vector3f position;
	public Vector3f velocity;

	public Vector3f acceleration;

	public Quaternion orientation;

	// objects with no mass will experience no acceleration
	public float mass = 0;

	public Transform() {
		this.position = new Vector3f(0, 0, 0);
		this.velocity = new Vector3f(0, 0, 0);
		this.acceleration = new Vector3f(0, 0, 0);
		this.orientation = new Quaternion(0, 0, 0, 1);
	}

	public Transform(Vector3f position, float mass) {
		this.position = position;
		this.velocity = new Vector3f();
		this.acceleration = new Vector3f();
		this.orientation = new Quaternion(0, 0, 0, 1);
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
	private void translate(float x, float y, float z) {
		position.x += x;
		position.y += y;
		position.z += z;
	}

	public void translate(float right, float up, float forward, boolean usesLocalAxes) {
		if (!usesLocalAxes)
			translate(right, up, forward);
		else {
			Quaternion v = new Quaternion(right, up, forward, 0);
			Quaternion.mul(orientation, v, v);
			Quaternion.mulInverse(v, orientation, v);
			translate(v.x, v.y, v.z);
		}
	}

	public void rotate(Vector3f axis, float angle) {
		Quaternion na = new Quaternion(axis.x, axis.y, axis.z, angle / 2);
		Quaternion.mul(na, orientation, orientation);
		orientation.normalise();
	}

	public Matrix4f toMatrix4f() {
		Matrix4f matrix = new Matrix4f();
		orientation.normalise();
		Matrix4f.translate(position, matrix, matrix);
		Matrix4f.mul(matrix, orientation.toMatrix4f(), matrix);
		return matrix;
	}

	/**
	 * @see lw3dge.components.Updatable#tick()
	 */
	public void tick() {
		Vector3f.add(position, velocity, position);
		Vector3f.add(acceleration, velocity, velocity);
	}
}
