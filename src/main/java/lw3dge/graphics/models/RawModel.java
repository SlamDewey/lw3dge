package lw3dge.graphics.models;

/**
 * This class represents a VAO as a Java Object Notice this class is FINAL and
 * therefore NOT editable after instantiation.
 * 
 * @author Jared Massa
 */
public final class RawModel {
	
	/**
	 * The pointer to the VAO for OpenGL
	 */
	private int vaoID;
	/**
	 * The length of a portion in the VAO buffer
	 */
	private int vertexCount;

	/**
	 * Create a new RawModel to reference a specified VAO
	 * 
	 * @param vaoID
	 *            the VAO pointer
	 * @param vertexCount
	 *            the vertex count of this model
	 */
	public RawModel(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	/**
	 * Get the VAO pointer
	 * 
	 * @return the VAO Pointer
	 */
	public int getVaoID() {
		return vaoID;
	}

	/**
	 * Get the vertex count
	 * 
	 * @return the vertex count
	 */
	public int getVertexCount() {
		return vertexCount;
	}
}
