package lw3dge.game.cameras;

import lw3dge.components.Updatable;
import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;

public class Camera implements Updatable {
	public Transform transform;

	public Camera() {
		transform = new Transform();
	}
	public Camera(Vector3f position) {
		transform = new Transform(position, 0);
	}
	public Camera(Transform transform) {
		this.transform = transform;
	}
	
	@Override
	public void tick() {
		transform.tick();
	}
}
