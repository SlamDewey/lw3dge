package lw3dge.game;

import lw3dge.engine.Config;
import lw3dge.engine.Log;

/**
 * A separate thread used to tick the world implemented by extending a thread
 * 
 * @author Jared Massa
 */
public class TickThread extends Thread implements Runnable {

	public static int UPS_GOAL = Config.UPS_GOAL; // # of times to update object
													// positions per second
	private static int UP_COUNT = 0;
	public static int UPDATES_IN_LAST_SECOND = 0;

	private final long one_second = 1_000_000_000; // its in nanoseconds so..
	private final long update_interval = (one_second / UPS_GOAL);
	long last_time;
	long last_second;

	/**
	 * Generates a new TickThread with a given thread name.
	 * 
	 * @param name
	 *            the name of this TickThread
	 */
	public TickThread(String name) {
		super(name);
	}

	/**
	 * The work for this thread to do. It will tick the scene at a constant rate
	 * as long as Game.RUNNING is true.
	 * 
	 * @see lw3dge.game.Scene#tick()
	 */
	@Override
	public void run() {
		last_time = last_second = System.nanoTime();
		while (Game.RUNNING) {
			long now = System.nanoTime();
			if (now - last_time >= update_interval) {
				Time.set(now - last_time);
				last_time = now;
				UP_COUNT++;
				if (Game.CURRENT_SCENE != null)
					Game.CURRENT_SCENE.tick();
			}
			if (now - last_second > one_second) {
				last_second = now;
				UPDATES_IN_LAST_SECOND = UP_COUNT;
				UP_COUNT = 0;
				Log.println("UPS: " + UPDATES_IN_LAST_SECOND);
			}
		}
	}
}
