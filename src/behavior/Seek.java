package behavior;

import core.Behavior;
import core.Boid;
import core.Target;
import core.World;
import processing.core.PVector;

/**
 * Seek: match position with a target, with the boid traveling at max speed.
 */
public class Seek extends Behavior {

	private Target target_; // target to seek

	/**
	 * Create a seek behavior with the specified target.
	 * 
	 * @param target
	 *          seek target
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Seek ( Target target, int c ) {
		super(c);
		target_ = target;
	}

	/**
	 * @param velocity
	 */
	public Seek ( PVector velocity ) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get the seek steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the steering force for the specified boid
	 */
	public static PVector getSteeringForce ( Boid boid, PVector target ) {
		PVector diff = PVector.sub(target,boid.getPosition());
		diff.normalize();
		PVector desiredvel = PVector.mult(diff,boid.getMaxSpeed());
		return PVector.sub(desiredvel,boid.getVelocity());
	}

	/**
	 * Get the seek steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the steering force for the specified boid
	 */
	public PVector getSteeringForce ( Boid boid, World world ) {
		PVector diff = PVector.sub(target_.getPosition(),boid.getPosition());
		diff.normalize();
		PVector desiredvel = PVector.mult(diff,boid.getMaxSpeed());
		return PVector.sub(desiredvel,boid.getVelocity());
	}

}
