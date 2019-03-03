package lw3dge.graphics.shaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import lw3dge.components.math.Matrix4f;
import lw3dge.components.math.Vector3f;

/**
 * Represents a basic ShaderProgram and all abstracted necessities 
 * Credit to ThinMatrix and his OpenGL Tutorials
 * 
 * @author Jared
 */
public abstract class ShaderProgram {

	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;

	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

	/**
	 * Create a ShaderProgram Instance with the defined vertex and fragment
	 * shaders.
	 * 
	 * @param vertexFile
	 *            the filename of the vertex shader
	 * @param fragmentFile
	 *            the filename of the fragment shader
	 */
	public ShaderProgram(String vertexFile, String fragmentFile) {
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}

	/**
	 * Called from the SuperClass constructor in ShaderProgram. Defined by
	 * children to load uniforms faster with nice organization.
	 */
	protected abstract void getAllUniformLocations();

	/**
	 * Find the location of a variable (uniform) in a shader program
	 * 
	 * @param uniformName
	 *            the string name of the variable
	 * @return a pointer to this uniform
	 */
	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(programID, uniformName);
	}

	/**
	 * Load a Float into a specified location in the shader program
	 * 
	 * @param location
	 *            a pointer to the location within the program
	 * @param value
	 *            the Float value to load
	 */
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}

	/**
	 * Load a Vector into a specified location in the shader program
	 * 
	 * @param location
	 *            a pointer to the location within the program
	 * @param value
	 *            the Vector value to load
	 */
	protected void loadVector(int location, Vector3f value) {
		GL20.glUniform3f(location, value.x, value.y, value.z);
	}

	/**
	 * Load a boolean into a specified location in the shader program
	 * 
	 * @param location
	 *            a pointer to the location within the program
	 * @param value
	 *            the boolean value to load
	 */
	protected void loadBoolean(int location, boolean value) {
		GL20.glUniform1f(location, value ? 1 : 0);
	}

	/**
	 * Load a 4x4 Matrix into a specified location in the shader program
	 * 
	 * @param location
	 *            a pointer to the location within the program
	 * @param matrix
	 *            the 4x4 matrix to inject
	 */
	protected void loadMatrix(int location, Matrix4f matrix) {
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4fv(location, false, matrixBuffer);
	}

	/**
	 * Enable this shader program
	 */
	public void start() {
		GL20.glUseProgram(programID);
	}

	/**
	 * Disable this shader program
	 */
	public void stop() {
		GL20.glUseProgram(0);
	}

	/**
	 * Free the memory storing this shader program
	 */
	public void cleanUp() {
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}

	/**
	 * An abstract bindAttributes for overriding
	 */
	protected abstract void bindAttributes();

	/**
	 * Bind attributes to a shader file
	 * 
	 * @param attribute
	 *            the index of the VAO to bind to
	 * @param variableName
	 *            the title of this VAO Attribute
	 */
	protected void bindAttributes(int attribute, String variableName) {
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}

	/**
	 * Load A Shader file and compile it.
	 * 
	 * @param file
	 *            the filename
	 * @param type
	 *            the type of file using OpenGL constants
	 * @return the pointer to this compiled shader
	 */
	private static int loadShader(String file, int type) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			File f = new File(file);
			FileInputStream fr = new FileInputStream(f);
			InputStreamReader isr = new InputStreamReader(fr);
			BufferedReader reader = new BufferedReader(isr);
			String line;
			while ((line = reader.readLine()) != null)
				shaderSource.append(line).append("\n");
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read shader file!");
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader.");
			System.exit(-1);
		}
		return shaderID;
	}
}
