package core;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

/**
 * An individual boid.
 */
public class Boid {

	private PApplet parent_;

	// simple vehicle model parameters
	public PVector position_;
	private float mass_;
	private PVector velocity_;
	private float maxforce_;
	private float maxspeed_;

	// parameters defining the neighborhood
	private float neighborRadius_;
	private float neighborAngle_;

	// action selection
	private Brain brain_;
	private SpaceType spaceType_;
	private World world_; // world the boid belongs to

	// resources
	private PImage comet_;
	private PImage rkt_;
	private PImage alien_;

	/**
	 * Create a new boid with the specified parameters.
	 * 
	 * @param parent
	 * @param world
	 *          the world containing the boid
	 * @param position
	 *          boid's position
	 * @param mass
	 *          boid's mass
	 * @param velocity
	 *          boid's velocity
	 * @param maxforce
	 *          boid's maximum acceleration force
	 * @param maxspeed
	 *          boid's maximum speed
	 * @param neighborRadius
	 *          radius of boid's neighborhood
	 * @param neighborAngle
	 *          boid's field of view
	 * @param brain
	 *          boid's brain (for selecting/combining behaviors)
	 */
	public Boid ( PApplet parent, World world, PVector position, float mass,
	              PVector velocity, float maxforce, float maxspeed,
	              float neighborRadius, float neighborAngle, Brain brain,
	              SpaceType type ) {
		parent_ = parent;
		world_ = world;
		position_ = position;
		mass_ = mass;
		velocity_ = velocity;
		maxforce_ = maxforce;
		maxspeed_ = maxspeed;
		neighborRadius_ = neighborRadius;
		neighborAngle_ = neighborAngle;
		spaceType_ = type;
		setBrain(brain);

	}

	/**
	 * Get the boid's position.
	 * 
	 * @return boid's current position
	 */
	public PVector getPosition () {
		return position_;
	}

	public SpaceType getType () {
		return spaceType_;
	}

	/**
	 * Get the boid's mass.
	 * 
	 * @return boid's mass
	 */
	public float getMass () {
		return mass_;
	}

	/**
	 * Get the boid's velocity.
	 * 
	 * @return boid's current velocity
	 */
	public PVector getVelocity () {
		return velocity_;
	}

	/**
	 * Get the boid's max acceleration force.
	 * 
	 * @return boid's max acceleration force
	 */
	public float getMaxForce () {
		return maxforce_;
	}

	/**
	 * Get the boid's max speed.
	 * 
	 * @return boid's max speed
	 */
	public float getMaxSpeed () {
		return maxspeed_;
	}

	/**
	 * Get the radius of the boid's neighborhood.
	 * 
	 * @return radius of boid's neighborhood
	 */
	public float getNeighborRadius () {
		return neighborRadius_;
	}

	/**
	 * Get the boid's field of view.
	 * 
	 * @return boid's field of view
	 */
	public float getNeighborAngle () {
		return neighborAngle_;
	}

	/**
	 * Determine if 'other' is a neighbor of this boid (i.e. is in this boid's
	 * neighborhood)
	 * 
	 * @param other
	 *          other boid
	 * @return true if 'other' is a neighbor of this boid, false if 'other' is not
	 *         in the neighborhood or if 'other' is the same boid (a boid is not
	 *         its own neighbor)
	 */
	public boolean isNeighbor ( Boid other ) {
		if ( other == this ) {
			return false;
		}

		float dist = PVector.dist(position_,other.position_);
		if ( dist > neighborRadius_ ) {
			return false;
		}

		float angle =
		    PVector.angleBetween(velocity_,PVector.sub(other.position_,position_));
		return PApplet.abs(angle) <= neighborAngle_;
	}

	/**
	 * Move the boid according to its behaviors. Also displays debugging info if
	 * debug mode is on.
	 */
	public void update () {
		// compute and apply the net steering force
		PVector total = brain_.getNetSteeringForce(this,world_);
		PVector accel = PVector.div(total,mass_);
		velocity_.add(accel);
		velocity_.limit(maxspeed_);
		position_.add(velocity_);
	}

