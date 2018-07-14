package brain;

import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author Richie
 */
public class CometGravitationField {
	private PApplet parent_;
	private PVector[][] gField_;
	private int dimension_ = 30;
	private int cols_, rows_;

	/**
	 * @param parent
	 * @param p
	 */
	public CometGravitationField ( PApplet parent ) {
		parent_ = parent;
		cols_ = parent_.width / dimension_;
		rows_ = parent_.height / dimension_;
		gField_ = new PVector[cols_][rows_];
		init();
	}

	/**
	 * 
	 */
	private void init () {
		for ( int i = 0 ; i < cols_ ; i++ ) {
			for ( int j = 0 ; j < rows_ ; j++ ) {
				if ( i > rows_ / 2 && j < cols_ / 2 ) {
					gField_[i][j] = new PVector(-2,-2);
				} else if ( i < rows_ / 2 && j > cols_ / 2 ) {
					gField_[i][j] = new PVector(2,2);
				} else {
					gField_[i][j] = new PVector(0,0);
				}
			}
		}
	}

	/**
	 * @param position
	 * @return
	 */
	public PVector find ( PVector position ) {
		int xPos = (int) PApplet.constrain(position.x / dimension_,0,
		                                   parent_.width / dimension_ - 1);
		int yPos = (int) PApplet.constrain(position.y / dimension_,0,
		                                   parent_.height / dimension_ - 1);
		return gField_[xPos][yPos];
	}

}
