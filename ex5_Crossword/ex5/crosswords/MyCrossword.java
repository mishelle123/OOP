package clids.ex5.crosswords;


import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import clids.ex5.crosswords.CrosswordStructure.SlotType;
import clids.ex5.search.BoardMove;
import clids.ex5.search.SearchBoard;



public class MyCrossword<M extends BoardMove> implements Crossword {

	private static final char FRAME = '#';
	private static final char UNUSED = '_';
	// List of all moves done so far 
	protected List<CrosswordEntry> movesRecord = new LinkedList<CrosswordEntry>();
	protected int quality;
	protected CrosswordDictionary dictionary; 
	protected TreeSet<MyCrosswordEntry> entries;
	protected TreeSet<String> terms;
	protected MySquare[][] board;


	/**
	 * constructor 
	 * 
	 */
	public MyCrossword(){
		this.entries = new TreeSet<>(new MyEntryComperator());
		this.terms = new TreeSet<>(new MyDictionaryComperator());
	}

	/**
	 * Returns true if a node object represents a one of the best 
	 * possible solutions of the problem, false otherwise.
	 * 
	 * @return True/False
	 */
	public boolean isBestSolution() {
		return this.terms.isEmpty();
	}


	/**
	 * Creates and returns an iterator on the list of nodes reachable by a
	 * single edge from the current node (in case of crossword - a list of
	 * crosswords with one more word in the grid).
	 * 
	 * @return Iterator object
	 */
	public Iterator<CrosswordEntry> getMovesIterator() {
		String[] terms = this.terms.toArray(new String[this.terms.size()]); 
		MyCrosswordEntry[] entries = this.entries.toArray(new MyCrosswordEntry[this.entries.size()]);
		return  new MyIterator(entries, terms, this.dictionary, this.board);
	}



	/**
	 * This function allows evaluation of quality of solutions 
	 * (higher values mean better solutions).
	 * boolean
	 * @return The quality value of this board.
	 */
	public int getQuality() {
		return this.quality;
	}

	/**
	 * This function allows estimation of potential 
	 * upper bound on quality of solutions available through zero or 
	 * more doMove() operations from the current board.
	 * 
	 * The returned value have to be always greater or equal to
	 * the best possible quality obtained through doMove operations.
	 * Hence, it is also assumed that doMove operation should never increase 
	 * the upper bound value.
	 * 
	 * @return The upper bound on quality of this board.
	 */
	public int getQualityBound() {
		int qualityBound=0;
		Iterator<String> iti = this.terms.iterator();
		
		// checks if the remaining terms fits the board
		while(iti.hasNext()){
			String term = iti.next();
			Iterator<MyCrosswordEntry> iti2 = this.entries.descendingIterator();
			boolean checkNextTerm = true;;
			// check for every entry
			while(checkNextTerm){
				if(iti2.hasNext()){
					MyCrosswordEntry entry = iti2.next();
					entry.setTerm(term);
					// checks if an entry with word fits the board
					if(checkEntry(this.board, entry)){
						qualityBound += entry.getTerm().length();
						// exit the while loop
						checkNextTerm = false;
					}
				}
				else{
					// exit the while loop
					checkNextTerm = false;
				}
			}
		}
		return this.quality + qualityBound;
	}

	/**
	 * Performs a move on the board potentially (reversibly) changing 
	 * the current board object.
	 * @param move
	 */
	public void doMove(CrosswordEntry move) {
		String term = move.getTerm();
		int x = move.getPosition().getX();
		int y = move.getPosition().getY();
		boolean isVertical = move.getPosition().isVertical();

		// performs the move on vertical entry
		if(isVertical){
			for(int i=0; i< term.length(); i++){
				if(this.board[x][y+i].getChar()==UNUSED){
					this.board[x][y+i].setChar(term.charAt(i));
				}
				this.board[x][y+i].countUses++;
			}

		}
		// performs the move on horizontal entry
		else{
			for(int i=0; i< term.length(); i++){
				if(this.board[x+i][y].getChar()==UNUSED){
					this.board[x+i][y].setChar(term.charAt(i));
				}
				this.board[x+i][y].countUses++;
			}
		}
		// update the quality
		this.quality += term.length();
		// adds the move to the move list
		this.movesRecord.add(0, move);
		// removes the entry from the entry list
		this.entries.remove((MyCrosswordEntry)move);
		// removes the term from the entry list
		this.terms.remove(term);
	}



