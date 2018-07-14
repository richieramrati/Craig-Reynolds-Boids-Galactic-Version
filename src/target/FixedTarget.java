package target;

import core.Target;
import processing.core.PVector;

/**
 * A fixed target.
 */
class FixedTarget implements Target {

	private PVector pos_; // target position

	/**
	 * Create a fixed target at the specified position.
	 * 
	 * @param pos
	 *          target position
	 */
	public FixedTarget ( PVector pos ) {
		pos_ = pos;
	}

	/**
	 * Create a fixed target at the specified position.
	 * 
	 * @param x
	 *          x coordinate of position
	 * @param y
	 *          y coordinate of position
	 */
	public FixedTarget ( float x, float y ) {
		this(new PVector(x,y));
	}

	/**
	 * Get the position of the target.
	 * 
	 * @return target position
	 */
	public PVector getPosition () {
		return pos_;
	}
}
