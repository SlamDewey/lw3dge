package lw3dge.game.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import lw3dge.components.math.Vector2f;
import lw3dge.engine.Config;
import lw3dge.graphics.DisplayManager;

public class Cursor extends GLFWCursorPosCallback {
	
	public static boolean HAS_FOCUS = false;
	
	public static Vector2f position = new Vector2f();
	public static Vector2f translation = new Vector2f();
	private static final Vector2f center = new Vector2f((Config.WIDTH / 2.0f), (Config.HEIGHT / 2.0f));
	
	@Override
	public void invoke(long window, double xPos, double yPos) {
		position.set((float) xPos, (float) yPos);
		Vector2f.sub(position, center, translation);
		
		if (HAS_FOCUS && Config.CENTER_CURSOR_ON_FOCUS) DisplayManager.centerCursor();
	}
	
	public static void gainFocus() {
		if (Config.CENTER_CURSOR_ON_FOCUS)
			DisplayManager.centerCursor();
		translation.set(0, 0);
		HAS_FOCUS = true;
		if (Config.HIDE_CURSOR_ON_FOCUS)
			DisplayManager.hideCursor();
	}
	public static void loseFocus() {
		HAS_FOCUS = false;
		DisplayManager.showCursor();
	}

}
