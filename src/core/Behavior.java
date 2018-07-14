package core;

import processing.core.PVector;

/**
 * A behavior that results in a steering force.
 */
public abstract class Behavior {

	private int color_; // color to use for displaying debugging info

	/**
	 * Create a behavior.
	 * 
	 * @param c
	 *          color to use for the debugging display
	 */
	protected Behavior ( int c ) {
		color_ = c;
	}

	protected Behavior () {}

	/**
	 * Get the color to use for the debugging display.
	 * 
	 * @return color to use for the debugging display
	 */
	public int getColor () {
		return color_;
	}

	/**
	 * Get the steering force for the specified boid according to this behavior.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the steering force for the specified boid
	 */
	public abstract PVector getSteeringForce ( Boid boid, World world );
}
