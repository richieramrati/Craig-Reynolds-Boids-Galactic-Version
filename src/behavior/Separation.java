package behavior;

import core.Behavior;
import core.Boid;
import core.World;
import processing.core.PVector;

/**
 * Separation: avoids neighbours within the boid's radius and steers away
 */
public class Separation extends Behavior {

	/**
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Separation ( int c ) {
		super(c);

	}

	/**
	 * 
	 */
	public Separation () {
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
		PVector result = new PVector(0,0);
		float numNeighbors = 0;
		for ( Boid b : world.getBoids() ) {
			if ( boid.isNeighbor(b) ) {
				PVector diff = new PVector(0,0);
				diff = PVector.sub(boid.getPosition(),b.getPosition());
				float dist = 1 / PVector.dist(b.getPosition(),boid.getPosition());
				diff.normalize();
				diff = PVector.mult(diff,dist);
				result = PVector.add(diff,result);
				numNeighbors++;
			}
		}
		if ( numNeighbors > 0 ) {
			return PVector.div(result,numNeighbors);
		} else {
			return new PVector(0,0);
		}
	}

}
