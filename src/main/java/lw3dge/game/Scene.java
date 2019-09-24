package lw3dge.game;

import java.util.ArrayList;
import java.util.List;

import lw3dge.components.LinkedListNode;
import lw3dge.components.Updatable;
import lw3dge.components.physics.Transform;
import lw3dge.game.cameras.Camera;
import lw3dge.game.terrain.Terrain;
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
	
	public LinkedListNode<GraphicalEntity> firstEntity;
	public LinkedListNode<GraphicalEntity> lastEntity;
	public List<Terrain> terrains = new ArrayList<Terrain>();
	public List<Light> lights = new ArrayList<Light>();

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
		LinkedListNode<GraphicalEntity> toAdd = new LinkedListNode<GraphicalEntity>(entity);
		if (firstEntity == null) {
			firstEntity = toAdd;
			return;
		}
		setLastEntity();
		lastEntity.addAfter(toAdd);
		lastEntity = toAdd;
	}
	public void delete(GraphicalEntity entity) {
		LinkedListNode<GraphicalEntity> cur = firstEntity;
		while (cur.getContent() != entity)
			cur = cur.next;
		if (entity == cur.getContent())
			cur.delete();
	}
	/**
	 * Cycle through all Linked Nodes, until hitting a node where node.next ==
	 * null i.e. the last node. Then set the 'lastEntity' variable to reference
	 * that node.
	 */
	public void setLastEntity() {
		if (firstEntity == null)
			return;
		LinkedListNode<GraphicalEntity> cur = firstEntity;
		while (cur.next != null)
			cur = cur.next;
		lastEntity = cur;
	}

	/**
	 * Adds a terrain
	 * 
	 * @param terrain
	 *            the Terrain to add
	 */
	public void addTerrain(Terrain terrain) {
		terrains.add(terrain);
	}

	/**
	 * Adds a light
	 * 
	 * @param light
	 *            the Light to add
	 */
	public void addLight(Light light) {
		lights.add(light);
	}

	/**
	 * Ticks the Camera and all entities in this scene
	 * 
	 * @see lw3dge.components.Updatable#tick()
	 */
	public void tick() {

		LinkedListNode<GraphicalEntity> cur = firstEntity;
		while (cur != null) {
			if (cur.getContent().shouldDelete) {
				cur.getContent().onDelete();
				if (cur.last != null) {
					cur = cur.last;
					cur.next.delete();
				} 
				else
					firstEntity = cur.next;
			}
			else {
				cur.getContent().tick();
			}
			cur = cur.next;
		}
		currentCamera.tick();
	}

	public Camera getCamera() {
		return currentCamera;
	}
}
