package lw3dge.game.cameras;

import lw3dge.components.physics.Transform;

public class ThirdPersonCamera extends Camera {
	
	private final int DISTANCE = 50;
	
	private Transform focus;

	/**
	 * Create a new ThirdPersonControlledCamera with no given focus transform. A new
	 * focus Transform will be created with no values, and the camera will look at
	 * the world origin.
	 */
	public ThirdPersonCamera() {
		super();
		this.focus = new Transform();
		lookAtFocus();
	}
	/**
	 * Create a new ThirdPersonControlledCamera with a focus transform
	 * 
	 * @param focus the focus transform
	 */
	public ThirdPersonCamera(Transform focus) {
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
	
	private void lookAtFocus() {
		transform.lookAt(focus);
	}
	
	/**
	 * Call the tick method in the Super class and then calculate additional
	 * translations based on mouse inputs.
	 * 
	 * @see lw3dge.game.cameras.Camera#tick()
	 */
	@Override
	public void tick() {
		super.tick();
		transform.position.y = DISTANCE * 2 + focus.position.y;
		transform.position.z = DISTANCE + focus.position.z;
		transform.position.x = focus.position.x;
		lookAtFocus();
	}
}