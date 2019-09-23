package lw3dge.game;

import lw3dge.graphics.DisplayManager;

public abstract class Game {

	public static Scene CURRENT_SCENE;

	/**
	 * Initialize the Original Game Scene
	 */
	private void init() {
		CURRENT_SCENE = new Scene();
	}

	/**
	 * Initializes the Engine. Entry Point defined in Game subclass.
	 * 
	 * @see lw3dge.game.Game#init()
	 * @see lw3dge.graphics.DisplayManager#start()
	 * @see lw3dge.graphics.DisplayManager#loop()
	 * @see lw3dge.graphics.DisplayManager#shutdown()
	 */
	public Game() {
		init();
		DisplayManager.start();
	}

	/**
	 * This function runs the game after the initialization stage. This should
	 * be called after initialization of game subclasses. This function enters
	 * the game loop and handles the ticker thread. It will also handle shutdown
	 * once the GLFW context has been shutdown.
	 * 
	 * @see game.Game#start_ticking()
	 * @see lw3dge.graphics.DisplayManager#loop()
	 * @see game.Game#stop_ticking()
	 * @see lw3dge.graphics.DisplayManager#shutdown()
	 */
	protected void run() {
		setupCamera();
		DisplayManager.loop();
		DisplayManager.shutdown();
	}


	/**
	 * Called from the run() to setup the initial Camera in this Game
	 * Scene
	 * 
	 * @see game.Game#run()
	 */
	protected abstract void setupCamera();
}
