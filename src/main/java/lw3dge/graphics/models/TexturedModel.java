package lw3dge.graphics.models;

import lw3dge.graphics.textures.ModelTexture;

/**
 * This class represents a connection between a RawModel and a ModelTexture
 * Credit to ThinMatrix and his OpenGL Tutorials
 * 
 * @author Jared Massa
 * @see lw3dge.graphics.models.RawModel
 * @see graphics.textures.ModelTexture
 */
public class TexturedModel {
	private RawModel model;
	private ModelTexture texture;

	/**
	 * Create a TexturedModel reference with a model and texture
	 * 
	 * @param model
	 *            the model to use
	 * @param texture
	 *            the texture to use
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
	 *            the new texture
	 */
	public void setTexture(ModelTexture texture) {
		this.texture = texture;
	}

	/**
	 * Get the RawModel this object uses
	 * 
	 * @return the model
	 */
	public RawModel getRawModel() {
		return model;
	}

	/**
	 * Get the Texture this object uses
	 * 
	 * @return the texture
	 */
	public ModelTexture getTexture() {
		return texture;
	}
}
