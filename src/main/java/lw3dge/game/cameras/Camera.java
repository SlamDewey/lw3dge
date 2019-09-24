package lw3dge.game.cameras;

import lw3dge.components.Updatable;
import lw3dge.components.math.Matrix4f;
import lw3dge.components.math.Quaternion;
import lw3dge.components.math.Vector3f;
import lw3dge.components.math.Vector4f;
import lw3dge.components.physics.Transform;

/**
 * This class represents an object used for holding data about a view matrix.
 * 
 * The view matrix is used for rending specific objects onto the screen and is
 * based on the camera's {@link Transform}.
 * 
 * @author Jared Massa
 * @see lw3dge.graphics.math.Maths#createViewMatrix(Transform)
 */
public class Camera implements Updatable {
	/**
	 * The {@link Transform} data of this Camera.
	 */
	public Transform transform;
	
	/**
	 * Create a new Camera with no {@link Transform} parameters.
	 */
	public Camera() {
		transform = new Transform();
	}
	/**
	 * Create a new Camera in a given position
	 * @param position the position
	 */
	public Camera(Vector3f position) {
		transform = new Transform(position, 0);
	}
	/**
	 * Create a new Camera with a given transform reference
	 * @param transform the {@link Transform} to reference
	 */
	public Camera(Transform transform) {
		this.transform = transform;
	}
	
	/**
	 * Calculates and sets a new orientation to make this transform face another
	 * (focus) transform by using vector cross products.
	 * 
	 * @param focus
	 *            The focus Transform to orient this transform toward
	 */
	public void lookAt(Transform focus) {
		Vector3f f = Vector3f.sub(focus.position, transform.position, null);
		Vector3f u = new Vector3f(0, 1f, 0);
		f.normalise();
		Vector3f r = Vector3f.cross(u, f, null);
		r.normalise();
		Vector3f.cross(f, r, u);
		f.scale(-1f);
		r.scale(-1f);

		Matrix4f m = new Matrix4f();
		m = Quaternion.toRotationMatrix(r, u, f);
		transform.orientation.setFromMatrix(m);
	}
	
	/**
	 * Tick this Camera's {@link Transform}
	 * 
	 * @see lw3dge.components.Updatable#tick()
	 */
	@Override
	public void tick() {
		transform.tick();
	}
	public Matrix4f getViewMatrix() {
		transform.orientation.normalise();
		Matrix4f matrix = new Matrix4f();
		Vector3f np = Vector3f.sub(new Vector3f(), transform.position, null);
		Vector4f aa = transform.orientation.getAxisAngle();
		Vector3f axis = new Vector3f(aa.x, aa.y, aa.z);
		Matrix4f.rotate(aa.w, axis, matrix, matrix);
		Matrix4f.translate(np, matrix, matrix);
		return matrix;
	}
}
