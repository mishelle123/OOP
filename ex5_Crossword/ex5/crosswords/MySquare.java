package clids.ex5.crosswords;

/**
 * 
 * represents a square in the board
 * 
 * @author jjgold, mishelle
 *
 */
public class MySquare {
	
	private char c;
	protected int countUses; 
	
	/**
	 * constructor 
	 * 
	 * @param pos
	 */
	public MySquare() {
//		this.pos = pos;
		this.countUses = 0;
	}
	
	/**
	 * sets the char in the square
	 * @param newChar
	 */
	public void setChar(char newChar){
		this.c = newChar;
		
	}
	
	/**
	 * 
	 * @return the char in the square
	 */
	public char getChar(){
		return this.c;
	}
	
	/**
	 * returns a copy of the square
	 * 
	 * @return a copy of the square
	 */
	public MySquare getCopy(){
		MySquare copy = new MySquare() ;
		copy.c = this.c;
		copy.countUses = this.countUses;
		return copy;
	}
	
}
