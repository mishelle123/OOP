package clids.ex5.crosswords;


/**
 * Specifies a basic position of an entry in a crossword.
 * 
 */
public class MyCrosswordPosition implements CrosswordPosition{

	private int x, y;
	private boolean isVertical;
	 
	/**
	 * constructor 
	 * 
	 * @param x
	 * @param y
	 * @param isVertical
	 */
	public MyCrosswordPosition(int x, int y, boolean isVertical){
		this.x = x;
		this.y = y;
		this.isVertical = isVertical;
	}
	
	/**
	 * @return The x coordinate.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return The y coordinate.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * @return True iff position is a vertical position.
	 */
	public boolean isVertical() {
		return this.isVertical;
	}

}