	/**
	 * Draw the boid on the screen, using its current position and orientation.
	 */
	public void render () {
		switch ( spaceType_ ) {

		case ROCKET:
			rkt_ = parent_.loadImage("rkt.png");
			parent_.pushMatrix();
			parent_.translate(position_.x,position_.y);
			float rAngle = PApplet.atan2(velocity_.x,velocity_.y);
			if ( PApplet.dist((float) velocity_.x,(float) velocity_.y,
			                  (float) position_.x,(float) position_.y) > 1 ) {
				position_.x += PApplet.cos(rAngle) * 1;
				position_.y += PApplet.sin(rAngle) * 1;
			}
			parent_.rotate(rAngle);
			parent_.image(rkt_,0,0);
			parent_.popMatrix();
			break;

		case COMET:
			comet_ = parent_.loadImage("meteorite.png");
			parent_.pushMatrix();
			parent_.translate(position_.x,position_.y);
			parent_.rotate(PApplet.atan2(velocity_.y,velocity_.x));
			parent_.image(comet_,0,0);
			parent_.popMatrix();
			break;

		case ALIEN:
			parent_.pushMatrix();
			alien_ = parent_.loadImage("ufo.png");
			parent_.translate(position_.x,position_.y);
			float aAngle = PApplet.atan2(position_.x,position_.y);
			if ( PApplet.dist((float) velocity_.x,(float) velocity_.y,
			                  (float) position_.x,(float) position_.y) > 1 ) {
				position_.x += PApplet.cos(aAngle) * 1;
				position_.y += PApplet.sin(aAngle) * 1;
			}

			parent_.image(alien_,0,0);
			parent_.popMatrix();
			break;
		}
	}

	/**
	 * @return the brain
	 */
	public Brain getBrain () {
		return brain_;
	}

	/**
	 * @param brain
	 *          the brain to set
	 */
	public void setBrain ( Brain brain ) {
		brain_ = brain;
	}
}

/// **
// * Move the boid according to its behaviors. Also displays debugging info if
// * debug mode is on.
// */
// public void update () {
// if ( world_.getDebug() ) {
// // draw the neighborhood
// parent_.ellipseMode(PApplet.CENTER);
// parent_.stroke(0);
// parent_.fill(0,10);
//
// parent_.pushMatrix();
// parent_.translate(position_.x,position_.y);
//
// float angle = PApplet.atan2(parent_.mouseY - position_.y,
// parent_.mouseX - position_.x);
// if ( PApplet.dist((float) parent_.mouseX,(float) parent_.mouseY,
// (float) position_.x,(float) position_.y) > 1 ) {
// position_.x += PApplet.cos(angle) * 1;
// position_.y += PApplet.sin(angle) * 1;
// }
//
// parent_.rotate(angle);
// parent_.arc(0,0,neighborRadius_ * 2,neighborRadius_ * 2,-neighborAngle_,
// neighborAngle_,PApplet.PIE);
// parent_.popMatrix();
// }
//
// // compute and apply the net steering force
// PVector total = brain_.getNetSteeringForce(this,world_);
// PVector accel = PVector.div(total,mass_);
// velocity_.add(accel);
// velocity_.limit(maxspeed_);
// position_.add(velocity_);
// }
//
/// **
// * Draw the boid on the screen, using its current position and orientation.
// */
// public void render () {
// // parent_.stroke(255,255,255);
// // parent_.fill(255,255,255);
// counter_++;
// rckt_ = parent_.loadImage("rkt.png");
// float angle = PApplet.atan2(parent_.mouseY - position_.y,
// parent_.mouseX - position_.x);
// if ( PApplet.dist((float) parent_.mouseX,(float) parent_.mouseY,
// (float) position_.x,(float) position_.y) > 1 ) {
// position_.x += PApplet.cos(angle) * 1;
// position_.y += PApplet.sin(angle) * 1;
// }
//
// parent_.pushMatrix();
// parent_.translate(position_.x,position_.y);
//
// parent_.rotate(angle);
// // parent_.triangle(30,0,-30,-5,-30,5);
// parent_.image(rckt_,0,0);
// parent_.popMatrix();
//
// // trying shit
// // parent_.translate(parent_.width / 2 - parent_.width / 2,
// // parent_.height / 2 - parent_.height / 2);
// // parent_.rotate((float) (counter_ * 6.28318 / 360));
// // parent_.translate(-parent_.width / 2,-parent_.height / 2);
// // parent_.image(rckt_,0,0);
//
// }
