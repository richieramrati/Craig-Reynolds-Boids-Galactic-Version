package behavior;

import core.Behavior;
import core.Boid;
import core.World;
import processing.core.PVector;

/**
 * Forward: desired velocity is in the same direction as the current velocity
 * with max speed
 */
public class Forward extends Behavior {

	/**
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Forward ( int c ) {
		super(c);
	}

	/**
	 * 
	 */
	public Forward () {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the seek steering force for the specified boid behavior.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the steering force for the specified boid behavior
	 */

	public PVector getSteeringForce ( Boid boid, World world ) {
		PVector newVel = boid.getVelocity();
		newVel.normalize();
		newVel = PVector.mult(newVel,boid.getMaxSpeed());
		return PVector.sub(newVel,boid.getVelocity());
	}
}
