package behavior;

import core.Behavior;
import core.Boid;
import core.World;
import processing.core.PVector;

/**
 * Alignment: match velocity of neighbours, desired velocity is the average
 * velocity
 */
public class Alignment extends Behavior {

	/**
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Alignment ( int c ) {
		super(c);

	}

	/**
	 * 
	 */
	public Alignment () {
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
		PVector newVel = new PVector(0,0);
		PVector sum = new PVector(0,0);
		PVector oldVel = boid.getVelocity();
		float numNeighbors = 0;
		for ( Boid b : world.getBoids() ) {
			if ( boid.isNeighbor(b) ) {
				sum = PVector.add(sum,b.getVelocity());
				numNeighbors++;
			}
		}
		if ( numNeighbors > 0 ) {
			newVel = PVector.div(sum,numNeighbors);
			return PVector.sub(newVel,oldVel);
		} else {
			return new PVector(0,0);
		}
	}

}
