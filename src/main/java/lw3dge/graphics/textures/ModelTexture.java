package lw3dge.graphics.textures;

/**
 * This class represents a pointer to a loaded Texture, as well as minor generic
 * texture characteristics.
 * 
 * @author Jared Massa
 */
public class ModelTexture {
	/**
	 * Represents the texture pointer.
	 */
	protected int textureID;
	/**
	 * A float used in the shader to determine the factor of reflected light
	 * that should be absorbed by a camera view.
	 */
	public float shineDamper = 1;
	/**
	 * A float to represent the reflectivity of this object (in use for specular
	 * lighting).
	 */
	public float reflectivity = 0;

	/**
	 * Create a new ModelTexture with a given Texture pointer
	 * 
	 * @param id
	 *            the ID of the texture, also known as the pointer for GLFW
	 */
	public ModelTexture(int id) {
		this.textureID = id;
	}

	/**
	 * Get the Pointer to the Texture this object is referencing
	 * 
	 * @return the texture pointer
	 */
	public int getTextureID() {
		return textureID;
	}
}
