package brain;

import java.util.ArrayList;
import java.util.List;

import behavior.Seek;
import core.Behavior;
import core.Boid;
import core.Brain;
import core.SpaceType;
import core.World;
import processing.core.PVector;

/**
 * @author Richie
 */
public class AlienBrain implements Brain {

	private List<AlienBehavior> behaviors_;

	public AlienBrain () {
		behaviors_ = new ArrayList<AlienBehavior>();
	}

	/**
	 * Add a behavior with the specified weight.
	 * 
	 * @param behavior
	 *          behavior to add
	 * @param weight
	 *          weight for the behavior
	 */
	public void addBehavior ( Behavior behavior, float weight ) {
		// TODO Auto-generated method stub
		behaviors_.add(new AlienBehavior(behavior,weight));
	}

	/**
	 * Get the net steering force for the specified boid. The magnitude of the
	 * force returned is limited by the boid's max force.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 */
	public PVector getNetSteeringForce ( Boid boid, World world ) {
		for ( Boid b : world.getBoids() ) {
			if ( boid.isNeighbor(b) && b.getType().equals(SpaceType.ROCKET) ) {
				Seek.getSteeringForce(boid,b.getPosition());
			}
		}
		PVector total = new PVector(0,0);
		for ( AlienBehavior behavior : behaviors_ ) {
			PVector steering = behavior.getSteeringForce(boid,world);
			steering.normalize();
			steering.mult(behavior.weight_);
			total.add(steering);
		}
		total.limit(boid.getMaxForce());
		return total;
	}

	/**
	 * Associate a weight with a behavior.
	 */
	class AlienBehavior {
		float weight_; // weight applied to this behavior's steering force
		Behavior behavior_; // the behavior

		/**
		 * Create a WeightedBehavior with the specified behavior and weight.
		 * 
		 * @param behavior
		 * @param weight
		 */
		AlienBehavior ( Behavior behavior, float weight ) {
			behavior_ = behavior;
			weight_ = weight;
		}

		/**
		 * Get the steering force for the specified boid according to this behavior.
		 * 
		 * @param boid
		 *          the boid
		 * @param world
		 *          the world containing the boid
		 * @return the steering force for the specified boid, scaled according to
		 *         its weight
		 */
		PVector getSteeringForce ( Boid boid, World world ) {
			return PVector.mult(behavior_.getSteeringForce(boid,world),weight_);
		}
	}
}