	/**
	 * Restores the object to state before the last move.
	 * It is assumed that sequence of undoMove operations always
	 * reflect in the correct order the sequence of doMove operations.
	 * Hence undoMove is always supplied with the most recent un-undoed move.
	 * (You don't have to check this assumption)
	 * @param move
	 */
	public void undoMove(CrosswordEntry move) {
		// Get most recent move
		MyCrosswordEntry lastMove = (MyCrosswordEntry) this.movesRecord.get(0);

		// Check if move is valid
		assert(move == lastMove);

		String term = move.getTerm();
		int x = move.getPosition().getX();
		int y = move.getPosition().getY();
		boolean isVertical = move.getPosition().isVertical();

		// undo the move on vertical entry
		if(isVertical){
			for(int i=0; i< term.length(); i++){
				if(this.board[x][y+i].countUses ==1){
					this.board[x][y+i].setChar(UNUSED);
				}
				this.board[x][y+i].countUses--;
			}

		}
		// undo the move on horizontal entry
		else{
			for(int i=0; i< term.length(); i++){
				if(this.board[x+i][y].countUses==1){
					this.board[x+i][y].setChar(UNUSED);
				}
				this.board[x+i][y].countUses--;
			}
		}
		// update the quality
		this.quality -= term.length();
		// removes the move to the move list
		this.movesRecord.remove(0);	
		// adds the entry from the entry list
		this.entries.add(lastMove);
		// adds the term from the entry list
		this.terms.add(term);

	}

	/**
	 * Creates a stand-alone copy of the current board.
	 * The returned copy should not be affected by subsequent doMove/undoMove
	 * operations on the current board.
	 * @return a copy of given board.
	 */
	public SearchBoard<CrosswordEntry> getCopy() {
		MyCrossword<M> copy = new MyCrossword<M>(); 
		copy.board = new MySquare[this.board.length][this.board[0].length];
		// copy the board
		for(int x = 0; x<this.board.length; x++){
			for(int y =0; y<this.board[0].length ; y++){
				copy.board[x][y] = this.board[x][y].getCopy();

			}
		}
		// creates copy's data structures
		TreeSet<MyCrosswordEntry> entriesCopy = new TreeSet<>(new MyEntryComperator());
		TreeSet<String> termsCopy = new TreeSet<>(new MyDictionaryComperator());
		List<CrosswordEntry> movesRecordCopy = new LinkedList<CrosswordEntry>();
		
		// creates iterators for the different data structures
		Iterator<MyCrosswordEntry> iti1 = this.entries.iterator();
		Iterator<String> iti2 = this.terms.iterator();
		Iterator<CrosswordEntry> iti3 = this.movesRecord.iterator();
		
		// copy 
		while(iti1.hasNext()){
			entriesCopy.add(iti1.next().copy());
		}

		while(iti2.hasNext()){
			termsCopy.add(iti2.next());
		}
		while(iti3.hasNext()){
			movesRecordCopy.add(((MyCrosswordEntry)iti3.next()).copy());
		}

		copy.terms = termsCopy;
		copy.dictionary = this.dictionary;
		copy.quality = this.quality; 
		copy.entries = entriesCopy;
		copy.movesRecord = movesRecordCopy;
		
		return copy;
	}

	/**
	 * Initializes all structures associated with crossword dictionary 
	 * Assumes valid and non-NULL dictionary object
	 * 
	 * @param dictionary - the dictionary object
	 * 					  (generated according to a text file)
	 */
	public void attachDictionary(CrosswordDictionary dictionary) {
		this.dictionary = dictionary;
		
		// copy the words in the dictionary
		for(String s: dictionary.getTerms()){
			this.terms.add(s);
		}

	}

