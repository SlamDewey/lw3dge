package lw3dge.game;

import lw3dge.engine.Log;
import lw3dge.engine.Log.LogLevel;
import lw3dge.graphics.DisplayManager;

public abstract class Game {

	public static Scene CURRENT_SCENE;
	public static boolean RUNNING = false;

	private TickThread ticker;

	/**
	 * Initialize the Original Game Scene and ticker thread
	 */
	private void init() {
		CURRENT_SCENE = new Scene();
		ticker = new TickThread("Engine Ticker");
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
		start_ticking();
		DisplayManager.loop();
		stop_ticking();
		DisplayManager.shutdown();
	}

	/**
	 * Starts the ticking thread
	 * 
	 * @see game.TickThread#run
	 */
	private synchronized void start_ticking() {
		RUNNING = true;
		ticker.start();
	}

	/**
	 * Joins the ticking thread with the main thread
	 */
	private synchronized void stop_ticking() {
		RUNNING = false;
		try {
			ticker.join();
		} catch (InterruptedException e) {
			Log.println(LogLevel.FATAL, "Couldn't join ticker thread!");
			e.printStackTrace();
		}
	}

	/**
	 * Called from the run() to setup the initial Camera in this Game
	 * Scene
	 * 
	 * @see game.Game#run()
	 */
	protected abstract void setupCamera();
}
