package lw3dgeTesters;

import org.lwjgl.glfw.GLFW;

import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;
import lw3dge.game.Time;
import lw3dge.game.input.Keyboard;
import lw3dge.graphics.entities.GraphicalEntity;
import resloaders.TexturedModels;

public class ControlledEntity extends GraphicalEntity {
	private final float speed = 10f;
	private boolean canJump = true;
	private float time_passed = 0.0f;

	public ControlledEntity(Transform transform) {
		super(TexturedModels.BUNNY, transform, NORMAL_SCALE);
	}

	@Override
	public void tick() {
		super.tick();
		transform.acceleration.set(0, -1 * Time.deltaTime, 0);
		
		if (time_passed < 0.05f && !canJump) {
			time_passed += Time.deltaTime;
		}
		else if (!canJump) {
			canJump = true;
		}
		if (transform.position.y < 0) {
			transform.position.y = 0;
			transform.acceleration.set(0, 0, 0);
			transform.velocity.set(0, 0, 0);
		}
		
		if (Keyboard.keys[GLFW.GLFW_KEY_W])
			transform.move_forward(speed * Time.deltaTime);
		if (Keyboard.keys[GLFW.GLFW_KEY_A])
			transform.move_left(speed * Time.deltaTime);
		if (Keyboard.keys[GLFW.GLFW_KEY_S])
			transform.move_backward(speed * Time.deltaTime);
		if (Keyboard.keys[GLFW.GLFW_KEY_D])
			transform.move_right(speed * Time.deltaTime);
		if (Keyboard.keys[GLFW.GLFW_KEY_SPACE] && canJump) {
			Vector3f.add(transform.velocity, new Vector3f(0, 15f * Time.deltaTime, 0), transform.velocity);
			canJump = false;
			time_passed = 0f;
		}
	}
}
