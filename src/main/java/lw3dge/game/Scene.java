package lw3dge.game;

import java.util.ArrayList;
import java.util.List;

import lw3dge.components.Updatable;
import lw3dge.components.physics.Transform;
import lw3dge.game.cameras.Camera;
import lw3dge.graphics.entities.GraphicalEntity;
import lw3dge.graphics.entities.Light;

/**
 * This class represents a Game Scene, including a list of objects, lights,
 * sky-box, and object management methods.
 * 
 * @author Jared Massa
 *
 */
public class Scene implements Updatable {

	private Camera currentCamera;

	public List<GraphicalEntity> entities = new ArrayList<GraphicalEntity>();
	public Light light;

	public Scene() {

	}

	/**
	 * Sets the rendering camera reference
	 * 
	 * @param camera
	 *            the new {@link Camera}
	 */
	public void setCamera(Camera camera) {
		currentCamera = camera;
	}

	public Transform getCameraTransform() {
		return currentCamera.transform;
	}

	/**
	 * Adds a GraphicalEntity to the render list for this scene
	 * 
	 * @param entity
	 *            the GraphicalEntity to add
	 */
	public void addEntity(GraphicalEntity entity) {
		entities.add(entity);
	}

	/**
	 * Sets the global light for this scene
	 * 
	 * @param light
	 *            the Light to add
	 */
	public void setGlobalLight(Light light) {
		this.light = light;
	}

	/**
	 * Ticks the Camera and all entities in this scene
	 * @see lw3dge.components.Updatable#tick()
	 */
	public void tick() {
		currentCamera.tick();
		for (GraphicalEntity e : entities)
			e.tick();
	}
}
