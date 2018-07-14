import java.util.Random;

import behavior.Cohesion;
import behavior.FollowField;
import behavior.FollowPath;
import behavior.Forward;
import behavior.Separation;
import brain.AlienBrain;
import brain.CometGravitationField;
import brain.RocketBrain;
import brain.WeightedAverageBrain;
import core.Boid;
import core.Brain;
import core.Path;
import core.SpaceType;
import core.World;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 * Boids main program. LET IT RUN FOR LIKE 15 SECONDS, THE VECTOR FIELDS WILL
 * CREATE A ASTEROID BELT!!!!
 */
public class Boids extends PApplet {
	private PImage bkGndImg_;
	private World world_; // the world containing the boids
	private int screenH_ = 1200;
	private int screenW_ = 600;
	private Path path_;
	private CometGravitationField gField_;

	public static void main ( String[] args ) {
		PApplet.main("Boids");
	}

	/**
	 * 
	 */
	public void settings () {
		size(screenH_,screenW_);
	}

	/**
	 * @return
	 */

	public float randomFloat () {
		Random rand = new Random();
		return rand.nextFloat() * screenH_;
	}

	/**
	 * @param number
	 * @param s
	 */
	public void setupEntity ( int number, SpaceType s ) {
		switch ( s ) {
		case ROCKET:
			RocketBrain rBrain = new RocketBrain(this);
			rBrain.addBehavior(new Separation(),3);
			rBrain.addBehavior(new Forward(),2);

			Boid rocket =
			    new Boid(this,world_,new PVector(600,300),1,PVector.random2D(),.7f,40,
			             60,radians(360),rBrain,s);
			world_.addBoid(rocket);
			break;

		case ALIEN:
			for ( int i = 0 ; i < number ; i++ ) {
				AlienBrain aBrain = new AlienBrain();
				aBrain.addBehavior(new Separation(),2);
				aBrain.addBehavior(new Forward(),1);
				Boid alien =
				    new Boid(this,world_,new PVector(randomFloat(),randomFloat()),1,
				             PVector.random2D(),.5f,40,50,radians(360),aBrain,s);
				world_.addBoid(alien);
			}
			break;

		case COMET:
			for ( int i = 0 ; i < number ; i++ ) {
				WeightedAverageBrain cBrain = new WeightedAverageBrain();
				gravitationalRing(cBrain);
				Boid comet =
				    new Boid(this,world_,new PVector(randomFloat(),randomFloat()),1,
				             PVector.random2D(),.03f,1,35,radians(135),cBrain,s);
				world_.addBoid(comet);
			}
			break;
		}

	}

	/**
	 * @param brain
	 */
	private void gravitationalRing ( Brain brain ) {
		brain.addBehavior(new FollowPath(path_),5);
		brain.addBehavior(new Separation(),5);
		brain.addBehavior(new FollowField(gField_),1);
		brain.addBehavior(new Cohesion(),2);
	}

	/**
	 * 
	 */
	public void setup () {
		bkGndImg_ = loadImage("glxy.jpg");
		background(bkGndImg_);
		world_ = new World();
		path_ = new Path(this);
		gField_ = new CometGravitationField(this);
		setupEntity(45,SpaceType.COMET);
		setupEntity(5,SpaceType.ALIEN);
		setupEntity(1,SpaceType.ROCKET);
	}

	/**
	 * 
	 */
	public void draw () {
		background(bkGndImg_);

		// draw all the boids in their current positions
		for ( Boid boid : world_.getBoids() ) {
			boid.render();
		}

		// calculate steering forces and move all boids
		for ( Boid boid : world_.getBoids() ) {
			boid.update();
			if ( boid.getPosition().x < 0 ) {
				boid.getPosition().x = screenH_;
			}
			if ( boid.getPosition().y < 0 ) {
				boid.getPosition().y = screenW_;
			}
			if ( boid.getPosition().x > screenH_ ) {
				boid.getPosition().x = 0;
			}
			if ( boid.getPosition().y > screenW_ ) {
				boid.getPosition().y = 0;
			}
		}

	}

}
