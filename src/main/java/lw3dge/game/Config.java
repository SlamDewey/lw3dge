package lw3dge.game;

import static org.lwjgl.glfw.GLFW.*;

public class Config {
	
	public static final boolean DEBUG_MODE = true;
	public static final double VERSION = 0.1;
	
	public static final int UPS_GOAL = 120;
	public static final int VSYNC = 1;
	
	public static final float FOV = 70;
	public static final float NEAR_PLANE = 0.01f;
	public static final float FAR_PLANE = 1000f;
	

	public static final int IS_RESIZABLE = GLFW_TRUE;
	public static final int IS_VISIBLE = GLFW_TRUE;
	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;
	public static final String TITLE = "Colony Engine";
	public static final String ABS_PROJ_PATH = "Z:/game/ColonyEngine";
}
