package resloaders;

import lw3dge.graphics.models.TexturedModel;

public class TexturedModels {
	public static TexturedModel DRAGON;
	public static TexturedModel TREE;
	public static TexturedModel BUNNY;
	public static TexturedModel CAPSULE;
	public static TexturedModel AIMER;
	
	public static void init() {
		DRAGON = new TexturedModel(Models.DRAGON_MODEL, Textures.BLANK_TEXTURE);
		TREE = new TexturedModel(Models.TREE_MODEL, Textures.TREE_TEXTURE);
		BUNNY = new TexturedModel(Models.BUNNY_MODEL, Textures.RAINBOW_TEXTURE);
		CAPSULE = new TexturedModel(Models.CAPSULE_MODEL, Textures.RAINBOW_TEXTURE);
		AIMER = new TexturedModel(Models.AIMER_MODEL, Textures.BLANK_TEXTURE);
	}
}
