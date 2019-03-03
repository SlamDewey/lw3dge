package game;

import graphics.DisplayManager;

public abstract class Game {

	public static Scene CURRENT_SCENE;
	public static boolean RUNNING = false;

	private TickThread ticker;

	/**
	 * Initialize the Original Game Scene and ticker thread
	 */
	private void init() {
		CURRENT_SCENE = new Scene();
		ticker = new TickThread("Engine Scene Updater");
	}

	/**
	 * Initializes the Engine. Entry Point defined in Game subclass.
	 * 
	 * @see game.Game#init()
	 * @see graphics.DisplayManager#start()
	 * @see graphics.DisplayManager#loop()
	 * @see graphics.DisplayManager#shutdown()
	 */
	public Game() {
		init();
		DisplayManager.start();
		setupCamera();
	}

	/**
	 * This function runs the game after the initialization stage. This should
	 * be called after initialization of game subclasses. This function enters
	 * the game loop and handles the ticker thread. It will also handle shutdown
	 * once the GLFW context has been shutdown.
	 * 
	 * @see game.Game#start_ticking()
	 * @see graphics.DisplayManager#loop()
	 * @see game.Game#stop_ticking()
	 * @see graphics.DisplayManager#shutdown()
	 */
	protected void run() {
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
			System.err.println("Could not join Ticker Thread - Interrupted");
			e.printStackTrace();
		}
	}

	/**
	 * Called from the Game constructor to setup the initial Camera in this Game
	 * Scene
	 * 
	 * @see game.Game#Game()
	 */
	protected abstract void setupCamera();
}
