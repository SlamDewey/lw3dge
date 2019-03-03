package graphics;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import graphics.models.RawModel;

/**
 * A class 'loaded' ;) with generic data gathering functions. The main
 * functionality of this class is to parse data into a format that is more
 * easily understandable by OpenGL/GLFW. Therefore it is more of a library than
 * a class, however it must actually be an object and must be instantiated
 * because some methods need an open OpenGL context to function correctly.
 * 
 * This class should not be reused for anything fancy, and therefore I will not
 * spend much time detailing these functions with comments.
 * 
 * @author Jared Massa
 *
 */
public class Loader {

	private static final int BYTES_PER_PIXEL = 4;

	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private static List<Integer> textures = new ArrayList<Integer>();

	/**
	 * Load a texture (.png) from a file
	 * 
	 * @param fileName
	 *            the filename of the .png
	 * @return a pointer to the loaded memory
	 */
	public static int loadTexture(final String fileName) {
		int texture = 0;
		try {
			InputStream IS = Class.class.getResourceAsStream(fileName);
			ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
			int read1 = IS.read();
			while (read1 != -1) {
				BAOS.write(read1);
				read1 = IS.read();
			}
			byte[] textureBA = BAOS.toByteArray();
			BAOS.close();
			BufferedImage textureBI = ImageIO.read(new ByteArrayInputStream(textureBA));
			texture = loadTexture(textureBI);
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		textures.add(texture);
		return texture;
	}

	/*
	 * A helper function
	 */
	private static int loadTexture(BufferedImage image) {
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL);
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF)); // Green component
				buffer.put((byte) (pixel & 0xFF)); // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component.
															// Only for RGBA
			}
		}
		buffer.flip(); // FOR THE LOVE OF GOD DO NOT FORGET THIS
		// You now have a ByteBuffer filled with the color data of each pixel.
		// Now just create a texture ID and bind it. Then you can newTexture it
		// using
		// whatever OpenGL method you want, for example:
		int textureID = GL11.glGenTextures(); // Generate texture ID
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID); // Bind texture ID
		// Setup wrap mode
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		// Setup texture scaling filtering
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		// Send texel data to OpenGL
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, buffer);
		// Return the texture ID so we can bind it later again
		return textureID;
	}

	/**
	 * Take data and store it in a VAO. This function will create the VAO, fill
	 * it with data, unbind the VAO, store the VAO pointer in a RawModel object,
	 * and return the model.
	 * 
	 * @param positions
	 *            the vertex positions of this model
	 * @param textureCoords
	 *            the texture coordinates of this model
	 * @param normals
	 *            the normals for each vertex in this model
	 * @param indices
	 *            the index buffer representing the vertex order and repetition
	 *            in this model
	 * @return the RawModel referencing the generated VAO pointer
	 */
	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}

	/**
	 * Load a VAO for a GUI 2D GUI Element
	 * 
	 * @deprecated until further notice
	 * @param positions
	 *            The GUI coordinates
	 * @return a VAO for this GUI element
	 */
	public RawModel loadToVAO(float[] positions) {
		int vaoID = createVAO();
		this.storeDataInAttributeList(0, 2, positions);
		unbindVAO();
		return new RawModel(vaoID, positions.length / 2);
	}

	/**
	 * TO BE CALLED UPON ENGINE SHUTDOWN. Destroys all VAO and VBO references by
	 * freeing the memory.
	 * 
	 * This will also free memory to all texture pointers.
	 */
	public void cleanUp() {
		for (int a : vaos)
			GL30.glDeleteVertexArrays(a);
		for (int b : vbos)
			GL30.glDeleteVertexArrays(b);
		for (int t : textures)
			GL11.glDeleteTextures(t);
	}

	/*
	 * **************************************
	 * Only Helper Functions Below
	 * *************************************/

	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}

	private int createVBO() {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		return vboID;
	}

	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		int vboID = createVBO();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = CreateFloatBufferOf(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		unbindVBO();
	}

	private FloatBuffer CreateFloatBufferOf(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private void bindIndicesBuffer(int[] indices) {
		int vboID = createVBO();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = CreateIntBufferOf(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		unbindVBO();
	}

	private IntBuffer CreateIntBufferOf(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}

	private void unbindVBO() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
}
