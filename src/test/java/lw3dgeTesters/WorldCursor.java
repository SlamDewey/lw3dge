package lw3dgeTesters;

import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;
import lw3dge.game.input.MousePicker;
import lw3dge.graphics.entities.GraphicalEntity;
import resloaders.TexturedModels;

public class WorldCursor extends GraphicalEntity {
	
	
	public WorldCursor() {
		super(TexturedModels.BUNNY, new Transform(), new Vector3f(1, 1, 1));
	}
	
	@Override
	public void tick() {
		super.tick();
		
		Vector3f pos = MousePicker.currentTerrainPoint;
		transform.position.set(pos);
	}
}
