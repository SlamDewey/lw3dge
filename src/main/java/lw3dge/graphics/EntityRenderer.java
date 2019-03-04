package lw3dge.graphics;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import lw3dge.components.math.Matrix4f;
import lw3dge.components.math.Vector3f;
import lw3dge.graphics.entities.GraphicalEntity;
import lw3dge.graphics.math.Maths;
import lw3dge.graphics.models.RawModel;
import lw3dge.graphics.models.TexturedModel;
import lw3dge.graphics.shaders.EntityShader;
import lw3dge.graphics.textures.ModelTexture;

/**
 * A rendering class controlled by the MasterRenderer for rendering
 * GraphicalEntities to the screen.
 * 
 * @see lw3dge.graphics.MasterRenderer
 * 
 * @author Jared Massa
 *
 */
public class EntityRenderer {

	/**
	 * The shader for entities
	 */
	private EntityShader shader;

	/**
	 * Generate a new EntityRenderer with a given shader program and projection
	 * matrix.
	 * 
	 * @param shader
	 *            the shader program instructions on drawing entities.
	 * @param projectionMatrix
	 *            a 4x4 matrix for calculating depth projections.
	 */
	public EntityRenderer(EntityShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	/**
	 * Render a set of entities based on the textured model of each entity
	 * 
	 * @param entities
	 *            a HashMap using TexturedModels as the key -(to be rendered)
	 */
	public void render(Map<TexturedModel, List<GraphicalEntity>> entities) {
		for (TexturedModel model : entities.keySet()) {
			prepareTexturedModel(model);
			List<GraphicalEntity> batch = entities.get(model);
			for (GraphicalEntity entity : batch) {
				prepareInstance(entity.getPosition(), entity.getRotation(), entity.getScale());
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
	}

	/*
	 * Only Helper Functions Below
	 *************************************/

	private void prepareTexturedModel(TexturedModel texturedModel) {
		RawModel rawModel = texturedModel.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTexture t = texturedModel.getTexture();
		shader.loadShine(t.shineDamper, t.reflectivity);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
	}

	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	private void prepareInstance(Vector3f obj_position, Vector3f obj_rotation, Vector3f scale) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(obj_position, obj_rotation, scale);
		shader.loadTransformationMatrix(transformationMatrix);
	}
}
