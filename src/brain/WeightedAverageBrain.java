package brain;

import java.util.ArrayList;
import java.util.List;

import core.Behavior;
import core.Boid;
import core.Brain;
import core.World;
import processing.core.PVector;

/**
 * The net steering force is a weighted average of individual steering forces.
 */

public class WeightedAverageBrain implements Brain {

	private List<WeightedBehavior> behaviors_; // applicable behaviors

	/**
	 * Create a brain to average steering forces.
	 * 
	 * @param parent
	 */
	public WeightedAverageBrain () {
		behaviors_ = new ArrayList<WeightedBehavior>();
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
		behaviors_.add(new WeightedBehavior(behavior,weight));
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
		PVector total = new PVector(0,0);
		for ( WeightedBehavior behavior : behaviors_ ) {
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
	class WeightedBehavior {
		float weight_; // weight applied to this behavior's steering force
		Behavior behavior_; // the behavior

		/**
		 * Create a WeightedBehavior with the specified behavior and weight.
		 * 
		 * @param behavior
		 * @param weight
		 */
		WeightedBehavior ( Behavior behavior, float weight ) {
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
