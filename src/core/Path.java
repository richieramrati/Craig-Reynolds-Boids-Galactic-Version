package core;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author Richie
 */
public class Path {

	public PVector start_;
	public PVector end_;
	public PApplet parent_;
	public float width_;

	public Path ( PApplet parent ) {
		parent_ = parent;
		width_ = parent.width / 8;
		start_ = new PVector(0,0);
		end_ = new PVector(parent_.width,parent_.height);

	}

}
