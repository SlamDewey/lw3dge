package lw3dge.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import lw3dge.components.math.Matrix4f;
import lw3dge.components.physics.Transform;
import lw3dge.engine.Config;
import lw3dge.game.Game;
import lw3dge.game.terrain.Terrain;
import lw3dge.graphics.entities.GraphicalEntity;
import lw3dge.graphics.entities.Light;
import lw3dge.graphics.models.TexturedModel;
import lw3dge.graphics.shaders.EntityShader;
import lw3dge.graphics.shaders.TerrainShader;

/**
 * A class to track all in-game objects to be rendered to the screen. The render
 * queues must be rebuilt every frame.
 * 
 * @author Jared Massa
 */
public class MasterRenderer {
	
	private static final float RED = 0.4f;
	private static final float GREEN = 0.4f;
	private static final float BLUE = 0.4f;
	
	private Matrix4f projectionMatrix;

	private EntityShader entityShader;
	private EntityRenderer entityRenderer;
	
	private TerrainShader terrainShader;
	private TerrainRenderer terrainRenderer;

	private Map<TexturedModel, List<GraphicalEntity>> entities = new HashMap<TexturedModel, List<GraphicalEntity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	private List<Light> lights = new ArrayList<Light>();

	/**
	 * Creates a new initialized MasterRenderer. Must be called after
	 * GL.createCapabilities() is called, during an active OpenGL context.
	 * 
	 * @see lw3dge.graphics.Display#init()
	 */
	public MasterRenderer() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		createProjectionMatrix();
		entityShader = new EntityShader();
		terrainShader = new TerrainShader();
		entityRenderer = new EntityRenderer(entityShader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		
	}

	/**
	 * Renders all elements known to the MasterRenderer and then clears the
	 * render queues.
	 */
	public void render() {
		Transform cameraT = Game.CURRENT_SCENE.getCameraTransform();
		// prepare to render this frame
		prepare();
		entityShader.start();
		entityShader.loadSkyColor(RED, GREEN, BLUE);
		// load camera view projection
		entityShader.loadViewMatrix(cameraT);
		// load all known lights for entity illumination calculations
		entityShader.loadLights(lights);
		// render entities
		entityRenderer.render(entities);
		// shutdown and wipe for next frame
		entityShader.stop();
		
		terrainShader.start();
		terrainShader.loadSkyColor(RED, GREEN, BLUE);
		terrainShader.loadLights(lights);
		terrainShader.loadViewMatrix(cameraT);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		
		lights.clear();
		entities.clear();
		terrains.clear();
	}

	/**
	 * Adds the specified entity into the entity list for rendering this frame.
	 * Entities stored in each frame are stored in a hash map using the texture
	 * as a key.
	 * 
	 * @param entity
	 *            the {@link GraphicalEntity} to be rendered
	 */
	public void processEntity(GraphicalEntity entity) {
		TexturedModel texMod = entity.getTexturedModel();
		List<GraphicalEntity> batch = entities.get(texMod);
		if (batch != null)
			batch.add(entity);
		else {
			List<GraphicalEntity> newBatch = new ArrayList<GraphicalEntity>();
			newBatch.add(entity);
			entities.put(texMod, newBatch);
		}
	}
	
	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}

	/**
	 * Adds a light into the render queue.
	 * 
	 * @param light
	 *            the {@link Light} object to render
	 */
	public void processLight(Light light) {
		lights.add(light);
	}

	/**
	 * Creates a projection matrix for 3D depth perception based on
	 * Configuration Data
	 * 
	 * @see lw3dge.engine.Config
	 */
	private void createProjectionMatrix() {
		float FOV = Config.FOV;
		float FAR_PLANE = Config.FAR_PLANE;
		float NEAR_PLANE = Config.NEAR_PLANE;

		float aspectRatio = (float) Config.WIDTH / (float) Config.HEIGHT;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}
	
	/**
	 * Prepare this renderer for a render frame (OpenGL stuff)
	 */
	private void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
	}

	public void cleanUp() {
		entityShader.cleanUp();
		terrainShader.cleanUp();
	}
}
