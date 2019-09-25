package lw3dgeTesters;

import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;
import lw3dge.game.input.MousePicker;
import lw3dge.graphics.entities.GraphicalEntity;
import resloaders.TexturedModels;

public class WorldCursor extends GraphicalEntity {
	
	
	public WorldCursor() {
		super(TexturedModels.AIMER, new Transform(), new Vector3f(1f, 1f, 1f));
	}
	
	@Override
	public void tick() {
		super.tick();
		
		Vector3f pos = MousePicker.currentTerrainPoint;
		transform.position.set(pos);
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
