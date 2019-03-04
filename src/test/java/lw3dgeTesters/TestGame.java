package lw3dgeTesters;

import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;
import lw3dge.game.Config;
import lw3dge.game.Game;
import lw3dge.game.Log;
import lw3dge.game.cameras.FirstPersonControlledCamera;
import lw3dge.game.terrain.Terrain;
import lw3dge.graphics.DisplayManager;
import lw3dge.graphics.Loader;
import lw3dge.graphics.OBJLoader;
import lw3dge.graphics.entities.GraphicalEntity;
import lw3dge.graphics.entities.Light;
import lw3dge.graphics.models.RawModel;
import lw3dge.graphics.models.TexturedModel;
import lw3dge.graphics.textures.ModelTexture;

public class TestGame extends Game {
	
	static RawModel dragon_model;
	static ModelTexture blank_texture;
	static TexturedModel dragon;
	
	static RawModel tree_model;
	static ModelTexture tree_texture;
	static TexturedModel tree;
	
	static ModelTexture grass;
	static Terrain terrain;

	public TestGame() {
		super();
		
		init_models_and_textures();
		
		for (int i = 0; i < 20; i++) {
			generate_dragon();
		}
		for (int i = 0; i < 400; i++) {
			generate_tree();
		}
		Light light1 = new Light(new Vector3f(0, 500, -30), new Vector3f(.4f, .4f, .4f));
		Light light2 = new Light(new Vector3f(50, 1, -80), new Vector3f(10, 0, 0), new Vector3f(1, 0.001f, 0.002f));
		Light light3 = new Light(new Vector3f(0, 1, -80), new Vector3f(0, 10, 0), new Vector3f(1, 0.001f, 0.002f));
		Light light4 = new Light(new Vector3f(-50, 1, -80), new Vector3f(0, 0, 10), new Vector3f(1, 0.001f, 0.002f));
		Game.CURRENT_SCENE.addLight(light1);
		Game.CURRENT_SCENE.addLight(light2);
		Game.CURRENT_SCENE.addLight(light3);
		Game.CURRENT_SCENE.addLight(light4);
		
		run();
	}
	private void init_models_and_textures() {
		dragon_model = OBJLoader.loadObjModel(Config.ABS_RESOURCE_PATH + "object_files/dragon.obj", DisplayManager.loader);
		dragon = new TexturedModel(dragon_model,
				new ModelTexture(Loader.loadTexture(Config.ABS_RESOURCE_PATH + "textures/blank_texture.png")));
		blank_texture = dragon.getTexture();
		blank_texture.shineDamper = 10;
		blank_texture.reflectivity = 1;
		
		tree_model = OBJLoader.loadObjModel(Config.ABS_RESOURCE_PATH + "object_files/tree.obj", DisplayManager.loader);
		tree_texture = new ModelTexture(Loader.loadTexture(Config.ABS_RESOURCE_PATH + "textures/blank_texture.png"));
		tree = new TexturedModel(tree_model, tree_texture);
		
		grass = new ModelTexture(Loader.loadTexture(Config.ABS_RESOURCE_PATH + "textures/blank_texture.png"));
		terrain = new Terrain(-1, -1, DisplayManager.loader, grass);
		Game.CURRENT_SCENE.addTerrain(terrain);
		terrain = new Terrain(-0, -1, DisplayManager.loader, grass);
		Game.CURRENT_SCENE.addTerrain(terrain);
		Log.println("Finished Initializing Models");
	}
	
	public static void generate_dragon() {
		Transform t;
		t = new Transform(new Vector3f((float) (Math.random() * -200.0f) + 100f, 0, (float) (Math.random() * -100.0f)), 0);
		Game.CURRENT_SCENE.addEntity(new GraphicalEntity(dragon, t, new Vector3f(1, 1, 1)));
	}
	private static void generate_tree() {
		Transform t;
		t = new Transform(new Vector3f((float) (Math.random() * 1600f) - 800f, 0, (float) (Math.random() * -800f)), 0);
		Game.CURRENT_SCENE.addEntity(new GraphicalEntity(tree, t, new Vector3f(10, 10, 10)));
	}

	@Override
	protected void setupCamera() {
		Game.CURRENT_SCENE.setCamera(new FirstPersonControlledCamera(new Transform()));
	}
	
	public static void main(String[] args) {
		new TestGame();
	}
}
