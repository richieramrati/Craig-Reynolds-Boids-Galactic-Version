package behavior;

import core.Behavior;
import core.Boid;
import core.Target;
import core.World;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Arrive behavior - match position with a target, ending with the boid at rest.
 */
public class Arrive extends Behavior {

	private Target target_; // target to arrive at
	private float threshold_; // begin slowing within threshold

	/**
	 * Create a behavior to arrive at the specified target. Within the threshold,
	 * the boid slows.
	 * 
	 * @param target
	 *          arrival target
	 * @param threshold
	 *          arrival threshold (boid slows near the target)
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Arrive ( Target target, float threshold, int c ) {
		super(c); // debug color is red
		target_ = target;
		threshold_ = threshold;
	}

	/**
	 * Get the arrival steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return the steering force for the specified boid
	 */
	public PVector getSteeringForce ( Boid boid, World world ) {
		PVector targetpos = target_.getPosition();
		PVector diff = PVector.sub(targetpos,boid.getPosition());
		diff.normalize();

		float speed; // scale factor for desired velocity
		// distance to target
		float d = PApplet.dist(boid.getPosition().x,boid.getPosition().y,
		                       targetpos.x,targetpos.y);
		if ( d > threshold_ ) {
			speed = boid.getMaxSpeed();
		} else {
			// scale the distance boid is from the target into the range 0-maxspeed
			speed = PApplet.map(d,0,threshold_,0,boid.getMaxSpeed());
		}
		PVector desiredvel = PVector.mult(diff,speed);
		return PVector.sub(desiredvel,boid.getVelocity());
	}
}