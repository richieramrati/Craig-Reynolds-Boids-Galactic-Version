package core;

import processing.core.PVector;

/**
 * A target for use in behaviors like seek.
 */
public interface Target {

	/**
	 * Get the position of the target.
	 * 
	 * @return target position
	 */
	public PVector getPosition ();
}
