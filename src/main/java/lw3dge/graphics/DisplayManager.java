package lw3dge.graphics;

import org.lwjgl.Version;

import lw3dge.game.Config;
import lw3dge.game.Log;

/**
 * A class for the rest of the engine to deal with GLFW. This Manager controls
 * most of the elements of the Display class and hosts that functionality as
 * public static wrappers of functionality already defined in the Display class.
 * 
 * @see lw3dge.graphics.Display
 * 
 * @author Jared
 *
 */
public class DisplayManager {
	
	/**
	 * This instance of the Display class and GLFW context.
	 */
	private static Display display;
	/**
	 * The instance of the Loader class, for loading data into VAO's and VBO's.
	 */
	public static Loader loader;
	/**
	 * The instance of the MasterRenderer class for handling render control.
	 */
	private static MasterRenderer mr;

	/**
	 * Initializes important private variables that need special care.
	 */
	public static void init() {
		loader = new Loader();
		mr = new MasterRenderer();
	}

	/**
	 * Initializes the Display and Display Manager.
	 * 
	 * @see lw3dge.graphics.DisplayManager#init()
	 * @see lw3dge.graphics.Display#init()
	 */
	public static void start() {
		Log.println(Config.TITLE + ", Version: " + Config.VERSION);
		Log.println("LWJGL Version: " + Version.getVersion());
		Log.println("Initializing GLFW...");
		display = new Display();
		display.init();
		Log.println("done!");
		Log.println("Initializing Engine Components...");
		init();
		Log.println("done!");
	}

	/**
	 * A static method to begin the render loop
	 * 
	 * @see lw3dge.graphics.Display#loop(Loader, MasterRenderer)
	 */
	public static void loop() {
		display.loop(loader, mr);
	}

	/**
	 * A static method to shut down the display
	 * 
	 * @see lw3dge.graphics.Display#shutdown()
	 */
	public static void shutdown() {
		Log.println("Shutting Down...");
		display.shutdown();
		Log.println("done!\n");
	}

	/**
	 * A public static method to hide the cursor on the display
	 * 
	 * @see lw3dge.graphics.Display#hideCursor()
	 */
	public static void hideCursor() {
		Display.hideCursor();
	}

	/**
	 * A public static method to show the cursor on the display
	 * 
	 * @see lw3dge.graphics.Display#showCursor()
	 */
	public static void showCursor() {
		Display.showCursor();
	}

	/**
	 * A public static method to center the mouse in the window
	 * 
	 * @see lw3dge.graphics.Display#centerCursor()
	 */
	public static void centerCursor() {
		Display.centerCursor();
	}
}
