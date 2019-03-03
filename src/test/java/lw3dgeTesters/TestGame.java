package lw3dgeTesters;

import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;
import lw3dge.game.Config;
import lw3dge.game.Game;
import lw3dge.game.cameras.FirstPersonControlledCamera;
import lw3dge.graphics.DisplayManager;
import lw3dge.graphics.Loader;
import lw3dge.graphics.OBJLoader;
import lw3dge.graphics.entities.GraphicalEntity;
import lw3dge.graphics.entities.Light;
import lw3dge.graphics.models.RawModel;
import lw3dge.graphics.models.TexturedModel;
import lw3dge.graphics.textures.ModelTexture;

public class TestGame extends Game {
	static RawModel mod;
	static TexturedModel staticModel;
	static ModelTexture texture;


	public TestGame() {
		super();
		// whatever you want here
		mod = OBJLoader.loadObjModel(Config.ABS_PROJ_PATH + "/resources/object_files/dragon.obj", DisplayManager.loader);
		staticModel = new TexturedModel(mod,
				new ModelTexture(Loader.loadTexture(Config.ABS_PROJ_PATH + "/resources/textures/blank_texture.png")));
		texture = staticModel.getTexture();
		texture.shineDamper = 10;
		texture.reflectivity = 1;
		for (int i = 0; i < 10; i++) {
			generate_obj();
		}
		Light light = new Light(new Vector3f(0, 50, -30), new Vector3f(1, 1, 1));
		Game.CURRENT_SCENE.setGlobalLight(light);
		
		run();
	}
	
	public static void generate_obj() {
		Transform t;
		t = new Transform(new Vector3f((float) (Math.random() * 100.0f) - 50, 0, -80), 0);
		Game.CURRENT_SCENE.addEntity(new GraphicalEntity(staticModel, t, new Vector3f(1, 1, 1)));
	}

	public static void main(String[] args) {
		new TestGame();
	}

	@Override
	protected void setupCamera() {
		Game.CURRENT_SCENE.setCamera(new FirstPersonControlledCamera(new Transform()));
	}
}
