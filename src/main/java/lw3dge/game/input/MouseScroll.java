package lw3dge.game.input;

import org.lwjgl.glfw.GLFWScrollCallbackI;

public class MouseScroll implements GLFWScrollCallbackI {
	
	public static int SCROLL_EVENT_ID = 0;
	public static double dx, dy;

	/*
	 * @see org.lwjgl.glfw.GLFWScrollCallbackI#invoke(long, double, double)
	 */
	@Override
	public void invoke(long window, double xOffset, double yOffset) {
		dx = xOffset;
		dy = yOffset;
		SCROLL_EVENT_ID = (SCROLL_EVENT_ID + 1) % 5;
	}
}
