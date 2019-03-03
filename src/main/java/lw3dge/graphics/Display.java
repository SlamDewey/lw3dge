package lw3dge.graphics;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import lw3dge.game.Config;
import lw3dge.game.Game;
import lw3dge.game.input.Cursor;
import lw3dge.game.input.Keyboard;
import lw3dge.game.input.Mouse;
import lw3dge.game.input.MouseScroll;
import lw3dge.graphics.entities.GraphicalEntity;

/**
 * A class to directly deal with GLFW. All functions in this class are declared
 * with package visibility, but are redeclared in the DisplayManager as static
 * helper functions.
 * 
 * @see lw3dge.graphics.DisplayManager
 * 
 * @author Jared Massa
 */
public class Display {
	private static long window;

	/**
	 * Initialize this Display and GLFW Context and attempt to center window
	 * 
	 * @throws IllegalStateException
	 *             upon failed GLFW_INIT
	 * @throws RuntimeException
	 *             upon failed window pointer creation
	 */
	void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, Config.IS_VISIBLE);
		glfwWindowHint(GLFW_RESIZABLE, Config.IS_RESIZABLE);
		window = glfwCreateWindow(Config.WIDTH, Config.HEIGHT, Config.TITLE, NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		// Generate and set IO interrupt call-backs
		Keyboard kInput = new Keyboard();
		Mouse mInput = new Mouse();
		Cursor cInput = new Cursor();
		MouseScroll sInput = new MouseScroll();
		glfwSetKeyCallback(window, kInput);
		glfwSetMouseButtonCallback(window, mInput);
		glfwSetCursorPosCallback(window, cInput);
		glfwSetScrollCallback(window, sInput);

		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			glfwGetWindowSize(window, pWidth, pHeight);
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			// center window on primary monitor
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}
		glfwMakeContextCurrent(window);
		glfwSwapInterval(Config.VSYNC);
		glfwShowWindow(window);
		GL.createCapabilities();
	}

	/**
	 * Enter the graphics loop for this display
	 * 
	 * @param loader
	 *            the generated Loader from initialization
	 * @param mr
	 *            the MasterRenderer from initialization
	 * @see lw3dge.graphics.Loader
	 * @see lw3dge.graphics.MasterRenderer
	 * @see lw3dge.graphics.DisplayManager#init()
	 */
	void loop(Loader loader, MasterRenderer mr) {
		int count = 0;
		double lastTime = 0.0d;
		while (!glfwWindowShouldClose(window)) {
			for (GraphicalEntity e : Game.CURRENT_SCENE.entities)
				mr.processEntity(e);
			if (Game.CURRENT_SCENE.light != null)
				mr.processLight(Game.CURRENT_SCENE.light);
			mr.render();
			glfwPollEvents();
			glfwSwapBuffers(window);
			count++;
			if (glfwGetTime() - lastTime > 1.0) {
				lastTime = glfwGetTime();
				System.out.println("Updated: " + count + " FPS");
				count = 0;
			}
		}

		mr.cleanUp();
		loader.cleanUp();
	}

	/**
	 * Hide Cursor within GLFW window
	 */
	static void hideCursor() {
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
	}

	/**
	 * Show Cursor within GLFW window
	 */
	static void showCursor() {
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
	}

	/**
	 * Center Cursor within GLFW window
	 */
	static void centerCursor() {
		glfwSetCursorPos(window, Config.WIDTH / 2, Config.HEIGHT / 2);
	}

	/**
	 * Destroy this GLFW context and free window pointer
	 */
	void shutdown() {
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
}