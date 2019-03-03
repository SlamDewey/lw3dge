package components.physics;

import components.math.Vector3f;
import game.spacial.Mesh;

public abstract class Collider {
	public Mesh mesh;
	protected Vector3f position;
	protected Vector3f rotation;
	
	public int dimensions;
	
	public Collider(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
		generateHitbox();
	}
	public Collider(Vector3f position, Vector3f rotation, Mesh hitbox) {
		this.position = position;
		this.rotation = rotation;
		this.mesh = hitbox;
	}
	
	
	
	public abstract boolean isCollidingWith(Collider other);
	protected abstract void generateHitbox();
	
}
