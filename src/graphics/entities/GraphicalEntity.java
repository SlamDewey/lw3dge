package graphics.entities;

import components.Updatable;
import components.math.Vector3f;
import components.physics.Transform;
import graphics.models.TexturedModel;

/**
 * This class is the most basic Entity class. It represents an in-game object as
 * a TexturedModel, Transform, and Scale.
 * 
 * @author Jared Massa
 * @see components.physics.Transform
 * @see graphics.models.TexturedModel
 */
public class GraphicalEntity implements Updatable {

	protected TexturedModel model;
	protected Transform transform;
	protected Vector3f scale;

	public static final Vector3f NORMAL_SCALE = new Vector3f(1, 1, 1);

	/**
	 * Create a new GraphicalEntity with these parameters
	 * 
	 * @param model
	 *            the TexturedModel to represent this basic entity
	 * @param transform
	 *            the transform for this specific entity
	 * @param scale
	 *            the scale of this specific entity (graphical scale)
	 */
	public GraphicalEntity(TexturedModel model, Transform transform, Vector3f scale) {
		this.model = model;
		this.transform = transform;
		this.scale = scale;
	}

	/**
	 * Ticks the Transform of this GraphicalEntity
	 * 
	 * @see components.Updatable#tick()
	 */
	@Override
	public void tick() {
		transform.tick();
	}

	/**
	 * Update the TexturedModel for this entity
	 * 
	 * @param model
	 *            the new model
	 */
	public void setTexturedModel(TexturedModel model) {
		this.model = model;
	}

	/****************************************
	 * Only Getters below
	 ****************************************/
	public TexturedModel getTexturedModel() {
		return model;
	}

	public Vector3f getScale() {
		return scale;
	}

	public Vector3f getPosition() {
		return transform.position;
	}

	public Vector3f getRotation() {
		return transform.rotation;
	}

	public float getXRot() {
		return transform.rotation.x;
	}

	public float getYRot() {
		return transform.rotation.y;
	}

	public float getZRot() {
		return transform.rotation.z;
	}
}
