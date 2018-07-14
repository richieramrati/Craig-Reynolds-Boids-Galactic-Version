package core;

import java.util.ArrayList;
import java.util.List;

/**
 * The world.
 */
public class World {

	private List<Boid> boids_; // boids in the world

	private boolean debug_; // if true, show debugging display

	/**
	 * Create an empty world.
	 */
	public World () {
		boids_ = new ArrayList<Boid>();
		debug_ = false;
	}

	/**
	 * Add a boid to the world.
	 * 
	 * @param boid
	 *          boid to add
	 */
	public void addBoid ( Boid boid ) {
		boids_.add(boid);
	}

	/**
	 * Get the boids in the world.
	 * 
	 * @return the boids in the world
	 */
	public Iterable<Boid> getBoids () {
		return boids_;
	}

	/**
	 * Get the debug status.
	 * 
	 * @return true if in debugging mode, false otherwise
	 */
	public boolean getDebug () {
		return debug_;
	}

	/**
	 * Set the debug status.
	 * 
	 * @param debug
	 *          status value to set
	 */
	public void setDebug ( boolean debug ) {
		debug_ = debug;
	}
}