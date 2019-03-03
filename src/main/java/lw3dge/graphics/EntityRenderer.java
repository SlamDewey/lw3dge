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
import lw3dge.graphics.shaders.StaticShader;
import lw3dge.graphics.textures.ModelTexture;

public class EntityRenderer {

	private StaticShader shader;
	
	public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	/**
	 * Prepare this renderer for a render frame (OpenGL stuff)
	 */
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.4f, 0.0f, 0.0f, 1);
	}

	/**
	 * Render a set of entities based on the textured model of each entity
	 * 
	 * @param entities
	 *            a HashMap using TexturedModels as the key (to be rendered)
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
	 * **************************************
	 * Only Helper Functions Below
	 * *************************************/
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
