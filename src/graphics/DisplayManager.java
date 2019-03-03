package graphics;

import org.lwjgl.Version;

import game.Config;

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
	 * @see graphics.DisplayManager#init()
	 * @see graphics.Display#init()
	 */
	public static void start() {
		System.out.println(Config.TITLE + ", Version: " + Config.VERSION);
		System.out.println("LWJGL Version: " + Version.getVersion());
		System.out.print("Initializing GLFW...");
		display = new Display();
		display.init();
		System.out.print("done!\n");
		System.out.print("Initializing Engine Components...");
		init();
		System.out.print("done!\n");
	}

	/**
	 * A static method to begin the render loop
	 * 
	 * @see graphics.Display#loop(Loader, MasterRenderer)
	 */
	public static void loop() {
		display.loop(loader, mr);
	}

	/**
	 * A static method to shut down the display
	 * 
	 * @see graphics.Display#shutdown()
	 */
	public static void shutdown() {
		System.out.print("Shutting Down...");
		display.shutdown();
		System.out.println("done!\n");
	}

	/**
	 * A public static method to hide the cursor on the display
	 * 
	 * @see graphics.Display#hideCursor()
	 */
	public static void hideCursor() {
		Display.hideCursor();
	}

	/**
	 * A public static method to show the cursor on the display
	 * 
	 * @see graphics.Display#showCursor()
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
