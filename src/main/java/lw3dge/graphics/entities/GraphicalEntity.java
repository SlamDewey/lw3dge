package lw3dge.graphics.entities;

import lw3dge.components.Updatable;
import lw3dge.components.math.Matrix4f;
import lw3dge.components.math.Quaternion;
import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;
import lw3dge.graphics.models.TexturedModel;

/**
 * This class is the most basic Entity class. It represents an in-game object as
 * a TexturedModel, Transform, and Scale.
 * 
 * @author Jared Massa
 * @see lw3dge.components.physics.Transform
 * @see lw3dge.graphics.models.TexturedModel
 */
public class GraphicalEntity implements Updatable {

	/**
	 * A scale with no variation in any axis
	 */
	public static final Vector3f NORMAL_SCALE = new Vector3f(1, 1, 1);

	/**
	 * The instructions on how to draw this object stored in a {@link TexturedModel}
	 */
	protected TexturedModel model;
	/**
	 * The instructions on where to draw this object stored in a {@link Transform}
	 */
	public Transform transform;
	/**
	 * The instructions on how big to draw this object
	 */
	protected Vector3f scale;

	/**
	 * Create a new GraphicalEntity with these parameters
	 * 
	 * @param model
	 *            the {@link TexturedModel} to represent this basic entity
	 * @param transform
	 *            the {@link Transform} for this specific entity
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
	 * @see lw3dge.components.Updatable#tick()
	 */
	public void tick() {
		transform.tick();
	}

	/**
	 * Update the {@link TexturedModel} for this entity
	 * 
	 * @param model
	 *            the new model
	 */
	public void setTexturedModel(TexturedModel model) {
		this.model = model;
	}

	/*
	 * Only Getters below
	 ***************************************/
	
	public Matrix4f getTransformationMatrix() {
		Matrix4f matrix = transform.toMatrix4f();
		Matrix4f.scale(new Vector3f(scale.x, scale.y, scale.z), matrix, matrix);
		return matrix;
	}

	public TexturedModel getTexturedModel() {
		return model;
	}

	public Vector3f getScale() {
		return scale;
	}

	public Vector3f getPosition() {
		return transform.position;
	}
	
	public Quaternion getOrientation() {
		return transform.orientation;
	}
}
