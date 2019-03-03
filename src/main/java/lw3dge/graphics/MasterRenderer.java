package lw3dge.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import lw3dge.components.math.Matrix4f;
import lw3dge.game.Config;
import lw3dge.game.Game;
import lw3dge.graphics.entities.GraphicalEntity;
import lw3dge.graphics.entities.Light;
import lw3dge.graphics.models.TexturedModel;
import lw3dge.graphics.shaders.StaticShader;

/**
 * A class to track all in-game objects to be rendered to the screen. The render
 * queues must be rebuilt every frame.
 * 
 * @author Jared Massa
 */
public class MasterRenderer {

	private Matrix4f projectionMatrix;

	private StaticShader shader;
	private EntityRenderer renderer;

	private Map<TexturedModel, List<GraphicalEntity>> entities = new HashMap<TexturedModel, List<GraphicalEntity>>();
	private List<Light> lights = new ArrayList<Light>();

	/**
	 * Creates a new initialized MasterRenderer. Must be called after
	 * GL.createCapabilities() is called.
	 * 
	 * @see lw3dge.graphics.Display#init()
	 */
	public MasterRenderer() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		createProjectionMatrix();
		shader = new StaticShader();
		renderer = new EntityRenderer(shader, projectionMatrix);
	}

	/**
	 * Renders all elements known to the MasterRenderer and then clears the
	 * render queues.
	 */
	public void render() {
		// prepare to render this frame
		renderer.prepare();
		shader.start();

		// load camera view projection
		shader.loadViewMatrix(Game.CURRENT_SCENE.getCameraTransform());
		// load all known lights for entity illumination calculations
		for (Light light : lights)
			shader.loadLight(light);
		// render entities
		renderer.render(entities);

		// shutdown and wipe for next frame
		shader.stop();
		lights.clear();
		entities.clear();
	}

	/**
	 * Adds the specified entity into the entity list for rendering this frame.
	 * Entities stored in each frame are stored in a hash map using the texture
	 * as a key.
	 * 
	 * @param entity
	 *            the entity to be rendered
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

	/**
	 * Adds a light into the render queue.
	 * 
	 * @param light
	 *            the light object to render
	 */
	public void processLight(Light light) {
		lights.add(light);
	}

	/**
	 * Creates a projection matrix for 3D depth perception based on
	 * Configuration Data
	 * 
	 * @see game.Config
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

	public void cleanUp() {
		shader.cleanUp();
	}
}
