package behavior;

import core.Behavior;
import core.Boid;
import core.World;
import processing.core.PVector;

/**
 * Cohesion: steer to move towards the center of your neighbors
 */
public class Cohesion extends Behavior {

	/**
	 * @param c
	 *          color to display steering vector (for debug mode)
	 */
	public Cohesion ( int c ) {
		super(c);
	}

	/**
	 * 
	 */
	public Cohesion () {
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
		PVector sum = new PVector(0,0);
		float numNeighbors = 0;
		for ( Boid b : world.getBoids() ) {
			if ( boid.isNeighbor(b) ) {
				sum = PVector.add(sum,b.getPosition());
				numNeighbors++;
			}
		}

		if ( numNeighbors > 0 ) {
			sum = PVector.div(sum,numNeighbors);
			return PVector.sub(sum,boid.getPosition());
		} else {
			return new PVector(0,0);
		}
	}

}
