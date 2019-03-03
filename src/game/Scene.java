package game;

import java.util.ArrayList;
import java.util.List;

import components.Updatable;
import components.physics.Transform;
import game.cameras.Camera;
import graphics.entities.GraphicalEntity;
import graphics.entities.Light;

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
	 * Sets the rendering camera, and this scene's static camera reference.
	 * 
	 * @param camera
	 *            the new camera
	 * @see game.Scene#CURRENT_CAMERA
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
	 */
	@Override
	public void tick() {
		currentCamera.tick();
		for (GraphicalEntity e : entities)
			e.tick();
	}
}
