package lw3dgeTesters;

import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;
import lw3dge.engine.Log;
import lw3dge.game.Game;
import lw3dge.game.cameras.ThirdPersonCamera;
import lw3dge.game.terrain.Terrain;
import lw3dge.graphics.DisplayManager;
import lw3dge.graphics.entities.GraphicalEntity;
import lw3dge.graphics.entities.Light;
import resloaders.Models;
import resloaders.TexturedModels;
import resloaders.Textures;

public class TestGame extends Game {
	
	
	static Terrain terrain;
	static Transform t;

	public TestGame() {
		super();
		init_models_and_textures();
		
		for (int i = 0; i < 20; i++)
			generate_dragon();
		for (int i = 0; i < 400; i++)
			generate_tree();
		
		set_lights();
	}
	private void init_models_and_textures() {
		
		Models.init();
		Textures.init();
		TexturedModels.init();
		
		terrain = new Terrain(-1, -1, DisplayManager.loader, Textures.GRASS_TEXTURE);
		Game.CURRENT_SCENE.addTerrain(terrain);
		terrain = new Terrain(-0, -1, DisplayManager.loader, Textures.GRASS_TEXTURE);
		Game.CURRENT_SCENE.addTerrain(terrain);
		Log.println("Finished Initializing Models");
	}
	
	public static void generate_dragon() {
		t = new Transform(new Vector3f((float) (Math.random() * -200.0f) + 100f, 0, (float) (Math.random() * -100.0f)), 0);
		Game.CURRENT_SCENE.addEntity(new GraphicalEntity(TexturedModels.DRAGON, t, new Vector3f(1, 1, 1)));
	}
	private void generate_tree() {
		t = new Transform(new Vector3f((float) (Math.random() * 1600f) - 800f, 0, (float) (Math.random() * -800f)), 0);
		Game.CURRENT_SCENE.addEntity(new GraphicalEntity(TexturedModels.TREE, t, new Vector3f(10, 10, 10)));
	}
	private void set_lights() {
		Light sun = new Light(new Vector3f(0, 1000, -50), new Vector3f(.8f, .8f, .8f));
		Light red = new Light(new Vector3f(50, 1, -80), new Vector3f(1, 0, 0), new Vector3f(1, 0.001f, 0.002f));
		Light green = new Light(new Vector3f(0, 1, -80), new Vector3f(0, 1, 0), new Vector3f(1, 0.001f, 0.002f));
		Light blue = new Light(new Vector3f(-50, 1, -80), new Vector3f(0, 0, 1), new Vector3f(1, 0.001f, 0.002f));
		Light red_green = new Light(new Vector3f(25, 1, -100), new Vector3f(1, 1, 0), new Vector3f(1, 0.001f, 0.002f));
		Light blue_green = new Light(new Vector3f(-25, 1, -100), new Vector3f(0, 1, 1), new Vector3f(1, 0.001f, 0.002f));
		Light white = new Light(new Vector3f(-0, 1, -60), new Vector3f(1, 1, 1), new Vector3f(1, 0.001f, 0.002f));
		Game.CURRENT_SCENE.addLight(sun);
		Game.CURRENT_SCENE.addLight(red);
		Game.CURRENT_SCENE.addLight(green);
		Game.CURRENT_SCENE.addLight(blue);
		Game.CURRENT_SCENE.addLight(red_green);
		Game.CURRENT_SCENE.addLight(blue_green);
		Game.CURRENT_SCENE.addLight(white);
	}

	@Override
	protected void setupCamera() {
		ControlledEntity focus = new ControlledEntity(new Transform(new Vector3f(0, 0, -20), 1));
		Game.CURRENT_SCENE.addEntity(focus);
		//Game.CURRENT_SCENE.setCamera(new FirstPersonControlledCamera());
		//Game.CURRENT_SCENE.setCamera(new ThirdPersonControlledCamera(focus.transform));
		Game.CURRENT_SCENE.setCamera(new ThirdPersonCamera(focus.transform));
	}
	
	public static void main(String[] args) {
		TestGame game = new TestGame();
		game.run();
	}
}
