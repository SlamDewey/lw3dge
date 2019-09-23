package lw3dge.engine;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL11;

public class Config {
	/*
	 * *********************************************************************
	 * Log Settings
	 ***********************************************************************/
	// the setting for the log output, can be:
	// LOG_SIMPLE, LOG_OFF, LOG_VERBOSE
	public static int DEBUG_MODE = Log.LOG_SIMPLE;
	/*
	 * *********************************************************************
	 * Client Application Settings
	 ***********************************************************************/
	// Title of the Client Application
	public static final String TITLE = "LW3DGE Test";
	// the version Client Application
	public static final double VERSION = 0.1;
	// for math calcuations
	public static final float EPSILON = 0.0001f;
	/*
	 * *********************************************************************
	 * Projection Matrix Settings
	 ***********************************************************************/
	// The field of view for the projection matrix.
	public static final float FOV = 70;
	// the nearest point that can be seen from the projection matrix
	public static final float NEAR_PLANE = 0.01f;
	// the maximum distance that the projection matrix will bother with
	public static final float FAR_PLANE = 10000f;

	/*
	 * *********************************************************************
	 * GLFW and OpenGL Constants / Settings
	 ***********************************************************************/
	// Should the window be resizable? GLFW_TRUE : GLFW_FALSE
	public static final int IS_RESIZABLE = GLFW_FALSE;
	// Should the window be visible? GLFW_TRUE : GLFW_FALSE
	public static final int IS_VISIBLE = GLFW_TRUE;
	// Window Width
	public static final int WIDTH = 1600;
	// Window Height
	public static final int HEIGHT = 900;
	// Enable or disable synchronization to monitor refresh frequency.
	public static final int VSYNC = GLFW_TRUE;
	// A constant integer used for scaling the mip-map blur over distance
	public static final int MIP_MAP_CONSTANT = -1;
	// render objects as wire frames or not.  GL11.GL_LINE : GL11.GL_FILL
	public static int POLYGON_MODE = GL11.GL_FILL;
	/*
	 * *********************************************************************
	 * Absolute and relative file paths for the Engine
	 ***********************************************************************/
	//absolute path to this project
	public static final String ABS_PROJ_PATH = "Z:/game/lw3dge/";
	//relative path to the LW3DGE code
	public static final String REL_ENG_PATH = "src/main/java/lw3dge/";
	//absolute engine path
	public static final String ABS_ENGINE_PATH = ABS_PROJ_PATH + REL_ENG_PATH;
	public static final String TERRAIN_VERTEX_SHADER_LOCATION = Config.ABS_ENGINE_PATH
			+ "graphics/shaders/terrainVertexShader.txt";
	public static final String TERRAIN_FRAGMENT_SHADER_LOCATION = Config.ABS_ENGINE_PATH
			+ "graphics/shaders/terrainFragmentShader.txt";
	public static final String ENTITY_VERTEX_SHADER_LOCATION = Config.ABS_ENGINE_PATH
			+ "graphics/shaders/vertexShader.txt";
	public static final String ENTITY_FRAGMENT_SHADER_LOCATION = Config.ABS_ENGINE_PATH
			+ "graphics/shaders/fragmentShader.txt";
	/*
	 * *********************************************************************
	 * Absolute and relative file paths for the Client Application
	 ***********************************************************************/
	//absolute path to the resource folder FOR CLIENT
	public static final String ABS_RESOURCE_PATH = ABS_PROJ_PATH + "src/test/resources/";

}
