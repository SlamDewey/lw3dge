package game;

import components.math.Vector3f;
import components.physics.Transform;
import game.cameras.FirstPersonControlledCamera;
import graphics.DisplayManager;
import graphics.Loader;
import graphics.OBJLoader;
import graphics.entities.GraphicalEntity;
import graphics.entities.Light;
import graphics.models.RawModel;
import graphics.models.TexturedModel;
import graphics.textures.ModelTexture;

public class TestGame extends Game {
	static RawModel mod;
	static TexturedModel staticModel;
	static ModelTexture texture;


	public TestGame() {
		super();
		// whatever you want here
		mod = OBJLoader.loadObjModel("/object_files/dragon.obj", DisplayManager.loader);
		staticModel = new TexturedModel(mod,
				new ModelTexture(Loader.loadTexture("/textures/blank_texture.png")));
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
