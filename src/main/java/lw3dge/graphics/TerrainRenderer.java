package lw3dge.graphics;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import lw3dge.components.math.Matrix4f;
import lw3dge.game.terrain.Terrain;
import lw3dge.graphics.models.RawModel;
import lw3dge.graphics.shaders.TerrainShader;
import lw3dge.graphics.textures.ModelTexture;

public class TerrainRenderer {

	private TerrainShader shader;

	public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	int LOD = 0;
	
	public void render(List<Terrain> terrains) {
		for (Terrain t : terrains) {
			prepareTerrain(t);
			shader.loadTransformationMatrix(t.getTransformationMatrix());
			GL11.glDrawElements(GL11.GL_TRIANGLES, t.getModel().getVertexCount(LOD), GL11.GL_UNSIGNED_INT, 0);			///
			unbindTexturedModel();
		}
	}

	/*
	 * Only Helper Functions Below
	 *************************************/

	private void prepareTerrain(Terrain terrain) {
		RawModel rawModel = terrain.getModel();
		GL30.glBindVertexArray(rawModel.getVaoID(LOD));						///
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTexture t = terrain.getTexture();
		shader.loadShine(t.shineDamper, t.reflectivity);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, t.getTextureID());
	}

	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
}
