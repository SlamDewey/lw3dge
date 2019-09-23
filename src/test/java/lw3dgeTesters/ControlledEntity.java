package lw3dgeTesters;

import org.lwjgl.glfw.GLFW;

import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;
import lw3dge.game.Time;
import lw3dge.game.input.Keyboard;
import lw3dge.graphics.entities.GraphicalEntity;
import resloaders.TexturedModels;

public class ControlledEntity extends GraphicalEntity {
	
	private final float speed = 100f;
	private boolean canJump = true;
	private float rotation_speed = 5f;

	public ControlledEntity(Transform transform) {
		super(TexturedModels.BUNNY, transform, NORMAL_SCALE);
	}
	
	@Override
	public void tick() {
		super.tick();
		transform.acceleration.setY(-.03f);
		
		if (!canJump && transform.position.y <= 0f)
			canJump = true;
		
		if (transform.position.y < 0) {
			transform.position.y = 0;
			transform.acceleration.setY(0f);
			transform.velocity.setY(0f);
		}

		if (Keyboard.keys[GLFW.GLFW_KEY_W])
			transform.translate(0, 0, (speed * Time.deltaTime), true);
		if (Keyboard.keys[GLFW.GLFW_KEY_A])
			transform.translate((speed * Time.deltaTime), 0, 0, true);
		if (Keyboard.keys[GLFW.GLFW_KEY_S])
			transform.translate(0, 0, (-speed * Time.deltaTime), true);
		if (Keyboard.keys[GLFW.GLFW_KEY_D])
			transform.translate((-speed * Time.deltaTime), 0, 0, true);
		if (Keyboard.keys[GLFW.GLFW_KEY_SPACE] && canJump) {
			Vector3f.add(new Vector3f(0, 2, 0), transform.velocity, transform.velocity);
			canJump = false;
		}		
		if (Keyboard.keys[GLFW.GLFW_KEY_Q])
			transform.rotate(Transform.Y_AXIS, rotation_speed * Time.deltaTime);
		if (Keyboard.keys[GLFW.GLFW_KEY_E])
			transform.rotate(Transform.Y_AXIS, -rotation_speed * Time.deltaTime);
	}
}
