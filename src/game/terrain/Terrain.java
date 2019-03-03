package game.terrain;

import graphics.Loader;
import graphics.models.RawModel;
import graphics.textures.ModelTexture;

public class Terrain {
	
	private static final float SIZE = 800;
	private static final int VERTEX_COUNT = 128;
	
	private float x, z;
	private RawModel model;
	private ModelTexture texture;
	
	public Terrain(int x, int z, Loader l, ModelTexture texture) {
		this.texture = texture;
		this.x = x * SIZE;
		this.z = z * SIZE;
		this.model = generateTerrain(l);
	}
	
	private RawModel generateTerrain(Loader l) {
		return null;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public RawModel getModel() {
		return model;
	}

	public ModelTexture getTexture() {
		return texture;
	}
}
