package target;

import core.Target;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * The mouse position as a target.
 */
public class MouseTarget implements Target {

	private PApplet parent_;

	/**
	 * Create a target which is the mouse position.
	 * 
	 * @param parent
	 */
	public MouseTarget ( PApplet parent ) {
		parent_ = parent;
	}

	/**
	 * Get the position of the target.
	 * 
	 * @return target position
	 */
	public PVector getPosition () {
		return new PVector(parent_.mouseX,parent_.mouseY);
	}
}