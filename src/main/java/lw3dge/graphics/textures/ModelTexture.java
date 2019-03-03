package lw3dge.graphics.textures;

/**
 * Represents the Texture a Model by referencing the pointer
 * 
 * @author Jared Massa
 */
public class ModelTexture {
	protected int textureID;
	
	public float shineDamper = 1;
	public float reflectivity = 0;

	/**
	 * Create a new ModelTexture
	 * @param id the ID (pointer) to the texture
	 */
	public ModelTexture(int id) {
		this.textureID = id;
	}
	/**
	 * Get the Pointer to the Texture
	 * @return the texture pointer
	 */
	public int getTextureID() {
		return textureID;
	}
}
