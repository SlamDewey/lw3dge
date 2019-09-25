package lw3dge.graphics.models;


/**
 * This class represents a VAO as a Java Object Notice this class is FINAL and
 * therefore NOT editable after instantiation.
 * 
 * @author Jared Massa
 */
public final class RawModel {
	public static final int LOD_COUNT = 8;
	
	/**
	 * The pointer to the VAO for OpenGL
	 */
	private int[] vaoIDs;
	/**
	 * The length of a portion in the VAO buffer
	 */
	private int[] vertexCounts;
	
	public RawModel() {
		this.vaoIDs = new int[LOD_COUNT];
		this.vertexCounts = new int[LOD_COUNT];
	}

	/**
	 * Create a new RawModel to reference a specified VAO
	 * 
	 * @param vaoID
	 *            the VAO pointer
	 * @param vertexCount
	 *            the vertex count of this model
	 */
	public RawModel(int[] vaoIDs, int[] vertexCounts) {
		this.vaoIDs = vaoIDs;
		this.vertexCounts = vertexCounts;
	}
	
	public void addVAO(int vaoID, int vertexCount) {
		for (int i = 0; i < LOD_COUNT - 1; i++) {
			if (vaoIDs[i] == 0) {
				vaoIDs[i] = vaoID;
				vertexCounts[i] = vertexCount;
				return;
			}
		}
	}

	/**
	 * Get the VAO pointer
	 * 
	 * @return the VAO Pointer
	 */
	public int getVaoID(int LOD) {
		return vaoIDs[LOD];
	}

	/**
	 * Get the vertex count
	 * 
	 * @return the vertex count
	 */
	public int getVertexCount(int LOD) {
		return vertexCounts[LOD];
	}
}
