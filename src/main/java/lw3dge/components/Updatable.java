package lw3dge.components;

/**
 * This interface give a base for all classes that require mathematical updates
 * to function in the game engine. Tick methods are called in order of scope.
 * (Zoomed out to Zoomed in)
 * 
 * @author Jared Massa
 *
 */
public abstract interface Updatable {
	/**
	 * A method all classes must define for themselves when they implement this
	 * interface. This method should be filled with the calculations that must
	 * be done every frame update of the game engine. (If you didn't figure it
	 * out, Tick() is short hand for Update())
	 */
	public abstract void tick();
}
