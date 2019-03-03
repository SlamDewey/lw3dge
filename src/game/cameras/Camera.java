package game.cameras;

import components.Updatable;
import components.math.Vector3f;
import components.physics.Transform;

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
