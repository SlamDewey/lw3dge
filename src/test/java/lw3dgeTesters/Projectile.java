package lw3dgeTesters;

import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;
import lw3dge.graphics.entities.GraphicalEntity;
import resloaders.TexturedModels;

public class Projectile extends GraphicalEntity {

	public Projectile(Vector3f position, Vector3f vel) {
		super(TexturedModels.CAPSULE, new Transform(), NORMAL_SCALE);
		transform.position.set(position);
		transform.velocity.set(vel);
	}
	
	int x = 0;
	@Override
	public void tick() {
		super.tick();
		x++;
		if (x > 500) {
			delete();
		}
	}
	
	@Override
	public void onDelete() {
		System.out.println("Deleted Projectile!");
	}

}
