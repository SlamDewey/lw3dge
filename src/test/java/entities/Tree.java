package entities;

import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;
import lw3dge.graphics.entities.GraphicalEntity;
import resloaders.TexturedModels;

public class Tree extends GraphicalEntity {

	public Tree() {
		super(TexturedModels.TREE,
				new Transform(new Vector3f((float) (Math.random() * 1600f) - 800f, 0, (float) (Math.random() * -800f)),
						0),
				new Vector3f(10, 10, 10));
	}

	@Override
	public void onDelete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCollision(GraphicalEntity other) {
		// TODO Auto-generated method stub

	}

}
