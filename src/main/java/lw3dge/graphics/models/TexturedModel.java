package lw3dge.graphics.models;

import lw3dge.graphics.textures.ModelTexture;

/**
 * This class represents a connection between a RawModel and a ModelTexture
 * Credit to ThinMatrix and his OpenGL Tutorials
 * 
 * @author Jared Massa
 * @see lw3dge.graphics.models.RawModel
 * @see lw3dge.graphics.textures.ModelTexture
 */
public class TexturedModel {
	/**
	 * The model to be using.
	 */
	private RawModel model;
	/**
	 * The ModelTexture referencing the texture this model should use.
	 */
	private ModelTexture texture;

	/**
	 * Create a TexturedModel reference with a given model and texture
	 * 
	 * @param model
	 *            the {@link RawModel} to use
	 * @param texture
	 *            the {@link ModelTexture} to use
	 */
	public TexturedModel(RawModel model, ModelTexture texture) {
		this.model = model;
		this.texture = texture;
		
	}

	/**
	 * Update this model's texture
	 * 
	 * NOTE: THIS WILL CHANGE ALL OBJECTS USING THIS TEXTUREDMODEL
	 * 
	 * @param texture
	 *            the new {@link ModelTexture}
	 */
	public void setTexture(ModelTexture texture) {
		this.texture = texture;
	}

	/**
	 * Get the RawModel this object uses
	 * 
	 * @param LOD
	 *            the level of detail for this model
	 * @return the {@link RawModel}
	 */
	public RawModel getRawModel() {
		return model;
	}

	/**
	 * Get the Texture this object uses
	 * 
	 * @return the {@link ModelTexture}
	 */
	public ModelTexture getTexture() {
		return texture;
	}
}
