package behavior;

import core.Behavior;
import core.Boid;
import core.Path;
import core.World;
import processing.core.PVector;

/**
 * @author Richie
 */
public class FollowPath extends Behavior {
	Path path_;

	/**
	 * @param p
	 */
	public FollowPath ( Path p ) {
		path_ = p;
	}

	/*
	 * (non-Javadoc)
	 * @see core.Behavior#getSteeringForce(core.Boid, core.World)
	 */
	public PVector getSteeringForce ( Boid boid, World world ) {

		PVector predictedVel = boid.getVelocity();
		predictedVel.normalize();
		predictedVel.mult(boid.getMaxSpeed());
		PVector predictedPos = PVector.add(boid.getPosition(),predictedVel);

		PVector dist1 = PVector.sub(predictedPos,path_.start_);
		PVector dist2 = PVector.sub(path_.end_,path_.start_);
		dist2.normalize();
		dist2.mult(dist1.dot(dist2));
		PVector normalLine = PVector.add(path_.start_,dist2);

		PVector direction = PVector.sub(path_.end_,path_.start_);
		direction.normalize();
		direction.mult(path_.width_);
		PVector target = PVector.add(normalLine,direction);
		float distance = PVector.dist(normalLine,predictedPos);

		if ( distance > path_.width_ ) {
			PVector diff = PVector.sub(target,boid.getPosition());
			diff.normalize();
			PVector desiredvel = PVector.mult(diff,boid.getMaxSpeed());
			return PVector.sub(desiredvel,boid.getVelocity());
		}
		return new PVector(0,0);
	}

}
