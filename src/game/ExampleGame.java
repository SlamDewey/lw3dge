package game;

import game.cameras.Camera;
import game.cameras.FirstPersonControlledCamera;

/**
 * This class serves as an example for how children of the Game Class should be
 * structured. Notice no functions are implemented, treat this closer to
 * English, not Java.
 * 
 * As well, Other than these two/three major rules you must follow, you can do
 * whatever else you want in this class without breaking anything.
 * 
 * @author Jared Massa
 *
 */
public abstract class ExampleGame extends Game {

	/**
	 * As a class extension, you are forced to call super(), so consider
	 * yourself lucky I don't have to explain how important it is!
	 * 
	 * In terms of the other instructions in this constructor, treat them as
	 * English (as stated above) except for run(). Run() is a method defined in
	 * the super class Game, and MUST be called at the end of your constructor.
	 * (This is what actually starts up the game after initialization!)
	 * 
	 * @see game.Game#run()
	 */
	public ExampleGame() {
		super();
		init_textures_and_models();
		generate_initial_game_objects();
		run();
	}

	/**
	 * Override the setupCamera function from the Game class. This is called
	 * upon game initialization and is VERY IMPORTANT. Without setting up a
	 * camera the engine will crash, so at the very least generate a camera with
	 * no Transform data until you can do better. Check the code inside the
	 * method for examples.
	 * 
	 * @see game.Game#setupCamera()
	 * 
	 * @see game.cameras.Camera
	 */
	@Override
	protected void setupCamera() {
		// consider this:
		Game.CURRENT_SCENE.setCamera(new FirstPersonControlledCamera());
		// here's an example line you might even write!
		Game.CURRENT_SCENE.setCamera(generateCamera());
		// or if you really don't know what you want and would rather decide
		// later, create a camera with nothing special happening:
		Game.CURRENT_SCENE.setCamera(new Camera());
	}

	// these were the things I had to write to make it look like English..
	protected abstract void init_textures_and_models();

	protected abstract void generate_initial_game_objects();

	protected abstract Camera generateCamera();
}
