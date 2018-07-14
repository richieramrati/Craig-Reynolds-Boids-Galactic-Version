package behavior;

import brain.CometGravitationField;
import core.Behavior;
import core.Boid;
import core.World;
import processing.core.PVector;

/**
 * @author Richie
 */
public class FollowField extends Behavior {
	private static CometGravitationField gField_;

	/**
	 * @param c
	 */
	public FollowField ( CometGravitationField f ) {
		gField_ = f;
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see core.Behavior#getSteeringForce(core.Boid, core.World)
	 */
	@Override
	public PVector getSteeringForce ( Boid boid, World world ) {
		PVector desired = gField_.find(boid.getPosition());
		desired.mult(boid.getMaxSpeed());
		PVector steer = PVector.sub(desired,boid.getVelocity());
		steer.limit(boid.getMaxForce());
		return steer;
	}

}
