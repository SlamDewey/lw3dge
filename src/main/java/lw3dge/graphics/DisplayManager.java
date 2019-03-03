package lw3dge.graphics;

import org.lwjgl.Version;

import lw3dge.game.Config;
import lw3dge.game.Log;

public class DisplayManager {

	private static Display display;
	public static Loader loader;
	private static MasterRenderer mr;

	/**
	 * Initializes important private variables that need special care.
	 */
	public static void init() {
		loader = new Loader();
		mr = new MasterRenderer();
	}

	/**
	 * Initializes the Display and Display Manager
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
	 * @see graphics.Display#centerCursor();
	 */
	public static void centerCursor() {
		Display.centerCursor();
	}
}
