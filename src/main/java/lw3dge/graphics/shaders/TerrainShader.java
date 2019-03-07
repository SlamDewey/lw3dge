package lw3dge.graphics.shaders;

import java.util.List;

import lw3dge.components.math.Matrix4f;
import lw3dge.components.math.Vector3f;
import lw3dge.engine.Config;
import lw3dge.game.cameras.Camera;
import lw3dge.graphics.entities.Light;

/**
 * A static shader to shade most objects Credit to ThinMatrix and his OpenGL
 * Tutorials
 * 
 * @author Jared Massa
 */
public class TerrainShader extends ShaderProgram {

	private static final int MAX_LIGHTS = 10;

	// uniforms
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColor[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColor;

	/**
		 * Create an Instance of this Shader
		 */
		public TerrainShader() {
			super(Config.TERRAIN_VERTEX_SHADER_LOCATION, Config.TERRAIN_FRAGMENT_SHADER_LOCATION);
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

		location_lightPosition = new int[MAX_LIGHTS];
		location_lightColor = new int[MAX_LIGHTS];
		location_attenuation = new int[MAX_LIGHTS];
		for (int i = 0; i < MAX_LIGHTS; i++) {
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}

		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_skyColor = super.getUniformLocation("skyColor");
	}

	public void loadSkyColor(float r, float g, float b) {
		super.loadVector(location_skyColor, new Vector3f(r, g, b));
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
	 *            the camera
	 */
	public void loadViewMatrix(Camera camera) {
		super.loadMatrix(location_viewMatrix, camera.getViewMatrix());
	}

	/**
	 * Load a Light into this shader
	 * 
	 * @param light
	 *            the Light
	 */
	public void loadLights(List<Light> lights) {
		for (int i = 0; i < MAX_LIGHTS; i++) {
			if (i < lights.size()) {
				super.loadVector(location_lightPosition[i], lights.get(i).position);
				super.loadVector(location_lightColor[i], lights.get(i).color);
				super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
			} else {
				super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
				super.loadVector(location_lightColor[i], new Vector3f(0, 0, 0));
				super.loadVector(location_attenuation[i], new Vector3f(1, 0, 0));
			}
		}
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
