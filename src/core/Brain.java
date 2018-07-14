package core;

import processing.core.PVector;

/**
 * A boid brain takes in information from the environment and produces an action
 * in the form of a net steering force.
 */
public interface Brain {

	/**
	 * Get the net steering force for the specified boid.
	 * 
	 * @param boid
	 *          the boid
	 * @param world
	 *          the world containing the boid
	 * @return
	 * @return net steering force for the specified boid
	 */
	public void addBehavior ( Behavior behavior, float weight );

	public PVector getNetSteeringForce ( Boid boid, World world );
}