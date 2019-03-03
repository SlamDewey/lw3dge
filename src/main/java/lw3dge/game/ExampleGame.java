package lw3dge.game;

import lw3dge.game.cameras.Camera;
import lw3dge.game.cameras.FirstPersonControlledCamera;

/**
 * This class serves as an example for how children of the Game Class should be
 * structured. Notice no functions are implemented, treat this closer to
 * English, not Java.
 * 
 * As well, Other than these three major rules you must follow, you can do
 * whatever else you want in this class without breaking anything.
 * 
 * @author Jared Massa
 *
 */
public abstract class ExampleGame extends Game {
	/**
	 * Entry point for the engine should look like this: (my compiler won't let
	 * me write this because ExampleGame is abstract..)
	 * 
	 * where ExampleGame is the name of your game subclass
	 */
	/*
	 * public static void main(String[] args) { new ExampleGame(); }
	 */

	/*
	 * As a class extension, you are forced to call super(), so consider
	 * yourself lucky I don't have to explain how important it is!
	 *
	 * In terms of the other instructions in this constructor, treat them as
	 * English (as stated above) except for run(). Run() is a method defined in
	 * the super class Game, and MUST be called at the end of your constructor.
	 * (This is what actually starts up the game after initialization!)
	 */
	/**
	 * This Constructor serves as an example on formatting Game Subclass
	 * Constructors. Read the code.
	 * 
	 * @see lw3dge.game.Game#run()
	 */
	public ExampleGame() {
		super(); // yeah you gotta do this
		init_textures_and_models(); // I recommend to do this statically in
									// another class and just call Models.init()
									// for example.
		generate_initial_game_objects(); // use your loaded models to generate
											// some game objects/entities
		run(); // you need to call this method, or else the GLFW context will
				// realize it isn't looping and end program
	}

	/*
	 * This is called upon game initialization and is VERY IMPORTANT. Without
	 * setting up a camera the engine will crash, so at the very least generate
	 * a camera with no Transform data until you can do better. Check the code
	 * inside the method for examples.
	 */
	/**
	 * Override the setupCamera function from the Game class.
	 * 
	 * @see lw3dge.game.Game#setupCamera()
	 * 
	 * @see lw3dge.game.cameras.Camera
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
