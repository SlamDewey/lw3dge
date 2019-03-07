package lw3dge.game.cameras;

import org.lwjgl.glfw.GLFW;

import lw3dge.components.physics.Transform;
import lw3dge.game.Time;
import lw3dge.game.input.Cursor;
import lw3dge.game.input.Keyboard;

/**
 * This class extends Camera and adds on First Person Camera Controls. You may
 * use the WASD keys to translate the camera with respect to orientation. You
 * may also use the Left Control and Space Keys to translate down and up in the
 * Y axis, respectively. Finally you may use the translation of the mouse across
 * the screen to define camera rotation.
 * 
 * @author Jared Massa
 *
 */
public class FirstPersonControlledCamera extends Camera {

	private final float vertical_sensitivity = .08f;
	private final float horizontal_sensitivity = .08f;
	/**
	 * Translational Movement Speed
	 */
	private final float SPEED = 10f;

	public FirstPersonControlledCamera() {
		super();
	}

	public FirstPersonControlledCamera(Transform transform) {
		super(transform);
	}

	/**
	 * Call the tick method in the Super class and then calculate additional
	 * translations based on keyboard and mouse inputs.
	 * 
	 * @see lw3dge.game.cameras.Camera#tick()
	 */
	@Override
	public void tick() {
		super.tick();
		float speed = SPEED * ((Keyboard.keys[GLFW.GLFW_KEY_LEFT_SHIFT]) ? 2 : 1);
		if (Keyboard.keys[GLFW.GLFW_KEY_W])
			transform.translate(0, 0, (speed * Time.deltaTime), true);
		if (Keyboard.keys[GLFW.GLFW_KEY_A])
			transform.translate((-speed * Time.deltaTime), 0, 0, true);
		if (Keyboard.keys[GLFW.GLFW_KEY_S])
			transform.translate(0, 0, (-speed * Time.deltaTime), true);
		if (Keyboard.keys[GLFW.GLFW_KEY_D])
			transform.translate((speed * Time.deltaTime), 0, 0, true);
		if (Keyboard.keys[GLFW.GLFW_KEY_SPACE])
			transform.translate(0, speed * Time.deltaTime, 0, false);
		if (Keyboard.keys[GLFW.GLFW_KEY_LEFT_CONTROL])
			transform.translate(0, -speed * Time.deltaTime, 0, false);

		if (Cursor.HAS_FOCUS) {
			transform.rotate(Transform.Y_AXIS, -Cursor.translation.x * horizontal_sensitivity * Time.deltaTime);
			transform.rotate(Transform.X_AXIS, Cursor.translation.y * vertical_sensitivity * Time.deltaTime);
		}
	}
}
