package lw3dge.components.physics;

import lw3dge.components.math.Vector3f;

public abstract class Collider {
	protected Vector3f position;
	protected Vector3f rotation;
	
	
	public Collider(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
		generateHitbox();
	}
	
	
	
	public abstract boolean isCollidingWith(Collider other);
	protected abstract void generateHitbox();
	
}
