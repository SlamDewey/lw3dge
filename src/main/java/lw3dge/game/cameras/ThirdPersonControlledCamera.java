package lw3dge.game.cameras;

import lw3dge.components.math.Matrix4f;
import lw3dge.components.math.Quaternion;
import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;
import lw3dge.game.Time;
import lw3dge.game.input.Cursor;
import lw3dge.game.input.MouseScroll;

public class ThirdPersonControlledCamera extends Camera {

	private final float MIN_ANGLE = (float) Math.PI / 50f;
	private final float DEG_90 = (float) Math.PI / 2f;

	private float vertical_sensitivity = .08f;
	private float horizontal_sensitivity = .08f;
	private float scroll_constant = 2.0f;

	private float distanceToFocus = 50f;
	private float angleAroundFocus = 0;
	private float pitch = (float) Math.PI / 6f;

	private Transform focus;

	public ThirdPersonControlledCamera() {
		super();
		this.focus = new Transform();
		lookAtFocus();
	}

	public ThirdPersonControlledCamera(Transform focus, float vertical_sensitivity, float horizontal_sensitivity,
			float scroll_constant) {
		super();
		this.focus = focus;
		this.vertical_sensitivity = vertical_sensitivity;
		this.horizontal_sensitivity = horizontal_sensitivity;
		this.scroll_constant = scroll_constant;
		lookAtFocus();
	}

	public ThirdPersonControlledCamera(Transform focus) {
		super();
		this.focus = focus;
		lookAtFocus();
	}

	public void setFocus(Transform focus) {
		this.focus = focus;
		lookAtFocus();
	}

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

	/**
	 * Call the tick method in the Super class and then calculate additional
	 * translations based on mouse inputs.
	 * 
	 * @see lw3dge.game.cameras.Camera#tick()
	 */
	private int last_scroll_event = -1;

	@Override
	public void tick() {
		super.tick();
		if (pitch > DEG_90)
			pitch = DEG_90;
		else if (pitch < MIN_ANGLE)
			pitch = MIN_ANGLE;
		if (distanceToFocus < 5)
			distanceToFocus = 5;

		transform.position.z = (float) Math.sin(-angleAroundFocus) * distanceToFocus + focus.position.z;
		transform.position.x = (float) Math.cos(-angleAroundFocus) * distanceToFocus + focus.position.x;
		transform.position.y = (float) Math.sin(pitch) * distanceToFocus + focus.position.y;

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
