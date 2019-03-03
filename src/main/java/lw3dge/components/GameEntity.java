package lw3dge.components;

import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;
import lw3dge.graphics.entities.GraphicalEntity;
import lw3dge.graphics.models.TexturedModel;

public class GameEntity extends GraphicalEntity {
	
	public GameEntity(TexturedModel model, Transform transform, Vector3f scale) {
		super(model, transform, scale);
	}

}
