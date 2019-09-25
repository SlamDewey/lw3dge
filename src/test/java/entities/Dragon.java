package entities;

import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;
import lw3dge.graphics.entities.GraphicalEntity;
import resloaders.TexturedModels;

public class Dragon extends GraphicalEntity {

	public Dragon() {
		super(TexturedModels.DRAGON,
				new Transform(
						new Vector3f((float) (Math.random() * -200.0f) + 100f, 0, (float) (Math.random() * -100.0f)),
						0),
				new Vector3f(1, 1, 1));
	}

	@Override
	public void onDelete() {

	}

	@Override
	public void onCollision(GraphicalEntity other) {

	}

}
