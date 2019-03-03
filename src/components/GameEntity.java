package components;

import components.math.Vector3f;
import components.physics.Transform;
import graphics.entities.GraphicalEntity;
import graphics.models.TexturedModel;

public class GameEntity extends GraphicalEntity {
	
	public GameEntity(TexturedModel model, Transform transform, Vector3f scale) {
		super(model, transform, scale);
	}

}
