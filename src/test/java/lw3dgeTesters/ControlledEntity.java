package lw3dgeTesters;

import org.lwjgl.glfw.GLFW;

import lw3dge.components.physics.Transform;
import lw3dge.game.Game;
import lw3dge.game.Time;
import lw3dge.game.input.Keyboard;
import lw3dge.graphics.entities.GraphicalEntity;
import resloaders.TexturedModels;

public class ControlledEntity extends GraphicalEntity {
	
	private final float speed = 35f;
	private WorldCursor aimer = new WorldCursor();

	public ControlledEntity(Transform transform) {
		super(TexturedModels.BUNNY, transform, NORMAL_SCALE);
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
		transform.lookAt(aimer.transform);
	}
}
