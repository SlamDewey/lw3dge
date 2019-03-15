package lw3dge.game.cameras;

import lw3dge.components.math.Matrix4f;
import lw3dge.components.math.Quaternion;
import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;
import lw3dge.game.Time;
import lw3dge.game.input.Cursor;
import lw3dge.game.input.MouseScroll;

/**
 * A special Camera class to SLERP around a given focus transform and adjust the
 * camera's pitch, yaw, and distance to focus with mouse inputs on the screen.
 * 
 * @author Jared Massa
 *
 */
public class ThirdPersonControlledCamera extends Camera {

	private final float MIN_ANGLE = (float) (Math.PI / 180f) * 5f; // 5deg min angle
	private final float MAX_ANGLE = (float) (Math.PI / 180f) * 85f; // 85deg max angle
	private final float MAX_DISTANCE = 300.0f;
	private final float MIN_DISTANCE = 5.0f;

	// should move to config?
	private float vertical_sensitivity = .08f;
	private float horizontal_sensitivity = .08f;
	private float scroll_constant = 2;

	// starting values
	private float distanceToFocus = 50f;
	private float angleAroundFocus = 0f;
	private float pitch = (float) (Math.PI / 180f) * 30f;

	private Transform focus;

	/**
	 * Create a new ThirdPersonControlledCamera with no given focus transform. A new
	 * focus Transform will be created with no values, and the camera will look at
	 * the world origin.
	 */
	public ThirdPersonControlledCamera() {
		super();
		this.focus = new Transform();
		lookAtFocus();
	}

	/**
	 * Create a new ThirdPersonControlledCamera with given focus and special
	 * sensitivity values.
	 * 
	 * @param focus                  the focus transform
	 * @param vertical_sensitivity   the pitch change sensitivity (default 0.08f)
	 * @param horizontal_sensitivity the yaw change sensitivity (default 0.08f)
	 * @param scroll_constant        the distance to scroll on a scroll event
	 *                               (default 2f)
	 */
	public ThirdPersonControlledCamera(Transform focus, float vertical_sensitivity, float horizontal_sensitivity,
			float scroll_constant) {
		super();
		this.focus = focus;
		this.vertical_sensitivity = vertical_sensitivity;
		this.horizontal_sensitivity = horizontal_sensitivity;
		this.scroll_constant = scroll_constant;
		lookAtFocus();
	}

	/**
	 * Create a new ThirdPersonControlledCamera with a focus transform
	 * 
	 * @param focus the focus transform
	 */
	public ThirdPersonControlledCamera(Transform focus) {
		super();
		this.focus = focus;
		lookAtFocus();
	}

	/**
	 * Sets the focus transform for this camera to SLERP over.
	 * 
	 * @param focus the focus transform
	 */
	public void setFocus(Transform focus) {
		this.focus = focus;
		lookAtFocus();
	}

	/**
	 * Calculates and sets a new orientation for this camera's transform by using
	 * vector cross products.
	 */
	private void lookAtFocus() {
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

	private int last_scroll_event = -1;
	/**
	 * Call the tick method in the Super class and then calculate additional
	 * translations based on mouse inputs.
	 * 
	 * @see lw3dge.game.cameras.Camera#tick()
	 */
	@Override
	public void tick() {
		super.tick();
		// clamp pitch
		if (pitch > MAX_ANGLE)
			pitch = MAX_ANGLE;
		else if (pitch < MIN_ANGLE)
			pitch = MIN_ANGLE;
		// clamp distance
		if (distanceToFocus > MAX_DISTANCE)
			distanceToFocus = MAX_DISTANCE;
		else if (distanceToFocus < MIN_DISTANCE)
			distanceToFocus = MIN_DISTANCE;
		// update this camera's position:
		// calculate xz_dist for slerp (essentially radius of the circle on the xz plane
		float xz_distToFocus = distanceToFocus * (float) Math.cos(pitch);
		transform.position.z = (float) Math.sin(-angleAroundFocus) * xz_distToFocus + focus.position.z;
		transform.position.x = (float) Math.cos(-angleAroundFocus) * xz_distToFocus + focus.position.x;
		// find point along circle of zy or zx plane whichever it is.
		transform.position.y = (float) Math.sin(pitch) * distanceToFocus + focus.position.y;

		// angle the camera to look at focus position
		lookAtFocus();

		if (Cursor.HAS_FOCUS) {
			angleAroundFocus -= Cursor.translation.x * vertical_sensitivity * Time.deltaTime;
			pitch += Cursor.translation.y * horizontal_sensitivity * Time.deltaTime;
			if (MouseScroll.SCROLL_EVENT_ID != last_scroll_event) {
				distanceToFocus -= MouseScroll.dy * scroll_constant;
				last_scroll_event = MouseScroll.SCROLL_EVENT_ID;
			}
		}
	}
}
