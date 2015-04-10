package clids.ex5.crosswords;

/**
 * A crossword entry is a word at a given position in a crossword.
 * 
 * @author jjgold, mishelle
 */
public class MyCrosswordEntry implements CrosswordEntry{

	private CrosswordPosition pos;
	private int length;
	String definition, term;

	/**
	 * constructor 
	 * 
	 * @param pos
	 * @param length
	 */
	public MyCrosswordEntry(CrosswordPosition pos, int length){
		this.pos = pos;
		this.length = length;
	}

	/**
	 * Returns the X/Y/Vertical position of an entry
	 * @return the position
	 */
	public CrosswordPosition getPosition() {
		return this.pos;
	}

	/**
	 * Returns the corresponding definition of the entry.
	 * 
	 * @return the corresponding dictionary definition.
	 */
	public String getDefinition() {
		return this.definition;
	}

	/**
	 * Returns the corresponding word of the entry.
	 * 
	 * @return the corresponding term.
	 */
	public String getTerm() {
		return this.term;
	}

	/**
	 * Retrieves length of the entry (redundant convenience, may be calculated
	 * through getTerm()).
	 * 
	 * @return number of letters of the term.
	 */
	public int getLength() {
		return this.term.length();
	}

	/**
	 * returns the length of this entry
	 * 
	 * @return the length of this entry
	 */
	public int getSpaceLength() {
		return this.length;
	}


	/**
	 * sets the term
	 * 
	 * @param newTerm
	 */
	public void setTerm(String newTerm){
		this.term = newTerm;
	}

	/**
	 * sets the definition
	 * 
	 * @param newDefinition
	 */
	public void setDefinition(String newDefinition){
		this.definition = newDefinition;
	}

	/**
	 * creates a copy of this entry
	 * 
	 * @return
	 */
	public MyCrosswordEntry copy(){
		MyCrosswordEntry copy = new MyCrosswordEntry(this.pos, this.length);
		copy.setDefinition(this.definition);
		copy.setTerm(this.term);
		return copy;
	}


}
