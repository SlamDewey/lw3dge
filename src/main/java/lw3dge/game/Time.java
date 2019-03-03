package lw3dge.game;

/**
 * This class is a crude Time-Keeping class and should be pretty easy to read.
 * 
 * Note: Nearly 99% of the time all of these values will be exactly the same.
 * The Updates per Second goal is set in the Config class, so use these mainly to
 * set a constant pace for object velocities and etc.
 * 
 * @author Jared Massa
 */
public class Time {
	public static double deltaTimeMillis = 0.0d;
	public static long deltaTimeNano = 0L;
	public static float deltaTime = 0.0f;
	
	public static void set(long timeDif) {
		deltaTimeNano = timeDif;
		deltaTimeMillis = (timeDif / 1_000_000);
		deltaTime = (float) (deltaTimeMillis / 1_000);
	}
}
