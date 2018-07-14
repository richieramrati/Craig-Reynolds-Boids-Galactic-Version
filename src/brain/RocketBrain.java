package brain;

import java.util.ArrayList;
import java.util.List;

import core.Behavior;
import core.Boid;
import core.Brain;
import core.SpaceType;
import core.World;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author Richie
 */
public class RocketBrain implements Brain {
	private List<RocketBehavior> behaviors_;
	private PApplet p_;

	public RocketBrain ( PApplet parent ) {
		p_ = parent;
		behaviors_ = new ArrayList<RocketBehavior>();
	}

	/*
	 * (non-Javadoc)
	 * @see core.Brain#addBehavior(core.Behavior, float)
	 */
	@Override
	public void addBehavior ( Behavior behavior, float weight ) {
		// TODO Auto-generated method stub
		behaviors_.add(new RocketBehavior(behavior,weight));
	}

	/*
	 * (non-Javadoc)
	 * @see core.Brain#getNetSteeringForce(core.Boid, core.World)
	 */
	@Override
	public PVector getNetSteeringForce ( Boid boid, World world ) {
		// TODO Auto-generated method stub
		for ( Boid b : world.getBoids() ) {
			if ( boid.isNeighbor(b) ) {
				shootProjectile(boid);
			}
		}
		PVector total = new PVector(0,0);
		for ( RocketBehavior behavior : behaviors_ ) {
			PVector steering = behavior.getSteeringForce(boid,world);
			steering.normalize();
			steering.mult(behavior.weight_);
			total.add(steering);
		}
		total.limit(boid.getMaxForce());
		return total;
	}

	/**
	 * 
	 */
	private void shootProjectile ( Boid b ) {
		Projectile laser = new Projectile(b.getPosition(),b.getVelocity(),p_);
		laser.update();
		laser.display();
	}

	/**
	 * Associate a weight with a behavior.
	 */
	class RocketBehavior {
		float weight_; // weight applied to this behavior's steering force
		Behavior behavior_; // the behavior

		/**
		 * Create a WeightedBehavior with the specified behavior and weight.
		 * 
		 * @param behavior
		 * @param weight
		 */
		RocketBehavior ( Behavior behavior, float weight ) {
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
