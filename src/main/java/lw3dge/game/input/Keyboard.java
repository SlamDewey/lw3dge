package lw3dge.game.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL11;

import lw3dge.engine.Config;
import lw3dge.engine.Log;

public class Keyboard extends GLFWKeyCallback {
	public static boolean[] keys = new boolean[500];

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if (key < keys.length && key > -1)
			keys[key] = action != GLFW.GLFW_RELEASE;
		if (key == GLFW.GLFW_KEY_ESCAPE)
			Cursor.loseFocus();
		if (key == GLFW.GLFW_KEY_F1) {
			if (action != GLFW.GLFW_RELEASE)
				Config.POLYGON_MODE = GL11.GL_LINE;
			else Config.POLYGON_MODE = GL11.GL_FILL;
		}
	}
}