	/**
	 * Initializes all data structures associated with crossword structure. 
	 * Assumes valid and non-NULL shape object
	 * 
	 * @param shape - the structure object (generated according to a text file)
	 */
	public void attachStructure(CrosswordStructure structure) {
		
		int boardWidth = structure.getWidth();
		int boardHight = structure.getHeight();
		this.board =  new MySquare[boardWidth][boardHight];

		// builds the board
		for(int x = 0; x<boardWidth; x++){
			for(int y =0; y< boardHight ; y++){
				MyCrosswordPosition pos = new MyCrosswordPosition(x, y, true);
				this.board[x][y] = new MySquare();
				// marks the square as 'unused'
				if(structure.getSlotType(pos).equals(SlotType.UNUSED_SLOT)){
					this.board[x][y].setChar(UNUSED);
				}
				// marks the square as 'frame'
				else{
					this.board[x][y].setChar(FRAME);
				}
			}
		}


		// create the entries list
		for(int x = 0; x<boardWidth; x++){
			for(int y =0; y< boardHight ; y++){
				// checks if the square is empty
				if(this.board[x][y].getChar()==UNUSED){

					MyCrosswordPosition vertical = new MyCrosswordPosition(x, y, true);
					MyCrosswordPosition horizontal = new MyCrosswordPosition(x, y, false);
					int i = y;
					
					// checks the length of the vertical entry
					while((i<boardHight) 
							&& (this.board[x][i].getChar()==UNUSED)){
						i++;
					}
					// creates the vertical entry
					MyCrosswordEntry verticalEntry = new MyCrosswordEntry(vertical, i-y);
					this.entries.add(verticalEntry);

					int j =x;
					
					// checks the length of the horizontal entry
					while((j< boardWidth) 
							&& (this.board[j][y].getChar()==UNUSED)){
						j++;
					}
					// creates the horizontal entry
					MyCrosswordEntry horizontalEntry = new MyCrosswordEntry(horizontal, j-x);
					this.entries.add(horizontalEntry);


				}
			}
		}

	}
	
	/**
	 * prints the board
	 * 
	 */
	public void printBoard (){
		for(int y = 0; y<this.board[0].length; y++){
			for(int x =0; x<this.board.length ; x++){
				System.out.print(board[x][y].getChar() + "");

			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Retrieves list of filled crossword entries associated with this 
	 * Crossword.The set of filled entries should satisfy both of 
	 * exercise requirements. 
	 * 
	 * There is no requirement on the order of the returned collection
	 * of entries.
	 * 
	 * @return Collection of filled entries.
	 */
	public Collection<CrosswordEntry> getCrosswordEntries() {
		return this.movesRecord;
	}

	/**
	 * return true iff the entry matches the board
	 * 
	 * @param board
	 * @param entry
	 * @return true iff the entry matches the board
	 */
	public static boolean checkEntry(MySquare[][] board, MyCrosswordEntry entry){
		String term = entry.getTerm(); 
		if(term.length()> entry.getSpaceLength()){
			return false;
		}
		int x = entry.getPosition().getX();
		int y = entry.getPosition().getY();
		boolean isVertical = entry.getPosition().isVertical();

		// checks if the vertical entry is unused or contains the same words as the term
		if(isVertical){
			for(int i=0; i< term.length(); i++){
				char c = board[x][y+i].getChar();
				if(c!=UNUSED && c!=term.charAt(i)){
					return false;
				}
			}
			return true;
		}
		// checks if the horizontal entry is unused or contains the same words as the term
		else{
			for(int i=0; i< term.length(); i++){
				char c = board[x+i][y].getChar();
				if(c!=UNUSED && c!=term.charAt(i)){
					return false;
				}
			}
			return true;		
		}
	}

}
