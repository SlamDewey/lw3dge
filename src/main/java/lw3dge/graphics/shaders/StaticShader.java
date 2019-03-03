package lw3dge.graphics.shaders;

import lw3dge.components.math.Matrix4f;
import lw3dge.components.physics.Transform;
import lw3dge.game.Config;
import lw3dge.graphics.entities.Light;
import lw3dge.graphics.math.Maths;

/**
 * A static shader to shade most objects 
 * Credit to ThinMatrix and his OpenGL
 * Tutorials
 * 
 * @author Jared Massa
 */
public class StaticShader extends ShaderProgram {
	// file locations
	private static final String VERTEX_FILE = Config.ABS_ENGINE_PATH + "graphics/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = Config.ABS_ENGINE_PATH + "graphics/shaders/fragmentShader.txt";
	// uniforms
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColor;
	private int location_shineDamper;
	private int location_reflectivity;

	/**
	 * Create an Instance of this Shader
	 */
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	/**
	 * Binds all VAO Attributes in this shader program
	 * 
	 * @see lw3dge.graphics.shaders.ShaderProgram#bindAttributes()
	 */
	@Override
	protected void bindAttributes() {
		super.bindAttributes(0, "position");
		super.bindAttributes(1, "textureCoords");
		super.bindAttributes(2, "normal");
	}

	/**
	 * Defines all uniform locations in this shader program. Called from the
	 * superclass
	 * 
	 * @see lw3dge.graphics.shaders.ShaderProgram#getAllUniformLocations()
	 */
	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightColor = super.getUniformLocation("lightColor");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
	}

	/**
	 * Load a transformation matrix for an object
	 * 
	 * @param matrix
	 *            the transformation matrix
	 */
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	/**
	 * Load the projection matrix for this game (created in initialization)
	 * 
	 * @param matrix
	 *            the projection matrix
	 */
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}

	/**
	 * Load the view matrix for this camera
	 * 
	 * @param camera
	 *            the transform of the current camera
	 */
	public void loadViewMatrix(Transform camera) {
		super.loadMatrix(location_viewMatrix, Maths.createViewMatrix(camera));
	}

	/**
	 * Load a Light into this shader
	 * 
	 * @param light
	 *            the Light
	 */
	public void loadLight(Light light) {
		super.loadVector(location_lightPosition, light.position);
		super.loadVector(location_lightColor, light.color);
	}

	/**
	 * Load shine characteristics of a texture
	 * 
	 * @param d
	 *            the diffuse constant
	 * @param r
	 *            the reflectivity constant
	 */
	public void loadShine(float d, float r) {
		super.loadFloat(location_shineDamper, d);
		super.loadFloat(location_reflectivity, r);
	}
}
