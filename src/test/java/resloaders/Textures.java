package resloaders;

import lw3dge.engine.Config;
import lw3dge.graphics.Loader;
import lw3dge.graphics.textures.ModelTexture;

public class Textures {

	public static ModelTexture BLANK_TEXTURE;
	public static ModelTexture TREE_TEXTURE;
	public static ModelTexture GRASS_TEXTURE;
	public static ModelTexture RAINBOW_TEXTURE;
	
	
	public static void init() {
		BLANK_TEXTURE = new ModelTexture(Loader.loadTexture(Config.ABS_RESOURCE_PATH + "textures/new_emote.png"));
		BLANK_TEXTURE.shineDamper = 3.5f;
		BLANK_TEXTURE.reflectivity = 1.5f;
		TREE_TEXTURE = new ModelTexture(Loader.loadTexture(Config.ABS_RESOURCE_PATH + "textures/tree.png"));
		GRASS_TEXTURE = new ModelTexture(Loader.loadTexture(Config.ABS_RESOURCE_PATH + "textures/grass.png"));
		RAINBOW_TEXTURE = new ModelTexture(Loader.loadTexture(Config.ABS_RESOURCE_PATH + "textures/rainbow.png"));
	}
}
