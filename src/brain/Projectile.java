package brain;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author Richie
 */
@SuppressWarnings("serial")
public class Projectile extends PVector {
	PVector vel_;
	PApplet p_;

	Projectile ( PVector loc, PVector vel, PApplet p ) {
		super(loc.x,loc.y);
		vel_ = vel;
		p_ = p;
	}

	void update () {
		add(vel_.mult(5));
	}

	void display () {
		p_.fill(255,255,255);
		p_.ellipse(x,y,20,20);
	}

}
