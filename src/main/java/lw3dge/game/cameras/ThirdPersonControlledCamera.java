package lw3dge.game.cameras;

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
	private float angleAroundFocus = 0f;
	private float pitch = (float) Math.PI / 6f;
	
	private Transform focus;

	public ThirdPersonControlledCamera() {
		super();
		this.focus = new Transform();
	}
	public ThirdPersonControlledCamera(Transform focus, float vertical_sensitivity, float horizontal_sensitivity, float scroll_constant) {
		super();
		this.focus = focus;
		this.vertical_sensitivity = vertical_sensitivity;
		this.horizontal_sensitivity = horizontal_sensitivity;
		this.scroll_constant = scroll_constant;
	}

	public ThirdPersonControlledCamera(Transform focus) {
		super();
		this.focus = focus;
	}
	
	public void setFocus(Transform focus) {
		this.focus = focus;
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
		if (pitch > DEG_90) pitch = DEG_90;
		else if (pitch < MIN_ANGLE) pitch = MIN_ANGLE;
		if (distanceToFocus < 5) distanceToFocus = 5;
		

		float horizDistance = (float) (distanceToFocus * Math.cos(pitch));
		float theta = focus.rotation.y + angleAroundFocus;
		
		transform.position.x = focus.position.x - (float) (horizDistance   * Math.sin(theta));
		transform.position.z = focus.position.z - (float) (horizDistance   * Math.cos(theta));
		transform.position.y = focus.position.y + (float) (distanceToFocus * Math.sin(pitch));
		
		transform.rotation.x = pitch;
		transform.rotation.y = (float) (Math.PI - theta);
		
		if (Cursor.HAS_FOCUS) {
			angleAroundFocus -= Cursor.translation.x * vertical_sensitivity * Time.deltaTime;
			pitch            += Cursor.translation.y * horizontal_sensitivity * Time.deltaTime;
			if (MouseScroll.SCROLL_EVENT_ID != last_scroll_event) {
				distanceToFocus -= MouseScroll.dy * scroll_constant;
				last_scroll_event = MouseScroll.SCROLL_EVENT_ID;
			}
		}
	}
}
