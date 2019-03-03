package lw3dge.game;

import static org.lwjgl.glfw.GLFW.*;

public class Config {

	public static int DEBUG_MODE = Log.LOG_SIMPLE;
	public static final double VERSION = 0.1;
	
	public static final int UPS_GOAL = 120;
	public static final int VSYNC = 1;
	public static final int MIP_MAP_CONSTANT = -1;
	
	public static final float FOV = 70;
	public static final float NEAR_PLANE = 0.01f;
	public static final float FAR_PLANE = 1000f;
	

	public static final int IS_RESIZABLE = GLFW_TRUE;
	public static final int IS_VISIBLE = GLFW_TRUE;
	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;
	public static final String TITLE = "LW3DGE";
	public static final String ABS_PROJ_PATH = "Z:/game/lw3dge/";
	public static final String REL_PROJ_PATH = "src/main/java/lw3dge/";
	public static final String ABS_ENGINE_PATH = ABS_PROJ_PATH + REL_PROJ_PATH;
	public static final String ABS_RESOURCE_PATH = ABS_PROJ_PATH + "src/main/resources/";
}
