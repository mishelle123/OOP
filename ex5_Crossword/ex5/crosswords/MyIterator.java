package clids.ex5.crosswords;

import java.util.Iterator;

/**
 * iterator for crossword entry
 * 
 * @author jjgold, mishelle
 *
 */
public class MyIterator implements Iterator<CrosswordEntry>{

	CrosswordDictionary dictionary; 
	private String[] terms;
	private MyCrosswordEntry[] entries;
	private MySquare[][] board;
	private MyCrosswordEntry currentEntry;
	private int termsIndex, entriesIndex;

	/**
	 * constructor
	 * 
	 * @param entries
	 * @param terms
	 * @param dictionary
	 * @param board
	 */
	public MyIterator(MyCrosswordEntry[] entries,
			String[] terms, CrosswordDictionary dictionary, MySquare[][] board) {
		this.dictionary = dictionary;
		this.entries = entries;
		this.terms = terms;
		this.board = board;
		this.currentEntry = entries[0];
		this.entriesIndex = 0;
		this.termsIndex = 0;

	}

	/**
	 * Returns true if the iteration has more elements.
	 * 
	 * @ return true if the iteration has more elements.
	 */
	public boolean hasNext() {
		
		// calls findNext method
		this.currentEntry = findNext();

		// check if hasNext
		return (this.currentEntry!=null);
	}

	/**
	 *Returns the next element in the iteration.
	 *
	 *@return the next element in the iteration.
	 */
	public CrosswordEntry next() {
		return this.currentEntry;
	}

	private MyCrosswordEntry findNext(){
		
		// checks if there are still remaining terms or entries
		if(entriesIndex<entries.length-1 || termsIndex<terms.length){
			if(termsIndex<terms.length){

				String term = this.terms[termsIndex++]; 
				String definition = this.dictionary.getTermDefinition(term);
				
				this.currentEntry.setDefinition(definition);
				this.currentEntry.setTerm(term);
				
				// calls checkEntry method  which decides if the term fits the entry
				if(MyCrossword.checkEntry(this.board, this.currentEntry)){
					return this.currentEntry;	
				}
				// calls recursively to finNext() whith the next term
				else{
					return findNext();
				}
			}
			
			// updates the entry in case we already checked all the terms
			else{
				this.currentEntry = this.entries[++entriesIndex];
				this.termsIndex = 0;
				return findNext();
			}
		}
		return null;
	}

	@Override
	public void remove() {
	}

	

}
