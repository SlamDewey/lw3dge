package resloaders;

import lw3dge.engine.Config;
import lw3dge.graphics.DisplayManager;
import lw3dge.graphics.OBJLoader;
import lw3dge.graphics.models.RawModel;

public class Models {
	

	public static RawModel DRAGON_MODEL;
	public static RawModel TREE_MODEL;
	public static RawModel BUNNY_MODEL;
	
	public static void init() {
		DRAGON_MODEL = OBJLoader.loadObjModel(Config.ABS_RESOURCE_PATH + "object_files/dragon.obj", DisplayManager.loader);
		TREE_MODEL = OBJLoader.loadObjModel(Config.ABS_RESOURCE_PATH + "object_files/tree.obj", DisplayManager.loader);
		BUNNY_MODEL = OBJLoader.loadObjModel(Config.ABS_RESOURCE_PATH + "object_files/bunny.obj", DisplayManager.loader);
	}
}
