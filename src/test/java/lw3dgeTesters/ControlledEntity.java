package lw3dgeTesters;

import org.lwjgl.glfw.GLFW;

import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;
import lw3dge.game.Game;
import lw3dge.game.Time;
import lw3dge.game.input.Keyboard;
import lw3dge.game.input.Mouse;
import lw3dge.graphics.entities.GraphicalEntity;
import resloaders.TexturedModels;

public class ControlledEntity extends GraphicalEntity {

	private final float speed = 35f;
	private WorldCursor aimer = new WorldCursor();

	public ControlledEntity(Transform transform) {
		super(TexturedModels.BUNNY, transform, new Vector3f(1, 1, 1));
		transform.rotate(Transform.Y_AXIS, (float) Math.PI);

		Game.CURRENT_SCENE.addEntity(aimer);
	}

	@Override
	public void tick() {
		super.tick();
		
		if (transform.position.y < 0) {
			transform.position.y = 0;
		}

		if (Keyboard.keys[GLFW.GLFW_KEY_W])
			transform.position.z -= speed * Time.deltaTime;
		if (Keyboard.keys[GLFW.GLFW_KEY_A])
			transform.position.x -= speed * Time.deltaTime;
		if (Keyboard.keys[GLFW.GLFW_KEY_S])
			transform.position.z += speed * Time.deltaTime;
		if (Keyboard.keys[GLFW.GLFW_KEY_D])
			transform.position.x += speed * Time.deltaTime;

		if (Mouse.buttons[GLFW.GLFW_MOUSE_BUTTON_1]) {
			System.out.println("Clicked!");
		}
		
		lookAt(aimer.transform);
	}
	
	//a special 2d LookAt function (ignores Y position of entities)
	public void lookAt(Transform focus) {
		Vector3f f = Vector3f.sub(transform.position, focus.position, null);
		f.negate();
		double hang = Math.atan(f.z / f.x) / -2;
		double quart_pi = Math.PI / 4;
		if (f.x > 0) hang += quart_pi;
		else hang += 3 * quart_pi;
		transform.orientation.set(0, (float) (Math.sin(hang) * Math.cos(1)), 0, (float)Math.cos(hang));
	}
}
