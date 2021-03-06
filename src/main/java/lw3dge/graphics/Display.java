package lw3dge.graphics;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import lw3dge.components.LinkedListNode;
import lw3dge.engine.Config;
import lw3dge.engine.Log;
import lw3dge.game.Game;
import lw3dge.game.Scene;
import lw3dge.game.Time;
import lw3dge.game.input.Cursor;
import lw3dge.game.input.Keyboard;
import lw3dge.game.input.Mouse;
import lw3dge.game.input.MousePicker;
import lw3dge.game.input.MouseScroll;
import lw3dge.game.terrain.Terrain;
import lw3dge.graphics.entities.GraphicalEntity;
import lw3dge.graphics.entities.Light;

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
	private static int update_count = 0;
	public static int updates_in_last_second = 0;
	private static int fps_count = 0;
	public static int frames_in_last_second = 0;
	private final long ONE_SECOND = 1_000_000_000; // its in nanoseconds so..
	private final long UPDATE_INTERVAL = ONE_SECOND / Config.UPDATES_PER_SECOND;
	
	public static MousePicker MP;

	/**
	 * Initialize this Display and GLFW Context and attempt to center window
	 * 
	 * @throws IllegalStateException
	 *             upon failed GLFW initialization
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
	 * Enter the graphics loop for this display. We will render entities, poll
	 * events, and swap buffers.  This method is also responsible for timing
	 * itself, so it can set the delta-time value for between frames.
	 * 
	 * @param loader
	 *            the generated {@link Loader} from initialization
	 * @param mr
	 *            the {@link MasterRenderer} from initialization
	 * @see lw3dge.graphics.Loader
	 * @see lw3dge.graphics.MasterRenderer
	 * @see lw3dge.graphics.DisplayManager#init()
	 * @see lw3dge.game.Time
	 */
	void loop(Loader loader, MasterRenderer mr) {
		Scene cur_scene;
		long last_second, now, last_update;
		last_update = 0;
		last_update = last_second = System.nanoTime();
		while (!glfwWindowShouldClose(window)) {
			//setup
			cur_scene = Game.CURRENT_SCENE;
			(MP = new MousePicker(cur_scene.getCamera(), MasterRenderer.projectionMatrix, cur_scene.terrains.get(0))).update();
			now = System.nanoTime();
			if (now - last_update >= UPDATE_INTERVAL) {
				Time.set(now - last_update);
				update_count++;
				last_update += UPDATE_INTERVAL;
				cur_scene.tick();
			}
			//graphics updates
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, Config.POLYGON_MODE);
			for (Terrain t : cur_scene.terrains)
				mr.processTerrain(t);
			LinkedListNode<GraphicalEntity> cur_entity = cur_scene.firstEntity;
			while (cur_entity != null) {
				mr.processEntity(cur_entity.getContent());
				cur_entity = cur_entity.next;
			}
			for (Light light : cur_scene.lights)
				mr.processLight(light);
			mr.render();
			//setup for next loop
			glfwPollEvents();
			glfwSwapBuffers(window);
			fps_count++;
			//debug logging
			if (Config.DEBUG_UPS_FPS) {
				if (now - last_second > ONE_SECOND) {
					last_second = now;
					updates_in_last_second = update_count;
					frames_in_last_second = fps_count;
					update_count = 0;
					fps_count = 0;
					Log.println("UPS: " + updates_in_last_second);
					Log.println("FPS: " + frames_in_last_second);
				}
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