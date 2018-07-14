package target;

import core.Boid;
import core.Target;
import processing.core.PVector;

/**
 * Another boid as a target.
 */
public class BoidTarget implements Target {

	private Boid boid_; // target boid

	/**
	 * Create a target that is the specified boid.
	 * 
	 * @param boid
	 */
	public BoidTarget ( Boid boid ) {
		boid_ = boid;
	}

	/**
	 * Get the position of the target.
	 * 
	 * @return target position
	 */
	public PVector getPosition () {
		return boid_.getPosition();
	}
}